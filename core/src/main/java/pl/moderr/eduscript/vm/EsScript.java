package pl.moderr.eduscript.vm;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.eduscript.ast.EsValue;

import java.util.*;

public class EsScript {

  private final EsInstance vm;
  private final UUID id;

  public EsScript(EsInstance machine, UUID uuid) {
    this.vm = machine;
    this.id = uuid;
  }

  public UUID getId() {
    return id;
  }

  public EsInstance getVirtualMachine() {
    return vm;
  }

  public EduScriptData data() {
    return vm.scripts.get(this);
  }

  public Map<String, EsValue<?>> getVariables() {
    return data().getVariables();
  }

  public Set<String> getDeclaredNames() {
    return data().getVariables().keySet();
  }

  public Optional<EsValue<?>> getVariable(@NotNull String identifier) {
    Optional<EsValue<?>> local = data().getVariable(identifier);
    if (local.isPresent()) return local;
    return getVirtualMachine().global.getVariable(identifier);
  }

  public void setVariable(@NotNull String identifier, @Nullable EsValue<?> value) {
    data().setVariable(identifier, value);
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
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    EsScript esScript = (EsScript) object;
    return Objects.equals(id, esScript.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
