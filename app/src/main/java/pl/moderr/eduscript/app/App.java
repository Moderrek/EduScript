package pl.moderr.eduscript.app;

import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.lexer.EsToken;
import pl.moderr.eduscript.lexer.EsTokenCollection;

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
            EsTokenCollection tokens = esTokenize(
                """
                    let a = 5;
                    "Hello World!";
                    1235;
                    abc;"""
            );
            for (EsToken token : tokens) {
                System.out.println(token);
            }
        } catch (EsScriptError error) {
            System.out.println(error.toString());
        }
    }

}
