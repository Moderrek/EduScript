package pl.moderr.eduscript.statements;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.vm.EsScript;

public class EsIfStatement extends EsExpression {
  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    return null;
  }
}
