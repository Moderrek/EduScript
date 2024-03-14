package pl.moderr.eduscript.types;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.vm.EsFunction;

public class EsFunctionRef extends EsValue<EsFunction> {

  private final @NotNull EsFunction func;

  public EsFunctionRef(@NotNull EsFunction func) {
    this.func = func;
  }

  @Override
  public EsFunction unwrap() {
    return func;
  }

  @Override
  public EsType getType() {
    return EsTypes.FUNC;
  }

  @Override
  public String asString() {
    return "funkcja";
  }

}
