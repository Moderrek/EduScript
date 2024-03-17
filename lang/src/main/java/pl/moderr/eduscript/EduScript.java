package pl.moderr.eduscript;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.lexer.EsLexer;
import pl.moderr.eduscript.lexer.EsTokenCollection;
import pl.moderr.eduscript.parser.EsParser;

public final class EduScript {

  public static @NotNull String VERSION = "PRE-0.5";

  private EduScript() {

  }

  public static @NotNull EsTokenCollection esTokenize(@NotNull String input) {
    EsLexer lexer = new EsLexer();
    return lexer.tokenize(input);
  }

  public static @NotNull EsExpression[] esParse(@NotNull EsTokenCollection tokens) {
    EsParser parser = new EsParser();
    return parser.parse(tokens);
  }

}
