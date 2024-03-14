package pl.moderr.eduscript.errors;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsPosition;

public class EsNameError extends EsRuntimeError {
  public EsNameError(String message) {
    super(message);
  }

  public EsNameError(int line, int column) {
    super(line, column);
  }

  public EsNameError(int line, int column, String message) {
    super(line, column, message);
  }

  public EsNameError(@NotNull EsPosition position, @NotNull String message) {
    super(position, message);
  }

  @Override
  protected @NotNull String prefix() {
    return "Błąd nazwy";
  }
}
