package pl.moderr.eduscript.types;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;

public class EsBool extends EsValue<Boolean> {

  private final boolean value;

  public EsBool() {
    this.value = false;
  }

  public EsBool(boolean value) {
    this.value = value;
  }

  @Override
  public EsType getType() {
    return EsTypes.BOOL;
  }

  @Override
  public String asString() {
    return String.valueOf(value);
  }

  @Override
  public Boolean unwrap() {
    return value;
  }

  @Override
  public @NotNull EsValue<?> operatorPlus(@NotNull EsValue<?> other) {
    throw new UnsupportedOperationException();
  }

  @Override
  public @NotNull EsValue<?> operatorMultiply(@NotNull EsValue<?> right) {
    throw new UnsupportedOperationException();
  }
}
