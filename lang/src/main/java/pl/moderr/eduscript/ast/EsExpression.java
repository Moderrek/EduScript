package pl.moderr.eduscript.ast;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.vm.EsScript;

public abstract class EsExpression implements Cloneable {

  public abstract EsValue<?> evaluate(@NotNull EsScript script);

  @Override
  public EsExpression clone() {
    try {
      return (EsExpression) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
