package pl.moderr.eduscript.lexer;

public class EsTokenKind {

  private final String name;
  private final boolean operator;

  public EsTokenKind(String name, boolean operator) {
    this.name = name;
    this.operator = operator;
  }

  public boolean isOperator() {
    return operator;
  }

  @Override
  public String toString() {
    return name;
  }
}
