package pl.moderr.eduscript.types;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.errors.EsRuntimeError;
import pl.moderr.eduscript.vm.EsScript;
import pl.moderr.eduscript.vm.EsVariable;

import java.util.Optional;

public class EsVar extends EsValue<String> {

  private final String identifier;

  public EsVar(@NotNull String identifier) {
    this.identifier = identifier;
  }

  @Override
  public String unwrap() {
    return identifier;
  }

  @Override
  public EsType getType() {
    return EsTypes.VAR;
  }

  @Override
  public String asString() {
    return identifier;
  }

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    Optional<EsVariable> variable = script.findVariable(identifier);
    if (variable.isEmpty())
      throw new EsRuntimeError("Niezdefiniowana wartość '" + identifier + "'.");
    return variable.get().get();
  }

}
