package pl.moderr.eduscript.parser;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.lexer.EsTokenCollection;

public abstract class EsParserBase<E> implements ParserMixinUtils {

  private EsTokenCollection tokens;
  private int pos;

  @Override
  public EsTokenCollection tokens() {
    return tokens;
  }

  @Override
  public int pos() {
    return pos;
  }

  @Override
  public int addPos(int n) {
    pos += n;
    return pos;
  }

  public E[] parse(@NotNull EsTokenCollection tokens) {
    this.tokens = tokens;
    this.pos = 0;
    return computeParse();
  }

  protected abstract E[] computeParse();

}
