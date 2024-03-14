package pl.moderr.eduscript.app;

import pl.moderr.eduscript.errors.EsScriptError;
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
      System.err.println(error);
    }
  }

  public void run() {
    // Create environment for running scripts.
    EsInstance eduscript = EsInstance.create();
    // Execute script.
    EsScript script = eduscript.run("""
        wypisz(prawda);
        """);
    // Remove script data.
    script.cleanup();
  }

}
