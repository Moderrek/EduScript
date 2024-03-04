package pl.moderr.eduscript.statements;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.vm.EsScript;

public class EsReturnStatement extends EsExpression {

  private final EsExpression expr;

  public EsReturnStatement(EsExpression expr) {
    this.expr = expr;
  }

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    return expr.evaluate(script);
  }
}
