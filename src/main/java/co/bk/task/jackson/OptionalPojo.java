package co.bk.task.jackson;

import java.util.Optional;

public class OptionalPojo {

  private final Optional<String> clientInfo;
  private final String serverAddress;

  public OptionalPojo(Optional<String> clientInfo, String serverAddress) {
    this.clientInfo = clientInfo;
    this.serverAddress = serverAddress;
  }

  public Optional<String> getClientInfo() {
    return clientInfo;
  }

  public String getServerAddress() {
    return serverAddress;
  }
}
