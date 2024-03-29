package pl.moderr.eduscript.lexer;

public final class EsTokenKinds {

  public static final EsTokenKind INTEGER = new EsTokenKind("calkowita", false);
  public static final EsTokenKind KEYWORD = new EsTokenKind("słowo kluczowe", false);
  public static final EsTokenKind IDENTIFIER = new EsTokenKind("identyfikator", false);
  public static final EsTokenKind STRING = new EsTokenKind("tekst", false);
  public static final EsTokenKind INSTRUCTION_END = new EsTokenKind("średnik", false);
  public static final EsTokenKind PAREN_LEFT = new EsTokenKind("otwarty nawias okrągły", false);
  public static final EsTokenKind PAREN_RIGHT = new EsTokenKind("zamknięty nawias okrągły", false);
  public static final EsTokenKind CURLY_LEFT = new EsTokenKind("otwarty nawias sześcienny", false);
  public static final EsTokenKind CURLY_RIGHT = new EsTokenKind("zamknięty nawias sześcienny", false);
  public static final EsTokenKind BRACKET_LEFT = new EsTokenKind("otwarty nawias kwadratowy", false);
  public static final EsTokenKind BRACKET_RIGHT = new EsTokenKind("zamknięty nawias kwadratowy", false);
  public static final EsTokenKind SEPARATOR_COMMA = new EsTokenKind("przecinek", false);
  public static final EsTokenKind PLUS = new EsTokenKind("plus", true)
      .operatorPrecedence(1);
  public static final EsTokenKind MINUS = new EsTokenKind("minus", true)
      .operatorPrecedence(1);
  public static final EsTokenKind TRUE = new EsTokenKind("prawda", false);
  public static final EsTokenKind ASSIGN = new EsTokenKind("przypisanie", false);
  public static final EsTokenKind LET = new EsTokenKind("definicja zmiennej", false);
  public static final EsTokenKind CONST = new EsTokenKind("stala", false);
  public static final EsTokenKind FALSE = new EsTokenKind("falsz", false);
  public static final EsTokenKind IF = new EsTokenKind("jezeli", false);
  public static final EsTokenKind WHILE = new EsTokenKind("dopoki", false);
  public static final EsTokenKind MATCH = new EsTokenKind("dopasuj", false);
  public static final EsTokenKind PLUS_ASSIGN = new EsTokenKind("plus assign", true);
  public static final EsTokenKind MULTIPLY = new EsTokenKind("multiply", true)
      .operatorPrecedence(2);
  public static final EsTokenKind MULTIPLY_ASSIGN = new EsTokenKind("multiply assign", true);
  public static final EsTokenKind DIVIDE = new EsTokenKind("divide", true);
  public static final EsTokenKind DIVIDE_ASSIGN = new EsTokenKind("divide assign", false);
  public static final EsTokenKind EQUAL = new EsTokenKind("equal", false)
      .operatorPrecedence(0);
  public static final EsTokenKind NOT_EQUAL = new EsTokenKind("not equal", true);
  public static final EsTokenKind NEGATE = new EsTokenKind("negate", false);
  public static final EsTokenKind GREATER = new EsTokenKind("negate", true);
  public static final EsTokenKind LESS = new EsTokenKind("less", true);
  public static final EsTokenKind GREATER_EQUAL = new EsTokenKind("greater or equal", true);
  public static final EsTokenKind LESS_EQUAL = new EsTokenKind("less or equal", true);
  public static final EsTokenKind MODULO = new EsTokenKind("modulo", true);
  public static final EsTokenKind MODULO_ASSIGN = new EsTokenKind("modulo assign", true);
  public static final EsTokenKind MINUS_ASSIGN = new EsTokenKind("minus assign", true);
  public static final EsTokenKind RETURN = new EsTokenKind("zwroc", false);
  public static final EsTokenKind DECIMAL = new EsTokenKind("liczba", false);

  private EsTokenKinds() {
  }

}
