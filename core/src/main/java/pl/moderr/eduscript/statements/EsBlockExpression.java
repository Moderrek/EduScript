package pl.moderr.eduscript.statements;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.types.EsUnit;
import pl.moderr.eduscript.vm.EsScript;

public class EsBlockExpression extends EsExpression {

  private final EsExpression[] expressions;

  public EsBlockExpression(EsExpression[] expressions) {
    this.expressions = expressions;
  }

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    for (EsExpression expression : expressions) {
      if (expression instanceof EsReturnStatement) {
        return expression.evaluate(script);
      }
      expression.evaluate(script);
    }
    return EsUnit.get();
  }
}
