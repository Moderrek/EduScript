package pl.moderr.eduscript.parser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.errors.EsParserError;
import pl.moderr.eduscript.errors.EsScriptError;
import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.lexer.EsTokenKind;
import pl.moderr.eduscript.statements.*;
import pl.moderr.eduscript.types.EsVar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static pl.moderr.eduscript.lexer.EsTokenKinds.*;

public class EsParser extends EsParserBase {

  @Override
  protected void computeParse() {
    if (tokens().isEmpty()) return;
    do {
      addExpression(statement());
      consume(INSTRUCTION_END);
    } while (!(pos() >= tokens().size()));
  }

  private @NotNull EsExpression expression(int minPrecedence) {
    EsExpression lhs;
    if (matchStay(MINUS) || matchStay(PLUS)) {
      lhs = EsValue.of(0);
    } else {
      lhs = term();
      if (lhs == null)
        throw new EsParserError(token().get().start(), "Podjęto próbę przeanalizowania wyrażenia, ale nie została znaleziona.");
    }
    while (token().isPresent() && token().get().kind().isOperator()) {
      if (token().isEmpty()) break;
      EsToken operator = token().get();
      int operatorPrecedence = operator.kind().getOperatorPrecedence();
      if (operatorPrecedence < 0) break;
      if (operatorPrecedence < minPrecedence) break;
      nextToken();
      int nextMinPrecedence = operatorPrecedence + 1;
      EsExpression rhs = expression(nextMinPrecedence);
      lhs = new EsOperatorStatement(lhs, operator.kind(), rhs);
    }
    return lhs;
  }

  private @NotNull EsExpression expression() {
    return expression(0);
  }

  private @Nullable EsExpression term() {
    // parse integer
    if (matchStay(INTEGER)) {
      EsToken literalInt = tokenNext().get();
      String literal = literalInt.value();
      int integer = Integer.parseInt(literal);
      return EsValue.of(integer);
    }
    // parse decimal
    if (matchStay(DECIMAL)) {
      EsToken literalDecimal = tokenNext().get();
      double value = Double.parseDouble(literalDecimal.value());
      return EsValue.of(value);
    }
    // parse str
    if (matchStay(STRING)) {
      return EsValue.of(tokenNext().get().value());
    }
    // parse bool
    if (matchStay(TRUE) || matchStay(FALSE)) {
      EsTokenKind tokenKind = tokenNext().get().kind();
      return EsValue.of(tokenKind == TRUE);
    }
    // Function expression
    if (match(IDENTIFIER, PAREN_LEFT)) {
      return fnCall();
    }
    // parse variable
    if (matchStay(IDENTIFIER)) {
      return new EsVar(tokenNext().get().value());
    }
    // parse returnable block of code
    if (match(CURLY_LEFT)) {
      List<EsExpression> expressions = new ArrayList<>();
      while (token().isPresent() && !token().get().match(CURLY_RIGHT)) {
        expressions.add(statement());
        consume(INSTRUCTION_END);
      }
      consume(CURLY_RIGHT);
      return new EsBlockExpression(expressions.toArray(new EsExpression[expressions.size()]));
    }
    return null;
  }

  @Contract(" -> new")
  private @NotNull EsExpression statement() {
    // Define mutable variable
    if (matchStay(LET)) {
      consume(LET);
      String identifier = tokenNext().get().value();
      consume(ASSIGN);
      EsExpression value = expression();
      return new EsLetStatement(identifier, value, true);
    }
    // Define const variable
    if (matchStay(CONST)) {
      consume(CONST);
      String identifier = tokenNext().get().value();
      consume(ASSIGN);
      EsExpression expression = expression();
      return new EsLetStatement(identifier, expression, false);
    }
    // Block statement
    if (matchStay(CURLY_LEFT)) {
      consume(CURLY_LEFT);
      List<EsExpression> expressions = new ArrayList<>();
      while (pos() < tokens().size() && !token().get().match(CURLY_RIGHT)) {
        expressions.add(statement());
        consume(INSTRUCTION_END);
      }
      consume(CURLY_RIGHT);
      return new EsBlockExpression(expressions.toArray(new EsExpression[expressions.size()]));
    }
    // Return statement
    if (matchStay(RETURN)) {
      consume(RETURN);
      EsExpression expr = expression();
      return new EsReturnStatement(expr);
    }
    // Assign statement
    if (match(IDENTIFIER, ASSIGN)) {
      EsToken identifier = lookTokenBack(2).get();
      EsExpression expr = expression();
      return new EsVariableAssignStatement(identifier, expr);
    }
    // Function statement
    if (match(IDENTIFIER, PAREN_LEFT)) {
      return fnCall();
    }
    // unknown
    EsToken token = token().get();
    throw new EsScriptError(token.start(), MessageFormat.format("Nieprawidłowe wyrażenie {0}", token.toString()));
  }

  @Contract(" -> new")
  private @NotNull EsExpression fnCall() {
    EsToken name = lookTokenBack(2).get();
    ArrayList<EsExpression> args = new ArrayList<>();
    boolean haveArgs = false;

    while (!match(PAREN_RIGHT)) {
      haveArgs = true;
      args.add(expression());
      if (!match(SEPARATOR_COMMA)) break;
    }

    if (haveArgs) consume(PAREN_RIGHT);

    return new EsFunctionCall(name, args);
  }

}
