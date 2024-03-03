package pl.moderr.eduscript.lexer;

public final class EsTokenKinds {

  private EsTokenKinds() { }

  public static final EsTokenKind INTEGER = new EsTokenKind("calk", false);
  public static final EsTokenKind KEYWORD = new EsTokenKind("keyword", false);
  public static final EsTokenKind IDENTIFIER = new EsTokenKind("identifier", false);
  public static final EsTokenKind STRING = new EsTokenKind("string", false);
  public static final EsTokenKind INSTRUCTION_END = new EsTokenKind("instruction end", false);
  public static final EsTokenKind PAREN_LEFT = new EsTokenKind("parentheses left", false);
  public static final EsTokenKind PAREN_RIGHT = new EsTokenKind("parentheses right", false);
  public static final EsTokenKind CURLY_LEFT = new EsTokenKind("curly left", false);
  public static final EsTokenKind CURLY_RIGHT = new EsTokenKind("curly right", false);
  public static final EsTokenKind BRACKET_LEFT = new EsTokenKind("bracket left", false);
  public static final EsTokenKind BRACKET_RIGHT = new EsTokenKind("bracket right", false);
  public static final EsTokenKind SEPARATOR_COMMA = new EsTokenKind("separator", false);
  public static final EsTokenKind PLUS = new EsTokenKind("plus", true);
  public static final EsTokenKind MINUS = new EsTokenKind("minus", true);
  public static final EsTokenKind TRUE = new EsTokenKind("true", false);
  public static final EsTokenKind ASSIGN = new EsTokenKind("assign", false);
  public static final EsTokenKind LET = new EsTokenKind("let", false);
  public static final EsTokenKind CONST = new EsTokenKind("const", false);
  public static final EsTokenKind FALSE = new EsTokenKind("false", false);
  public static final EsTokenKind IF = new EsTokenKind("if", false);
  public static final EsTokenKind WHILE = new EsTokenKind("while", false);
  public static final EsTokenKind MATCH = new EsTokenKind("match", false);
  public static final EsTokenKind PLUS_ASSIGN = new EsTokenKind("plus assign", true);
  public static final EsTokenKind MULTIPLY = new EsTokenKind("multiply", true);
  public static final EsTokenKind MULTIPLY_ASSIGN = new EsTokenKind("multiply assign", true);
  public static final EsTokenKind DIVIDE = new EsTokenKind("divide", true);
  public static final EsTokenKind DIVIDE_ASSIGN = new EsTokenKind("divide assign", false);
  public static final EsTokenKind EQUAL = new EsTokenKind("equal", false);
  public static final EsTokenKind NOT_EQUAL = new EsTokenKind("not equal", true);
  public static final EsTokenKind NEGATE = new EsTokenKind("negate", false);
  public static final EsTokenKind GREATER = new EsTokenKind("negate", true);
  public static final EsTokenKind LESS = new EsTokenKind("less", true);
  public static final EsTokenKind GREATER_EQUAL = new EsTokenKind("greater or equal", true);
  public static final EsTokenKind LESS_EQUAL = new EsTokenKind("less or equal", true);
  public static final EsTokenKind MODULO = new EsTokenKind("modulo", true);
  public static final EsTokenKind MODULO_ASSIGN = new EsTokenKind("modulo assign", true);
  public static final EsTokenKind MINUS_ASSIGN = new EsTokenKind("minus assign", true);


}
