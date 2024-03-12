package pl.moderr.eduscript.vm;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.types.EsBool;
import pl.moderr.eduscript.types.EsFloat;
import pl.moderr.eduscript.types.EsInt;
import pl.moderr.eduscript.types.EsStr;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EduScriptData {

  private final Map<String, EsVariable> variables;

  public EduScriptData() {
    variables = new ConcurrentHashMap<>();
  }

  public Map<String, EsVariable> getVariables() {
    return variables;
  }

  public boolean isVariableDefined(@NotNull String identifier) {
    return variables.containsKey(identifier);
  }

  public Optional<EsVariable> getVariable(@NotNull String identifier) {
    return Optional.ofNullable(variables.get(identifier));
  }

  public void setVariable(@NotNull String identifier, int value) {
    setVariable(identifier, EsVariable.Mutable(identifier, new EsInt(value)));
  }

  public void setVariable(@NotNull String identifier, @Nullable EsVariable value) {
    if (value == null) {
      variables.remove(identifier);
      return;
    }
    variables.put(identifier, value);
  }

  public void setVariable(@NotNull String identifier, String value) {
    setVariable(identifier, EsVariable.Mutable(identifier, new EsStr(value)));
  }

  public void setVariable(@NotNull String identifier, boolean value) {
    setVariable(identifier, EsVariable.Mutable(identifier, new EsBool(value)));
  }

  public void setVariable(@NotNull String identifier, double value) {
    setVariable(identifier, EsVariable.Mutable(identifier, new EsFloat(value)));
  }

}
