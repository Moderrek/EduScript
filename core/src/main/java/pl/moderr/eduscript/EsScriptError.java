package pl.moderr.eduscript;

import org.jetbrains.annotations.NotNull;

/**
 * EsScriptError is the parent class of any exception.<br>
 * thrown when an error is encountered in EduScript code.
 *
 * @author Tymon Woźniak
 * @since 1.0
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

  public EsScriptError(@NotNull EsPosition position, @NotNull String message) {
    super(message);
    this.position = position;
  }

  @Override
  public String toString() {
    return "EsError | " + start().toString() + ": " + getMessage();
  }

  @Override
  public @NotNull EsPosition start() {
    return position;
  }
}
