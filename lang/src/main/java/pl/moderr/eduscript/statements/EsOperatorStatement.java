package pl.moderr.eduscript.statements;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.lexer.EsTokenKind;
import pl.moderr.eduscript.vm.EsScript;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EsOperatorStatement extends EsExpression {

  private final EsExpression expr_left;
  private final EsTokenKind operator;
  private final EsExpression expr_right;

  public EsOperatorStatement(EsExpression left, EsTokenKind op, EsExpression right) {
    this.expr_left = left;
    this.operator = op;
    this.expr_right = right;
  }

  @Override
  public EsValue<?> evaluate(@NotNull EsScript script) {
    EsValue<?> left = expr_left.evaluate(script);
    EsValue<?> right = expr_right.evaluate(script);

    String operatorMethodName = "operator" + operator.getName();
    Method operatorMethod;
    try {
      operatorMethod = left.getClass().getMethod(operatorMethodName, EsValue.class);
      try {
        return (EsValue<?>) operatorMethod.invoke(left, right);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    } catch (NoSuchMethodException ignored) {
    }

    throw new UnsupportedOperationException("Nie zdefiniowana operacja " + operator);
  }

}
