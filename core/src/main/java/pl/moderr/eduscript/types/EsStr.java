package pl.moderr.eduscript.types;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;

public class EsStr extends EsValue<String> {

  private final String value;

  public EsStr() {
    this.value = "";
  }

  public EsStr(String value) {
    this.value = value;
  }

  @Override
  public @NotNull EsValue<?> operatorPlus(@NotNull EsValue<?> other) {
    return new EsStr(value + other.asString());
  }

  @Override
  public String unwrap() {
    return value;
  }

  @Override
  public EsType getType() {
    return EsTypes.STR;
  }

  @Override
  public String asString() {
    return value;
  }

}
