package pl.moderr.eduscript.vm;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.ast.EsExpression;

import java.util.*;

public class EsScript {

  private final EsInstance instance;
  private final UUID id;
  private final Stack<EsExpression> callStack;

  public EsScript(EsInstance instance, UUID uuid) {
    this.instance = instance;
    this.id = uuid;
    this.callStack = new Stack<>();
  }

  public UUID getId() {
    return id;
  }

  public Map<String, EsVariable> getVariables() {
    return data().getVariables();
  }

  public EduScriptData data() {
    return instance.scripts.get(this);
  }

  public Set<String> getDeclaredNames() {
    return data().getVariables().keySet();
  }

  public EsInstance getEsInstance() {
    return instance;
  }

  public boolean hasDefinedVariable(@NotNull String identifier) {
    return data().getVariable(identifier).isPresent();
  }

  public Optional<EsVariable> findVariable(@NotNull String identifier) {
    Optional<EsVariable> local = data().getVariable(identifier);
    if (local.isPresent()) return local;
    return getEsInstance().global.getVariable(identifier);
  }

  public @Nullable Object rawValue(@NotNull String identifier) {
    return data().getVariable(identifier).map(EsVariable::unwrap).orElse(null);
  }

  public void cleanup() {
    instance.removeScript(this);
  }

  public void run(@NotNull String code) {
    instance.run(this, code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    EsScript esScript = (EsScript) object;
    return Objects.equals(id, esScript.id);
  }

  public Stack<EsExpression> getCallStack() {
    return callStack;
  }
}
