package pl.moderr.eduscript.types;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;

public class EsUnit extends EsValue<Void> {

  @Contract(value = " -> new", pure = true)
  public static @NotNull EsUnit get() {
    return new EsUnit();
  }

  @Override
  public Void unwrap() {
    return null;
  }

  @Override
  public EsType getType() {
    return EsTypes.UNIT;
  }

  @Override
  public String asString() {
    return "nic";
  }

}
