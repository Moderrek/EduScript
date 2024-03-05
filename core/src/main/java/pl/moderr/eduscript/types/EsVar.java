package pl.moderr.eduscript.types;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.vm.EsScript;

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
    Optional<EsValue<?>> variable = script.getVariable(identifier);
    if (variable.isEmpty()) {
      throw new EsScriptError("Undefined variable '" + identifier + "'");
    }
    return variable.get();
  }
}
