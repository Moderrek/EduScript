package pl.moderr.eduscript.app;

import pl.moderr.eduscript.EsScriptError;
import pl.moderr.eduscript.vm.EsInstance;
import pl.moderr.eduscript.vm.EsScript;

import java.util.Arrays;

public class App {

  public static void main(String[] args) {
    boolean debug = Arrays.stream(args).toList().contains("--debug");
    try {
      new App().run();
    } catch (EsScriptError error) {
      if (debug) error.printStackTrace();
      System.out.println(error.getMessage());
    }
  }

  public void run() {
    // Create environment for running scripts.
    EsInstance eduscript = EsInstance.create();
    // Execute script.
    EsScript script = eduscript.run("""
        zmien a = 5;
        zmien b = 5 + a * 2;
        zmien c = pi;
        zmien d = 2 + 2 * 2;
        stala f = 3;
        """);
    // Modify script data after run.
    System.out.println(script.getDeclaredNames()); // local scope -> ["a", "b", "c", "d"]
    System.out.println(script.getVariable("b").get().unwrap()); // -> 15
    script.data().setVariable("a", 10);
    script.run("zmien b = 5 + a * 2;");
    System.out.println(script.getVariable("b").get().unwrap()); // -> 25
    System.out.println(script.getVariable("d").get().unwrap().equals(6));
    // Remove script data.
    script.cleanup();
  }

}
