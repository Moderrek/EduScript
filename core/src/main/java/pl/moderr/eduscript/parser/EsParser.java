package pl.moderr.eduscript.parser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.lexer.EsTokenKind;
import pl.moderr.eduscript.lexer.EsTokenKinds;
import pl.moderr.eduscript.statements.EsLetStatement;
import pl.moderr.eduscript.statements.EsOperatorStatement;
import pl.moderr.eduscript.types.EsInt;

import static pl.moderr.eduscript.lexer.EsTokenKinds.*;

public class EsParser extends EsParserBase {

  @Override
  protected void computeParse() {
    if (tokens().isEmpty()) return;
    do {
      addExpression(expression());
      consume(INSTRUCTION_END);
    } while (!(pos() >= tokens().size()));
  }

  private EsExpression expression(int minPrecedence) {
    EsExpression lhs;
    if (matchStay(MINUS) || matchStay(PLUS)) {
      lhs = new EsInt();
    } else {
      lhs = value();
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

  private EsExpression expression() {
    return expression(0);
  }

  @Contract(" -> new")
  private @NotNull EsExpression value() {
    // parse integer
    if (matchStay(INTEGER)) {
      EsToken literalInt = tokenNext().get();
      String literal = literalInt.value();
      int value = Integer.parseInt(literal);
      return new EsInt(value);
    }
    if (match(LET)) {
      String identifier = tokenNext().get().value();
      consume(ASSIGN);
      EsExpression value = expression();
      return new EsLetStatement(identifier, value);
    }
    // unknown
    throw new EsScriptError("expected value, got " + token());
  }
}
