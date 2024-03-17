package pl.moderr.eduscript.errors;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsPosition;

public class EsSyntaxError extends EsScriptError {

  public EsSyntaxError(String message) {
    super(message);
  }

  public EsSyntaxError(int line, int column) {
    super(line, column);
  }

  public EsSyntaxError(int line, int column, String message) {
    super(line, column, message);
  }

  public EsSyntaxError(@NotNull EsPosition position, @NotNull String message) {
    super(position, message);
  }

  @Override
  protected @NotNull String prefix() {
    return "Błąd składni";
  }

}
