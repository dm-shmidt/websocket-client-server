package org.dms.wbsserver.server;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ServerHandler implements WebSocketHandler {

  private  final ServerLogic serverLogic;
  @Override
  @NonNull
  public Mono<Void> handle(@NonNull WebSocketSession session) {
    return serverLogic.doLogic(session);
  }
}