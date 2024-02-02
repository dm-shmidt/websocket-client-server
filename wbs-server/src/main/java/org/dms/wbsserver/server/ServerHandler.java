package org.dms.wbsserver.server;

import lombok.NonNull;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class ServerHandler implements WebSocketHandler {

  @Override
  @NonNull
  public Mono<Void> handle(@NonNull WebSocketSession session) {
    return new ServerLogic().doLogic(session);
  }
}