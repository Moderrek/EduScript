package pl.moderr.eduscript.types;

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
  public Boolean unwrap() {
    return value;
  }

  @Override
  public EsType getType() {
    return EsTypes.BOOL;
  }

  @Override
  public String asString() {
    return value ? "tak" : "nie";
  }

}
