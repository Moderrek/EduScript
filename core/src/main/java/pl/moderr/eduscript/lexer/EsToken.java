package pl.moderr.eduscript.lexer;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsPosition;
import pl.moderr.eduscript.Positionable;

public record EsToken(EsTokenKind kind, String value, EsPosition start, EsPosition end) implements Positionable {

  public EsToken(EsTokenKind kind, @NotNull String value, EsPosition start, EsPosition end) {
    this.kind = kind;
    this.value = value;
    this.start = start;
    this.end = end;
  }

  public static @NotNull EsToken integer(@NotNull String value, @NotNull EsPosition start, @NotNull EsPosition end) {
    return new EsToken(EsTokenKinds.INTEGER, value, start, end);
  }

  public static @NotNull EsToken string(@NotNull String value, @NotNull EsPosition start, @NotNull EsPosition end) {
    return new EsToken(EsTokenKinds.STRING, value, start, end);
  }

  public static @NotNull EsToken identifier(@NotNull String value, @NotNull EsPosition start, @NotNull EsPosition end) {
    return new EsToken(EsTokenKinds.IDENTIFIER, value, start, end);
  }

  public static boolean match(@NotNull EsToken token, @NotNull EsToken other) {
    return token.match(other);
  }

  public boolean match(@NotNull EsToken token) {
    return this.kind == token.kind;
  }

  public boolean match(@NotNull EsTokenKind other) {
    return this.kind == other;
  }

  @Override
  public @NotNull String toString() {
    return kind() + " {" + value() + "} " + start() + "->" + end();
  }

  @Override
  public @NotNull EsPosition start() {
    return start;
  }
}
