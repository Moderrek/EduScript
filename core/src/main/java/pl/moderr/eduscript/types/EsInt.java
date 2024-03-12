package pl.moderr.eduscript.types;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;

public class EsInt extends EsValue<Integer> {

  private final int value;

  public EsInt() {
    value = 0;
  }

  public EsInt(int value) {
    this.value = value;
  }

  @Override
  public Integer unwrap() {
    return value;
  }

  @Override
  public @NotNull EsValue<?> operatorPlus(@NotNull EsValue<?> other) {
    if (other.isType(EsInt.class)) {
      EsValue<Integer> right = other.cast(EsInt.class);
      if (right == null) throw new EsScriptError("Nie można rzutować typu!");
      int result = value + right.unwrap();
      return new EsInt(result);
    }
    throw new UnsupportedOperationException();
  }

  @Override
  public @NotNull EsValue<?> operatorMultiply(@NotNull EsValue<?> other) {
    if (other.isType(EsInt.class)) {
      EsValue<Integer> right = other.cast(EsInt.class);
      if (right == null) throw new EsScriptError("Nie można rzutować typu!");
      int result = value * right.unwrap();
      return new EsInt(result);
    }
    throw new UnsupportedOperationException();
  }

  @Override
  public EsType getType() {
    return EsTypes.INT;
  }

  @Override
  public String asString() {
    return String.valueOf(value);
  }
}
