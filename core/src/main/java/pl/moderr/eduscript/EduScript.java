package pl.moderr.eduscript;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.lexer.EsLexer;
import pl.moderr.eduscript.lexer.EsTokenCollection;
import pl.moderr.eduscript.parser.EsParser;

public final class EduScript {

  private EduScript() {

  }

  public static @NotNull EsTokenCollection esTokenize(@NotNull String input) {
    EsLexer lexer = new EsLexer();
    return lexer.tokenize(input);
  }

  @Contract(value = " -> new", pure = true)
  public static @NotNull EsTokenCollection esTokens() {
    return new EsTokenCollection();
  }

  public static void esRun(@NotNull String code) {
    EsLexer lexer = new EsLexer();
    EsParser parser = new EsParser();
    EsTokenCollection tokens = lexer.tokenize(code);
    EsExpression[] expressions = parser.parse(tokens);
    System.out.println(expressions.length);
  }

}
