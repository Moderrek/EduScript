package pl.moderr.eduscript.lexer;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.EsPosition;
import pl.moderr.eduscript.errors.EsSyntaxError;

import java.util.HashMap;
import java.util.Map;

import static pl.moderr.eduscript.lexer.EsTokenKinds.*;

public class EsLexer implements LexerMixinUtils {

  private static final Map<String, EsTokenKind> keywords;
  private static final Map<Character, EsTokenKind> operators_short;
  private static final Map<String, EsTokenKind> operators;

  static {
    operators_short = Map.of(
        '(', PAREN_LEFT,
        '{', CURLY_LEFT,
        '}', CURLY_RIGHT,
        '[', BRACKET_LEFT,
        ']', BRACKET_RIGHT,
        ')', PAREN_RIGHT,
        ',', SEPARATOR_COMMA
    );

    operators = new HashMap<>();
    operators.put("=", ASSIGN);
    operators.put("+", PLUS);
    operators.put("+=", PLUS_ASSIGN);
    operators.put("-", MINUS);
    operators.put("-=", MINUS_ASSIGN);
    operators.put("*", MULTIPLY);
    operators.put("*=", MULTIPLY_ASSIGN);
    operators.put("/", DIVIDE);
    operators.put("/=", DIVIDE_ASSIGN);
    operators.put("==", EQUAL);
    operators.put("!=", NOT_EQUAL);
    operators.put("~=", NOT_EQUAL);
    operators.put("~", NEGATE);
    operators.put("!", NEGATE);
    operators.put(">", GREATER);
    operators.put("<", LESS);
    operators.put(">=", GREATER_EQUAL);
    operators.put("<=", LESS_EQUAL);
    operators.put("%", MODULO);
    operators.put("%=", MODULO_ASSIGN);

    keywords = new HashMap<>();
    keywords.put("zmien", LET);
    keywords.put("stala", CONST);
    keywords.put("prawda", TRUE);
    keywords.put("falsz", FALSE);
    keywords.put("jezeli", IF);
    keywords.put("dopoki", WHILE);
    keywords.put("dopasuj", MATCH);
    keywords.put("zwroc", RETURN);
  }

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
        if (lastToken().isEmpty() || !lastToken().get().match(INSTRUCTION_END)) {
          EsPosition start = position.clone();
          tokens.add(new EsToken(INSTRUCTION_END, ";", start, position.clone()));
        }
        move();
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

        while (!end() && (digit().get() || symbol().get() == '.')) {
          char digit = symbolNext().get();
          if (digit == '.') {
            if (isDecimal)
              throw new EsSyntaxError(start, "Nie można rozpoznać liczby");
            isDecimal = true;
          }
          buffer.append(digit);
        }

        String number = buffer.toString();
        EsToken token = isDecimal ? new EsToken(DECIMAL, number, start, position.clone()) : EsToken.integer(number, start, position.clone());

        tokens.add(token);
        continue;
      }
      // Identifier or keyword token
      if (letter().get() || digit().get()) {
        StringBuilder buffer = new StringBuilder();
        EsPosition start = position.clone();
        while (!end() && (letter().get() || digit().get())) buffer.append(symbolNext().get());

        String name = buffer.toString();
        if (keywords.containsKey(name)) {
          EsTokenKind kind = keywords.get(name);
          EsToken token = new EsToken(kind, name, start, position.clone());
          tokens.add(token);
          continue;
        }
        EsToken token = EsToken.identifier(name, start, position.clone());
        tokens.add(token);
        continue;
      }
      // Identifier literal
      if (symbol().get() == '`') {
        StringBuilder buffer = new StringBuilder();
        EsPosition start = position.clone();
        boolean foundEnd = false;
        move();
        while (!end()) {
          if (symbol().get() == '`') {
            foundEnd = true;
            move();
            break;
          }
          buffer.append(symbolNext().get());
        }

        if (!foundEnd) {
          throw new EsSyntaxError(start(), "Nie można znaleźć końca nazwy.");
        }

        String string = buffer.toString();
        EsToken token = EsToken.identifier(string, start, position.clone());
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
          throw new EsSyntaxError(start(), "Nie można znaleźć końca ciągu znaku.");
        }

        String string = buffer.toString();
        EsToken token = EsToken.string(string, start, position.clone());
        tokens.add(token);
        continue;
      }

      if (operators_short.containsKey(symbol().get())) {
        EsPosition start = position.clone();
        char symbol = symbolNext().get();
        EsTokenKind kind = operators_short.get(symbol);
        EsToken token = new EsToken(kind, String.valueOf(symbol), start, position.clone());
        tokens.add(token);
        continue;
      }
      // others
      StringBuilder buffer = new StringBuilder();
      EsPosition start = position.clone();
      while (!end() && !whitespace().get() && !digit().get()) {
        buffer.append(symbolNext().get());
      }
      String symbol = buffer.toString();
      if (operators.containsKey(symbol)) {
        EsTokenKind kind = operators.get(symbol);
        EsToken token = new EsToken(kind, symbol, start, position.clone());
        tokens.add(token);
        continue;
      }

      throw new EsSyntaxError(position, "Nie rozpoznano '" + symbol + "'.");
    }

    return tokens;
  }

  @Override
  public @NotNull EsPosition start() {
    return position;
  }

  @Override
  public String input() {
    return input;
  }

  @Override
  public EsTokenCollection tokens() {
    return tokens;
  }

}
