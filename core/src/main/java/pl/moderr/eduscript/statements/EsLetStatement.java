package pl.moderr.eduscript.statements;

import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;

public class EsLetStatement extends EsExpression {

  private final String id;
  private final EsExpression expr;

  public EsLetStatement(String identifier, EsExpression value) {
    this.id = identifier;
    this.expr = value;
  }

  @Override
  public EsValue<?> evaluate() {
    EsValue<?> value = expr.evaluate();
    System.out.println(id + " = " + value);
    return value;
  }

}
