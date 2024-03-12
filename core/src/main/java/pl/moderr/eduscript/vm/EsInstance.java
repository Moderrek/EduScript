package pl.moderr.eduscript.vm;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.lexer.EsLexer;
import pl.moderr.eduscript.lexer.EsTokenCollection;
import pl.moderr.eduscript.parser.EsParser;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EsInstance {

  final EduScriptData global;
  final Map<EsScript, EduScriptData> scripts;
  public Consumer<String> out;
  public Consumer<String> debug;
  public boolean debugMode;

  public EsInstance() {
    this.out = System.out::print;
    this.debug = System.out::println;
    this.debugMode = false;
    this.scripts = new ConcurrentHashMap<>();
    this.global = new EduScriptData();
    global.setVariable("pi", Math.PI);
  }

  public static @NotNull EsInstance create() {
    return new EsInstance();
  }

  public void debug(@NotNull String message) {
    if (!debugMode) return;
    if (debug == null) return;
    debug.accept(message);
  }

  public void removeScript(@NotNull EsScript script) {
    scripts.remove(script);
  }

  public @NotNull EsScript run(@NotNull EsScript script, @NotNull String code) {
    // Compute script
    EsTokenCollection tokens;
    {
      EsLexer lexer = new EsLexer();
      tokens = lexer.tokenize(code);
    }
    EsExpression[] statements;
    {
      EsParser parser = new EsParser();
      statements = parser.parse(tokens);
    }
    // Run
    for (EsExpression stmt : statements) {
      stmt.evaluate(script);
    }
    return script;
  }

  public @NotNull EsScript run(@NotNull String code) {
    // Compute script
    EsTokenCollection tokens;
    {
      EsLexer lexer = new EsLexer();
      tokens = lexer.tokenize(code);
    }
    EsExpression[] statements;
    {
      EsParser parser = new EsParser();
      statements = parser.parse(tokens);
    }
    // Run
    EsScript script = createScript();
    for (EsExpression stmt : statements) {
      stmt.evaluate(script);
    }
    return script;
  }

  public EsScript createScript() {
    EsScript script = new EsScript(this, UUID.randomUUID());
    scripts.put(script, new EduScriptData());
    return script;
  }

}
