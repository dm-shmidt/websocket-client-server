package org.dms.wbsserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class ServerLogic {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Mono<Void> doLogic(WebSocketSession session) {
        return
            session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(message -> logger.info("Server -> received from client id=[{}]: [{}]", session.getId(), message))
                .then();
    }
}