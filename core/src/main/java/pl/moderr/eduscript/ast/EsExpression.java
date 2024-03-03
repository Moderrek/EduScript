package pl.moderr.eduscript.ast;

public abstract class EsExpression implements Cloneable {

  public abstract EsValue<?> evaluate();

  @Override
  public EsExpression clone() {
    try {
      return (EsExpression) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
