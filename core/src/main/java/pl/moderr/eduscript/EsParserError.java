package pl.moderr.eduscript;

import org.jetbrains.annotations.NotNull;

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
  public String toString() {
    return "Błąd analizy | " + start().toString() + ": " + getMessage();
  }
}
