package pl.moderr.eduscript.vm;


import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.ast.EsExpression;
import pl.moderr.eduscript.ast.EsValue;

import java.util.*;

public class EsScript {

  private final EsInstance vm;
  private final UUID id;
  private final Stack<EsExpression> callStack;

  public EsScript(EsInstance machine, UUID uuid) {
    this.vm = machine;
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
    return vm.scripts.get(this);
  }

  public Set<String> getDeclaredNames() {
    return data().getVariables().keySet();
  }

  public Optional<EsVariable> getVariable(@NotNull String identifier) {
    Optional<EsVariable> local = data().getVariable(identifier);
    if (local.isPresent()) return local;
    return getVirtualMachine().global.getVariable(identifier);
  }

  public EsInstance getVirtualMachine() {
    return vm;
  }

  public void setVariable(@NotNull String identifier, @NotNull EsValue<?> value) {
    data().setVariable(identifier, EsVariable.Mutable(identifier, value));
  }

  public boolean hasDefinedVariable(@NotNull String identifier) {
    return data().getVariable(identifier).isPresent();
  }

  public void remove() {
    cleanup();
  }

  public void cleanup() {
    vm.removeScript(this);
  }

  public void run(@NotNull String code) {
    vm.run(this, code);
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
