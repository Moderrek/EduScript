package pl.moderr.eduscript.parser;

import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.lexer.EsTokenCollection;

public interface ParserMixinUtils {

  EsTokenCollection tokens();
  int pos();
  int addPos(int n);

  default void move() {
    addPos(1);
  }

  default void move(int n) {
    addPos(n);
  }

  default boolean end() {
    return pos() <= tokens().size();
  }

}
