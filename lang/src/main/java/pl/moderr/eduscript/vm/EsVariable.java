package pl.moderr.eduscript.vm;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.errors.EsScriptError;
import pl.moderr.eduscript.types.EsUnit;

import java.util.Objects;

public class EsVariable {

  private final String identifier;
  private final boolean isConst;
  private EsValue<?> value;

  public EsVariable(@NotNull String identifier, @NotNull EsValue<?> init, boolean isConst) {
    this.identifier = identifier;
    this.value = init;
    this.isConst = isConst;
  }

  @Contract(value = "_, _ -> new", pure = true)
  public static @NotNull EsVariable Const(String identifier, EsValue<?> init) {
    return new EsVariable(identifier, init, true);
  }

  @Contract(value = "_, _ -> new", pure = true)
  public static @NotNull EsVariable Mutable(@NotNull String identifier, @NotNull EsValue<?> init) {
    return new EsVariable(identifier, init, false);
  }

  public boolean isDefined() {
    return !value.isType(EsUnit.class);
  }

  public boolean isMutable() {
    return !isConst;
  }

  public @NotNull EsVariable set(@NotNull EsValue<?> value) {
    if (isConst())
      throw new EsScriptError("Nie można zmodyfikować wartości stałej!");
    this.value = value;
    return this;
  }

  public boolean isConst() {
    return isConst;
  }

  public @NotNull String getIdentifier() {
    return identifier;
  }

  public @NotNull EsValue<?> get() {
    return value;
  }

  public @NotNull Object unwrap() {
    return value.unwrap();
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier, value);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    EsVariable that = (EsVariable) object;
    return Objects.equals(value, that.value);
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
