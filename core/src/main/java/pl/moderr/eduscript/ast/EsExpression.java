package pl.moderr.eduscript.ast;

public class EsExpression implements Cloneable {
  @Override
  public EsExpression clone() {
    try {
      return (EsExpression) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
