package pl.moderr.eduscript.app;

import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.lexer.EsTokenCollection;
import pl.moderr.eduscript.parser.EsParser;

import static pl.moderr.eduscript.EduScript.*;

public class App {

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        new App().run();
    }

    public void run() {
        System.out.println(getGreeting());
        System.out.println("Hello EduScript!");

        try {
            EsTokenCollection tokens = esTokenize("""
                zmien a = 2 + 2 * 2;""");
            for (EsToken token : tokens) {
                System.out.println(token);
            }
            System.out.println("Tokenized " + tokens.size() + " tokens.");
            EsParser parser = new EsParser();
            EsExpression[] ast = parser.parse(tokens);
            System.out.println("Parsed " + ast.length + " expressions.");
            for (EsExpression expr : ast) {
                System.out.println(expr.evaluate());
            }
        } catch (EsScriptError error) {
            error.printStackTrace();
            System.out.println(error.toString());
        }
    }

}
