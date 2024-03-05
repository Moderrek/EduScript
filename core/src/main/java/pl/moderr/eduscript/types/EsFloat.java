package pl.moderr.eduscript.types;

import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;

public class EsFloat extends EsValue<Float> {

  private final float value;

  public EsFloat() {
    this.value = 0;
  }

  public EsFloat(float value) {
    this.value = value;
  }

  @Override
  public Float unwrap() {
    return value;
  }

  @Override
  public EsType getType() {
    return EsTypes.FLOAT;
  }

  @Override
  public String asString() {
    return String.valueOf(value);
  }

}
