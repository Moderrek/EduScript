package pl.moderr.eduscript.parser;

import pl.moderr.eduscript.lexer.EsTokenCollection;

public interface ParserMixinUtils {

  default void move() {
    addPos(1);
  }

  int addPos(int n);

  default void move(int n) {
    addPos(n);
  }

  default boolean end() {
    return pos() <= tokens().size();
  }

  int pos();

  EsTokenCollection tokens();

}
