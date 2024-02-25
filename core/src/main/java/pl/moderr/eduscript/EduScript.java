package pl.moderr.eduscript;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.lexer.EsLexer;
import pl.moderr.eduscript.lexer.EsTokenCollection;

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

  }

}
