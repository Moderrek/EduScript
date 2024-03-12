package pl.moderr.eduscript.types;

import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;

public class EsFloat extends EsValue<Double> {

  private final double value;

  public EsFloat() {
    this.value = 0;
  }

  public EsFloat(double value) {
    this.value = value;
  }

  @Override
  public Double unwrap() {
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
