package pl.moderr.eduscript.parser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.lexer.EsTokenKind;
import pl.moderr.eduscript.statements.EsLetStatement;
import pl.moderr.eduscript.statements.EsOperatorStatement;
import pl.moderr.eduscript.types.EsBool;
import pl.moderr.eduscript.types.EsInt;
import pl.moderr.eduscript.types.EsStr;
import pl.moderr.eduscript.types.EsVar;

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
      lhs = new EsInt();
    } else {
      lhs = term();
      if (lhs == null)
        throw new EsScriptError("Expression is null");
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
      int value = Integer.parseInt(literal);
      return new EsInt(value);
    }
    // parse str
    if (matchStay(STRING)) {
      return new EsStr(tokenNext().get().value());
    }
    // parse bool
    if (matchStay(TRUE) || matchStay(FALSE)) {
      EsTokenKind value = tokenNext().get().kind();
      return new EsBool(value == TRUE);
    }
    // parse variable
    if (matchStay(IDENTIFIER)) {
      return new EsVar(tokenNext().get().value());
    }
    return null;
  }

  @Contract(" -> new")
  private @NotNull EsExpression statement() {
    if (match(LET)) {
      String identifier = tokenNext().get().value();
      consume(ASSIGN);
      EsExpression value = expression();
      return new EsLetStatement(identifier, value);
    }
    if (match(CURLY_LEFT)) {
      List<EsExpression> expressions = new ArrayList<>();
      while (pos() < tokens().size() && !token().get().match(CURLY_RIGHT)) {
        expressions.add(statement());
        consume(INSTRUCTION_END);
      }
      consume(CURLY_RIGHT);
//      EsExpression[]
    }
    EsToken token = token().get();
    // unknown
    throw new EsScriptError(token.start().line(), token.start().col(), "Invalid statement");
  }
}
