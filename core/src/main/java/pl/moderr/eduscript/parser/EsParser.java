package pl.moderr.eduscript.parser;

import pl.moderr.eduscript.ast.EsExpression;

import java.util.ArrayList;

public class EsParser extends EsParserBase<EsExpression> {

  @Override
  protected EsExpression[] computeParse() {
    ArrayList<EsExpression> expressions = new ArrayList<>();
//    while (!end()) {
//
//    }
    return expressions.toArray(new EsExpression[expressions.size()]);
  }
}
