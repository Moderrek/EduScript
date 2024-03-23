package pl.moderr.eduscript.types;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.errors.EsScriptError;

public class EsFloat extends EsValue<Double> {

  private final double value;

  public EsFloat() {
    this.value = 0;
  }

  public EsFloat(double value) {
    this.value = value;
  }

  @Override
  public String asString() {
    return String.valueOf(value);
  }

  @Override
  public Double unwrap() {
    return value;
  }

  @Override
  public EsType getType() {
    return EsTypes.FLOAT;
  }

  public @NotNull EsValue<?> operatorplus(@NotNull EsValue<?> other) {
    if (other.isType(EsInt.class)) {
      EsInt right = other.cast(EsInt.class);
      if (right == null) throw new EsScriptError("Nie można rzutować typu!");
      double result = value + right.unwrap();
      return new EsFloat(result);
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
      double result = value * right.unwrap();
      return new EsFloat(result);
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
