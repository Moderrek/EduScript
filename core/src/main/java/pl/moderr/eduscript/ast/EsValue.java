package pl.moderr.eduscript.ast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.vm.EsScript;

public abstract class EsValue<V> extends EsExpression {

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

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    return this;
  }

}
