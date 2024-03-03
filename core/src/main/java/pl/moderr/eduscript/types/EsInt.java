package pl.moderr.eduscript.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;

public class EsInt extends EsValue<Integer> {

  public EsInt() {
    value = 0;
  }

  public EsInt(int value) {
    this.value = value;
  }

  private final int value;

  @Override
  public EsType getType() {
    return EsTypes.INT;
  }

  @Override
  public String asString() {
    return String.valueOf(value);
  }

  @Override
  public Integer unwrap() {
    return value;
  }

  @Override
  public @NotNull EsValue<?> operatorPlus(@NotNull EsValue<?> other) {
    if (other.isType(EsInt.class)) {
      EsValue<Integer> right = other.cast(EsInt.class);
      if (right == null) throw new EsScriptError("Cannot cast!");
      int result = value + right.unwrap();
      return new EsInt(result);
    }
    throw new UnsupportedOperationException();
  }
}