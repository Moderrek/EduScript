package pl.moderr.eduscript.vm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.EsPosition;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.errors.EsRuntimeError;
import pl.moderr.eduscript.types.EsVar;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class EsFunction<R extends EsValue> {

  protected EsScript script;
  protected @Nullable EsPosition definedAt;
  protected EsExpression[] args;
  protected Map<EsExpression, EsValue<?>> evaluatedArgs;

  public EsFunction() {
    this.definedAt = null;
  }

  public EsFunction(@Nullable EsPosition definedAt) {
    this.definedAt = definedAt;
  }

  protected abstract R invoke();

  protected EsRuntimeError createError(String message) {
    if (definedAt != null)
      return new EsRuntimeError(definedAt, message);
    return new EsRuntimeError(message);
  }

  public R evaluate(@NotNull EsScript script, @NotNull EsExpression[] args) {
    this.script = script;
    this.args = args;
    this.evaluatedArgs = new ConcurrentHashMap<>();

    return invoke();
  }

  protected @NotNull EsExpression[] args() {
    return this.args;
  }

  protected @NotNull EsInstance instance() {
    return this.script.getEsInstance();
  }

  protected @NotNull EsScript script() {
    return this.script;
  }

  protected void assertArgs(int argCount) {
    int givenArguments = args.length;
    if (argCount == 0 && givenArguments != argCount)
      throw createError(MessageFormat.format("Spodziewano się {1} argumentów, otrzymano {2}", argCount, givenArguments));
    if (givenArguments < argCount)
      throw createError(MessageFormat.format("Spodziewano się {1} argumentów, otrzymano {2}", argCount, givenArguments));
    if (givenArguments > argCount)
      throw createError(MessageFormat.format("Spodziewano się {1} argumentów, otrzymano {2}", argCount, givenArguments));
  }

  protected void assertArgType(int index, EsType expected) throws Exception {
    EsType got = arg(index, EsValue.class).orElseThrow().getType();
    // TODO type error
    if (expected != got)
      throw new EsRuntimeError(MessageFormat.format("Spodziewano się {0}, otrzymano {1}", expected.toString(), got.toString()));
  }

  protected <T extends EsValue> Optional<T> arg(int index, Class<T> type) {
    if (index >= args().length) return Optional.empty();
    EsExpression expression = args()[index];
    if (evaluatedArgs.containsKey(expression)) {
      EsValue value = evaluatedArgs.get(expression);
      try {
        return Optional.of((T) value);
      } catch (ClassCastException exception) {
        throw createError("expected " + type.toString() + ", got " + value.getClass().toString());
      }
    }
    EsValue value = args()[index].evaluate(script());
    if (args[index] instanceof EsVar var) {
      value = var.evaluate(script());
    }
    value = EsValue.safeCast(value.evaluate(script()), type);
    evaluatedArgs.put(expression, value);
    return Optional.of((T) value);
  }

}
