package pl.moderr.eduscript.parser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.lexer.EsTokenKind;
import pl.moderr.eduscript.statements.EsOperatorStatement;
import pl.moderr.eduscript.types.EsInt;

import static pl.moderr.eduscript.lexer.EsTokenKinds.*;

public class EsParser extends EsParserBase {

  @Override
  protected void computeParse() {
    if (tokens().isEmpty()) return;
    do {
      computeStatement();
      consume(INSTRUCTION_END);
    } while (!(pos() >= tokens().size()));
  }

  private void computeStatement() {
    EsExpression left;
    if (matchStay(MINUS) || matchStay(PLUS)) {
      left = new EsInt();
    } else {
      left = parseValue();
    }

    while (token().isPresent() && token().get().kind().isOperator()) {
      EsTokenKind operator = tokenNext().get().kind();
      EsExpression right = parseValue();
      left = new EsOperatorStatement(left, operator, right);
    }

    addExpression(left);
  }

  @Contract(" -> new")
  private @NotNull EsValue<?> parseValue() {
    // parse integer
    if (matchStay(INTEGER)) {
      EsToken literalInt = tokenNext().get();
      String literal = literalInt.value();
      int value = Integer.parseInt(literal);
      return new EsInt(value);
    }
    // unknown
    throw new EsScriptError("expected value, got " + token());
  }
}
