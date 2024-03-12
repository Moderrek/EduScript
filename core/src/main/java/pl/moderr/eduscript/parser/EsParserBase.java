package pl.moderr.eduscript.parser;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsParserError;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.lexer.EsTokenCollection;
import pl.moderr.eduscript.lexer.EsTokenKind;

import java.util.ArrayList;
import java.util.Optional;

public abstract class EsParserBase implements ParserMixinUtils {

  private EsTokenCollection tokens;
  private int pos;
  private ArrayList<EsExpression> expressions;

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

  public void addExpression(@NotNull EsExpression expression) {
    expressions.add(expression);
  }

  public Optional<EsToken> nextToken() {
    pos += 1;
    return token();
  }

  public Optional<EsToken> token() {
    if (!hasToken(pos)) return Optional.empty();
    return Optional.of(tokens.get(pos));
  }

  public boolean hasToken(int pos) {
    return !(pos < 0 || pos >= tokens.size());
  }

  public Optional<EsToken> lookTokenBack(int back) {
    return lookTokenAhead(back * -1);
  }

  public Optional<EsToken> lookTokenAhead(int ahead) {
    int index = pos + ahead;
    if (!hasToken(index)) return Optional.empty();
    return Optional.of(tokens.get(index));
  }

  public boolean match(EsTokenKind kind) {
    Optional<EsToken> token = tokenNext();
    return token.map(esToken -> esToken.match(kind))
        .orElse(false);
  }

  public Optional<EsToken> tokenNext() {
    if (!hasToken()) return Optional.empty();
    EsToken token = token().get();
    pos += 1;
    return Optional.of(token);
  }

  public boolean hasToken() {
    return hasToken(pos);
  }

  public boolean matchStay(EsTokenKind kind) {
    return token().map(token -> token.match(kind)).orElse(false);
  }

  public boolean match(EsTokenKind @NotNull ... kinds) {
    int offset = 0;
    for (EsTokenKind kind : kinds) {
      Optional<EsToken> token = lookTokenAhead(offset++);
      if (token.isEmpty()) return false;
      if (!token.get().match(kind)) return false;
    }
    pos += kinds.length;
    return true;
  }

  public EsToken consume(EsTokenKind kind) {
    Optional<EsToken> token = tokenNext();
    if (token.isEmpty())
      throw new EsParserError("Spodziewano się '" + kind + "'.");
    if (!token.get().match(kind))
      throw new EsParserError(
          token.get().start(),
          "Spodziewano się '" + kind + "', otrzymano '" + token.get().kind() + "'."
      );
    return token.get();
  }

  public EsExpression[] parse(@NotNull EsTokenCollection tokens) {
    this.tokens = tokens;
    this.pos = 0;
    this.expressions = new ArrayList<>();
    computeParse();
    return expressions.toArray(new EsExpression[expressions.size()]);
  }

  protected abstract void computeParse();

}
