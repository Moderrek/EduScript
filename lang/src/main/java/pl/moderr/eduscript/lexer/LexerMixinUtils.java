package pl.moderr.eduscript.lexer;

import pl.moderr.eduscript.Positionable;

import java.util.Optional;

public interface LexerMixinUtils extends Positionable {

  default Optional<Character> nextSymbol() {
    move();
    return symbol();
  }

  default Optional<Character> symbolNext() {
    Optional<Character> symbol = symbol();
    move();
    return symbol;
  }

  default Optional<Boolean> whitespace() {
    Optional<Character> symbol = symbol();
    return symbol.map(Character::isWhitespace);
  }

  default Optional<Character> symbol() {
    if (end()) return Optional.empty();
    return Optional.of(at(pos()));
  }

  default boolean end() {
    return start().index() >= length();
  }

  default char at(int index) {
    return input().charAt(index);
  }

  default int pos() {
    return start().index();
  }

  default int length() {
    return input().length();
  }

  String input();

  default Optional<Boolean> digit() {
    Optional<Character> symbol = symbol();
    return symbol.map(Character::isDigit);
  }

  default Optional<Boolean> letter() {
    Optional<Character> symbol = symbol();
    return symbol.map(Character::isLetter);
  }

  default boolean move() {
    Optional<Character> symbol = symbol();
    if (symbol.isEmpty()) return false;
    start().move(symbol.get());
    return true;
  }

  default Optional<EsToken> lastToken() {
    EsTokenCollection tokens = tokens();
    if (tokens.isEmpty()) return Optional.empty();
    return Optional.of(tokens.get(tokens.size() - 1));
  }

  EsTokenCollection tokens();

}
