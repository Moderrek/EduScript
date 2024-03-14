package pl.moderr.eduscript.statements;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.errors.EsNameError;
import pl.moderr.eduscript.errors.EsRuntimeError;
import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.types.EsFunctionRef;
import pl.moderr.eduscript.types.EsUnit;
import pl.moderr.eduscript.vm.EsFunction;
import pl.moderr.eduscript.vm.EsScript;
import pl.moderr.eduscript.vm.EsVariable;

import java.util.ArrayList;
import java.util.Optional;

public class EsFunctionCall extends EsExpression {

  private final EsToken ident;
  private final ArrayList<EsExpression> args;

  public EsFunctionCall(EsToken name, ArrayList<EsExpression> args) {
    this.ident = name;
    this.args = args;
  }

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    Optional<EsVariable> variableOptional = script.findVariable(ident.value());
    if (variableOptional.isEmpty())
      throw new EsNameError(ident.start(), "Funkcja '" + ident.value() + "' nie jest zdefiniowana!");
    EsVariable var = variableOptional.get();
    if (!var.get().isType(EsFunctionRef.class))
      throw new EsRuntimeError(ident.start(), "Wartość '" + ident.value() + "' nie jest funkcją!");
    EsFunction func = (EsFunction) var.get().unwrap();
    func.evaluate(script, args.toArray(new EsExpression[args.size()]));
    return EsUnit.get();
  }
}
