package pl.moderr.eduscript.statements;

import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.lexer.EsTokenKind;
import pl.moderr.eduscript.lexer.EsTokenKinds;

import static pl.moderr.eduscript.lexer.EsTokenKinds.*;

public class EsOperatorStatement extends EsExpression {

  private final EsExpression expr_left;
  private final EsTokenKind operator;
  private final EsExpression expr_right;

  public EsOperatorStatement(EsExpression left, EsTokenKind op, EsExpression right) {
    this.expr_left = left;
    this.operator = op;
    this.expr_right = right;
  }

  @Override
  public EsValue<?> evaluate() {
    EsValue<?> left = expr_left.evaluate();
    EsValue<?> right = expr_right.evaluate();

    if (operator == PLUS) {
      return left.operatorPlus(right);
    }
    if (operator == MULTIPLY) {
      return left.operatorMultiply(right);
    }

    throw new UnsupportedOperationException();
  }

}
