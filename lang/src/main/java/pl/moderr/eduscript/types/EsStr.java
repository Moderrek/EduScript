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
  public String asString() {
    return value;
  }

  @Override
  public String unwrap() {
    return value;
  }

  @Override
  public EsType getType() {
    return EsTypes.STR;
  }

  public @NotNull EsValue<?> operatorplus(@NotNull EsValue<?> other) {
    return new EsStr(value + other.asString());
  }

  public @NotNull EsValue<?> operatormultiply(@NotNull EsValue<?> other) {
    EsInt right = other.cast(EsInt.class);
    assert right != null;
    return new EsStr(value.repeat(right.unwrap()));
  }

}
