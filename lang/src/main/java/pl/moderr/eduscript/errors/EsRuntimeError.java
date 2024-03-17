package pl.moderr.eduscript.errors;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsPosition;

public class EsRuntimeError extends EsScriptError {

  public EsRuntimeError(String message) {
    super(message);
  }

  public EsRuntimeError(int line, int column) {
    super(line, column);
  }

  public EsRuntimeError(int line, int column, String message) {
    super(line, column, message);
  }

  public EsRuntimeError(@NotNull EsPosition position, @NotNull String message) {
    super(position, message);
  }

  @Override
  protected @NotNull String prefix() {
    return "Błąd wykonania";
  }

}
