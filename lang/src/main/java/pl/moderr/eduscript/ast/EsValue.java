package pl.moderr.eduscript.ast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.types.*;
import pl.moderr.eduscript.vm.EsScript;

public abstract class EsValue<V> extends EsExpression {

  public static @NotNull EsValue<Boolean> of(boolean value) {
    return new EsBool(value);
  }

  public static @NotNull EsValue<Integer> of(int value) {
    return new EsInt(value);
  }

  public static @NotNull EsValue<Double> of(double value) {
    return new EsFloat(value);
  }

  public static @NotNull EsValue<String> of(String value) {
    return new EsStr(value);
  }

  public static @NotNull EsValue<Void> of() {
    return new EsUnit();
  }

  public static <T extends EsValue> @NotNull T safeCast(@NotNull EsValue value, @NotNull Class<T> type) {
    if (type == EsValue.class) return (T) value;
    if (!value.getClass().equals(type)) {
      EsValue casted = value.tryCast(type);
      if (value == casted) throw new RuntimeException("TODO ZŁY TYP");
      return (T) casted;
    }
    return (T) value;
  }

  public <T extends EsValue> EsValue tryCast(Class<T> to) {
    if (to == EsStr.class) {
      return new EsStr(this.asString());
    }
    return this;
  }
//  public static <T extends EsValue<?>> @NotNull T Cast(@NotNull EsValue<?> value, @NotNull Class<T> type) {
//    T casted = value.cast(type);
//    if (casted == null)
//      throw new EsScriptError("Invalid cast");
//    return casted;
//  }
//
//  public static <T extends EsValue> boolean IsType(@NotNull EsValue<?> value, @NotNull Class<T> type) {
//    if (type == EsValue.class) return true;
//    if (value.getClass().equals(type)) return true;
//    T casted = value.cast(type);
//    return casted != null;

//  }

  public abstract String asString();

  public abstract V unwrap();

  public @NotNull EsValue<?> operatorPlus(@NotNull EsValue<?> other) {
    throw new UnsupportedOperationException();
  }

  public @NotNull EsValue<?> operatorMultiply(@NotNull EsValue<?> right) {
    throw new UnsupportedOperationException();
  }

  public <T extends EsValue> boolean isType(@NotNull Class<T> type) {
    if (type == EsValue.class) return true;
    if (getClass().equals(type)) return true;
    EsValue<?> casted = cast(type);
    return casted != null;
  }

  public <W, T extends EsValue<W>> @Nullable T cast(Class<T> to) {
    if (getClass() == to) return to.cast(this);
    return null;
  }

  @Override
  public String toString() {
    return getType().getName() + "[" + asString() + "]";
  }

  public abstract EsType getType();

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    return this;
  }

}
