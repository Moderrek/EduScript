package pl.moderr.eduscript.errors;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsPosition;

public class EsParserError extends EsScriptError {

  public EsParserError(String message) {
    super(message);
  }

  public EsParserError(int line, int column) {
    super(line, column);
  }

  public EsParserError(int line, int column, String message) {
    super(line, column, message);
  }

  public EsParserError(@NotNull EsPosition pos, String message) {
    super(pos, message);
  }

  @Override
  protected @NotNull String prefix() {
    return "Błąd analizy";
  }

}
