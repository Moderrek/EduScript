package pl.moderr.eduscript.lexer;

public class EsTokenKind {

  private final String name;
  private final boolean operator;
  private int operatorPrecedence;

  public EsTokenKind(String name, boolean operator) {
    this.name = name;
    this.operator = operator;
    this.operatorPrecedence = -1;
  }

  public EsTokenKind operatorPrecedence(int value) {
    this.operatorPrecedence = value;
    return this;
  }

  public int getOperatorPrecedence() {
    return operatorPrecedence;
  }

  public boolean isOperator() {
    return operator;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
