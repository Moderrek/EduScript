package pl.moderr.eduscript.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.errors.EsScriptError;
import pl.moderr.eduscript.vm.EsInstance;
import pl.moderr.eduscript.vm.EsScript;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class EsServer extends WebSocketServer {
  private static final int TCP_PORT = 15836;

  private final Set<WebSocket> connections;

  public EsServer() {
    super(new InetSocketAddress(TCP_PORT));
    connections = new HashSet<>();
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    connections.add(conn);
    System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    connections.remove(conn);
    System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
  }

  @Override
  public void onMessage(@NotNull WebSocket conn, String message) {
    System.out.println("Message from client: " + message);

    EsInstance instance = EsInstance.create();
    instance.out = conn::send;
    long start = System.currentTimeMillis();
    EsScript script;
    try {
      script = instance.run(message);
    } catch (EsScriptError scriptError) {
      conn.send(scriptError.toString());
      scriptError.printStackTrace();
      return;
    } catch (Exception e) {
      conn.send("Failed to run script.");
      return;
    }
    long end = System.currentTimeMillis() - start;
    conn.send("\uD83D\uDEE0\uFE0F Wykonano w " + end + "ms.");
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    if (conn != null) {
      connections.remove(conn);
    }
    System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
  }

  @Override
  public void onStart() {
    System.out.println("Starting server");
  }
}
