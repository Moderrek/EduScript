package pl.moderr.eduscript;

import org.jetbrains.annotations.NotNull;

/**
 * EsScriptError is the parent class of any exception.<br>
 * thrown when an error is encountered in EduScript code.
 * @since 1.0
 * @author Tymon Woźniak
 */
public class EsScriptError extends RuntimeException implements Positionable {

  private final EsPosition position;

  public EsScriptError(String message) {
    super(message);
    this.position = new EsPosition();
  }

  public EsScriptError(int line, int column) {
    super();
    this.position = new EsPosition(line, column);
  }

  public EsScriptError(int line, int column, String message) {
    super(message);
    this.position = new EsPosition(line, column);
  }

  @Override
  public @NotNull EsPosition getStart() {
    return position;
  }

  @Override
  public String toString() {
    return "EsError | " + getStart().toString() + ": " + getMessage();
  }
}
