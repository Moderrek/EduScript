package pl.moderr.eduscript.statements;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.vm.EsScript;
import pl.moderr.eduscript.vm.EsVariable;

public class EsLetStatement extends EsExpression {

  private final String id;
  private final EsExpression expr;
  private final boolean mutable;

  public EsLetStatement(String identifier, EsExpression value, boolean mutable) {
    this.id = identifier;
    this.expr = value;
    this.mutable = mutable;
  }

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    EsValue<?> value = expr.evaluate(script);
    script.data().setVariable(id, mutable ? EsVariable.Mutable(id, value) : EsVariable.Const(id, value));
    return script.data().getVariable(id).get().get();
  }

}
