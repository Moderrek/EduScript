package pl.moderr.eduscript.lexer;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsPosition;
import pl.moderr.eduscript.Positionable;

public final class EsToken implements Positionable {

  private final EsTokenKind kind;
  private final String value;
  private final EsPosition start;
  private final EsPosition end;

  public EsToken(EsTokenKind kind, @NotNull String value, EsPosition start, EsPosition end) {
    this.kind = kind;
    this.value = value;
    this.start = start;
    this.end = end;
  }

  public static @NotNull EsToken integer(@NotNull String value, @NotNull EsPosition start, @NotNull EsPosition end) {
    return new EsToken(EsTokenKind.INTEGER, value, start, end);
  }

  public static @NotNull EsToken string(@NotNull String value, @NotNull EsPosition start, @NotNull EsPosition end) {
    return new EsToken(EsTokenKind.STRING, value, start, end);
  }

  public static @NotNull EsToken identifier(@NotNull String value, @NotNull EsPosition start, @NotNull EsPosition end) {
    return new EsToken(EsTokenKind.IDENTIFIER, value, start, end);
  }

  public EsTokenKind getKind() {
    return kind;
  }

  public boolean match(@NotNull EsTokenKind other) {
    return this.kind == other;
  }

  public boolean match(@NotNull EsToken token) {
    return this.kind == token.kind;
  }

  public static boolean match(@NotNull EsToken token, @NotNull EsToken other) {
    return token.match(other);
  }

  @Override
  public @NotNull EsPosition getStart() {
    return start;
  }

  public EsPosition getEnd() {
    return end;
  }

  public String getValue() {
    return value;
  }

  @Override
  public @NotNull String toString() {
    return getKind() + " {" + getValue() + "} " + getStart() + "->" + getEnd();
  }
}
