package pl.moderr.eduscript.lexer;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsPosition;
import pl.moderr.eduscript.EsScriptError;

public class EsLexer implements LexerMixinUtils {

  private EsTokenCollection tokens;
  private EsPosition position;
  private String input;

  public EsTokenCollection tokenize(@NotNull String input) {
    this.tokens = new EsTokenCollection();
    this.position = new EsPosition();
    this.input = input;

    while (!end()) {
      // Instruction end
      if (symbol().get() == ';') {
        if (lastToken().isEmpty() || !lastToken().get().match(EsTokenKind.INSTRUCTION_END)) {
          EsPosition start = position.clone();
          move();
          tokens.add(new EsToken(EsTokenKind.INSTRUCTION_END, ";", start,  position.clone()));
        }
        continue;
      }

      if (whitespace().get()) {
        move();
        continue;
      }
      // Number token
      if (digit().get()) {
        StringBuilder buffer = new StringBuilder();
        EsPosition start = position.clone();
        boolean isDecimal = false;

        while (!end() && digit().get()) {
          char digit = symbolNext().get();
          buffer.append(digit);
        }

        String number = buffer.toString();
        EsToken token = EsToken.integer(number, start, position.clone());

        tokens.add(token);
        continue;
      }
      // Identifier or keyword token
      if (letter().get() || digit().get()) {
        StringBuilder buffer = new StringBuilder();
        EsPosition start = position.clone();
        while (!end() && (letter().get() || digit().get())) buffer.append(symbolNext().get());

        String name = buffer.toString();
        EsToken token = EsToken.identifier(name, start, position.clone());
        tokens.add(token);
        continue;
      }
      // String token
      if (symbol().get() == '"') {
        StringBuilder buffer = new StringBuilder();
        EsPosition start = position.clone();
        boolean foundEnd = false;
        move();
        while (!end()) {
          if (symbol().get() == '"') {
            foundEnd = true;
            move();
            break;
          }
          buffer.append(symbolNext().get());
        }

        if (!foundEnd) {
          throw new EsScriptError(getStart().getLine(), getStart().getCol(), "Cannot find end of string");
        }

        String string = buffer.toString();
        EsToken token = EsToken.string(string, start, position.clone());
        tokens.add(token);
        continue;
      }

      if (symbol().get() == '=') {
        move();
        continue;
      }

      throw new EsScriptError(position.line(), position.col(), "Unknown symbol '" + symbol().get() + "'");
    }

    return tokens;
  }

  @Override
  public EsTokenCollection tokens() {
    return tokens;
  }

  @Override
  public String input() {
    return input;
  }

  @Override
  public @NotNull EsPosition getStart() {
    return position;
  }

}
