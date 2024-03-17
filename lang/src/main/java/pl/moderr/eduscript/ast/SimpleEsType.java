package pl.moderr.eduscript.ast;

import org.jetbrains.annotations.NotNull;

public class SimpleEsType extends EsType {

  private final String name;

  public SimpleEsType(@NotNull String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
}
