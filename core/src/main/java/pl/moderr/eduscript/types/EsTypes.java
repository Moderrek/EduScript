package pl.moderr.eduscript.types;

import pl.moderr.eduscript.ast.EsType;
import pl.moderr.eduscript.ast.SimpleEsType;

public final class EsTypes {

  public static final EsType INT = new SimpleEsType("calkowita");
  public static final EsType FLOAT = new SimpleEsType("liczba");
  public static final EsType BOOL = new SimpleEsType("logiczna");
  public static final EsType STR = new SimpleEsType("tekst");
  public static final EsType VAR = new SimpleEsType("wartosc");
  public static final EsType UNIT = new SimpleEsType("jednostka");

  private EsTypes() {

  }

}
