package pl.moderr.eduscript.types;

import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;

public class EsUnit extends EsValue<Void> {

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
