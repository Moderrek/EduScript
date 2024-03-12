package pl.moderr.eduscript.statements;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.vm.EsScript;

public class EsLetStatement extends EsExpression {

  private final String id;
  private final EsExpression expr;

  public EsLetStatement(String identifier, EsExpression value) {
    this.id = identifier;
    this.expr = value;
  }

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    EsValue<?> value = expr.evaluate(script);
    script.setVariable(id, value);
    script.getVirtualMachine().out.accept(id + "=" + value);
    return script.getVariable(id).get().get();
  }

}
