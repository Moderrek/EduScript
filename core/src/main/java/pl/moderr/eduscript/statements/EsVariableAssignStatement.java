package pl.moderr.eduscript.statements;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.types.EsUnit;
import pl.moderr.eduscript.vm.EsScript;
import pl.moderr.eduscript.vm.EsVariable;

public class EsVariableAssignStatement extends EsExpression {

  private final EsToken ident;
  private final EsExpression expr;

  public EsVariableAssignStatement(@NotNull EsToken ident, @NotNull EsExpression expr) {
    this.ident = ident;
    this.expr = expr;
  }

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    String name = ident.value();
    if (!script.hasDefinedVariable(name))
      throw new EsScriptError(ident.start(), "Variable '" + name + "' is not defined.");
    EsVariable variable = script.data().getVariable(name).get();
    if (variable.isConst())
      throw new EsScriptError(ident.start(), "Variable '" + name + "' is const.");

    EsValue<?> value = expr.evaluate(script);
    variable.set(value);

    return EsUnit.get();
  }
}
