package pl.moderr.eduscript.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.errors.EsScriptError;

public class EsInt extends EsValue<Integer> {

  private final int value;

  public EsInt() {
    value = 0;
  }

  public EsInt(int value) {
    this.value = value;
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
  public <W, T extends EsValue<W>> @Nullable T cast(Class<T> to) {
    return super.cast(to);
  }

  @Override
  public EsType getType() {
    return EsTypes.INT;
  }

  public @NotNull EsValue<?> operatorplus(@NotNull EsValue<?> other) {
    if (other.isType(EsInt.class)) {
      EsValue<Integer> right = other.cast(EsInt.class);
      if (right == null) throw new EsScriptError("Nie można rzutować typu!");
      int result = value + right.unwrap();
      return new EsInt(result);
    }
    if (other.isType(EsFloat.class)) {
      EsFloat right = other.cast(EsFloat.class);
      if (right == null) throw new EsScriptError("Nie można rzutować typu!");
      double result = value + right.unwrap();
      return new EsFloat(result);
    }
    throw new UnsupportedOperationException();
  }

  public @NotNull EsValue<?> operatormultiply(@NotNull EsValue<?> other) {
    if (other.isType(EsInt.class)) {
      EsValue<Integer> right = other.cast(EsInt.class);
      if (right == null) throw new EsScriptError("Nie można rzutować typu!");
      int result = value * right.unwrap();
      return new EsInt(result);
    }
    if (other.isType(EsFloat.class)) {
      EsFloat right = other.cast(EsFloat.class);
      if (right == null) throw new EsScriptError("Nie można rzutować typu!");
      double result = value * right.unwrap();
      return new EsFloat(result);
    }
    throw new UnsupportedOperationException();
  }
}
