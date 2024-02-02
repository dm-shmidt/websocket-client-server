package org.dms.wbsclient.client;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
public class Client {


  private Sinks.Many<String> sendBuffer;
  private Disposable subscription;
  private WebSocketSession session;

  public void connect(WebSocketClient webSocketClient, URI uri) {
    sendBuffer = Sinks.many().unicast().onBackpressureBuffer();

    subscription =
        webSocketClient
            .execute(uri, this::handleSession)
            .then(Mono.fromRunnable(this::onClose))
            .subscribe();

    log.info("Client connected.");
  }

  public void disconnect() {
    if (subscription != null && !subscription.isDisposed()) {
      subscription.dispose();
      subscription = null;

      onClose();
    }

    log.info("Client disconnected.");
  }

  public void send(String message) {
    sendBuffer.tryEmitNext(message);
  }

  private Mono<Void> handleSession(WebSocketSession session) {
    onOpen(session);

    return session
        .send(sendBuffer
            .asFlux()
            .map(session::textMessage))
        .then();
  }

  private void onOpen(WebSocketSession session) {
    this.session = session;

    log.info("Session opened");
  }

  private void onClose() {
    session = null;

    log.info("Session closed");
  }
}
