package pl.moderr.eduscript.vm;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;
import pl.moderr.eduscript.lexer.EsLexer;
import pl.moderr.eduscript.lexer.EsTokenCollection;
import pl.moderr.eduscript.parser.EsParser;
import pl.moderr.eduscript.types.EsFunctionRef;
import pl.moderr.eduscript.types.EsStr;
import pl.moderr.eduscript.types.EsTypes;
import pl.moderr.eduscript.types.EsUnit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EsInstance {

  final EduScriptData global;
  final Map<EsScript, EduScriptData> scripts;
  public @NotNull Consumer<String> out;
  public @NotNull Consumer<String> debug;
  public @NotNull Consumer<EsValue<?>> statement;
  public boolean debugMode;

  public EsInstance() {
    this.out = System.out::print;
    this.debug = System.out::println;
    this.statement = value -> {
    };
    this.debugMode = false;
    this.scripts = new ConcurrentHashMap<>();
    this.global = new EduScriptData();
    global.setVariable("pi", Math.PI);
    global.setVariable("wypisz", new EsFunctionRef(new EsFunction() {
      @Override
      protected EsValue invoke() {
        for (int i = 0; i < args().length; i += 1) {
          EsValue val = (EsValue) arg(i, EsStr.class).get();
          instance().out.accept(val.asString());
        }
        instance().out.accept("\n");
        return EsUnit.get();
      }
    }));
    global.setVariable("typ", new EsFunctionRef(new EsFunction() {
      @Override
      protected EsValue invoke() {
        assertArgs(1);
        EsValue val = (EsValue) arg(0, EsValue.class).get();
        return EsValue.of(val.getType().getName());
      }
    }));
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
      EsValue<?> value = stmt.evaluate(script);
      if (value != null && value.getType() != EsTypes.UNIT) {
        statement.accept(value);
      }
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
      EsValue<?> value = stmt.evaluate(script);
      if (value != null && value.getType() != EsTypes.UNIT) {
        statement.accept(value);
      }
    }
    return script;
  }

  public EsScript createScript() {
    EsScript script = new EsScript(this, UUID.randomUUID());
    scripts.put(script, new EduScriptData());
    return script;
  }

}
