package pl.moderr.eduscript;

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

  @Override
  public String toString() {
    return "EsParseError | " + start().toString() + ": " + getMessage();
  }
}
