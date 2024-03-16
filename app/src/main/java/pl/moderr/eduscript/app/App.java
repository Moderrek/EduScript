package pl.moderr.eduscript.app;

import pl.moderr.eduscript.EduScript;
import pl.moderr.eduscript.errors.EsScriptError;
import pl.moderr.eduscript.vm.EsInstance;
import pl.moderr.eduscript.vm.EsScript;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {


  public static void main(String[] args) {
    boolean debug = Arrays.stream(args).toList().contains("--debug");
    try {
      new App().runTerminal(args);
    } catch (EsScriptError error) {
      if (debug) error.printStackTrace();
      System.err.println(error);
    }
  }

  public void runTerminal(String[] args) {
    // Create environment for running scripts.
    EsInstance eduscript = EsInstance.create();
    eduscript.statement = value -> {
      System.out.println(value.asString());
    };

    System.out.println("EduScript " + EduScript.VERSION + " created by Tymon Woźniak");
    System.out.println("Enter 'wyjdz' to exit");

    Scanner scanner = new Scanner(System.in);

    EsScript script = eduscript.run("");
    while (true) {
      System.out.print("> ");
      String code;
      try {
        code = scanner.nextLine();
      } catch (IllegalStateException e) {
        break;
      }
      if (code == null) break;
      if (List.of("wyjdz", "exit").contains(code)) break;
      try {
        script.run(code);
      } catch (EsScriptError err) {
        System.err.println(err.toString());
      }
    }
    // Remove script data.
    script.cleanup();
  }

}
