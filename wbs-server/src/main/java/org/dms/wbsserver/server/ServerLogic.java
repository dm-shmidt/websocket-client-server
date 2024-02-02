package org.dms.wbsserver.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dms.dto.InfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class ServerLogic {

  private final ObjectMapper mapper = new ObjectMapper();

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public Mono<Void> doLogic(WebSocketSession session) {
    return
        session
            .receive()
            .map(WebSocketMessage::getPayloadAsText)
            .doOnNext(message -> {

              try {
                final var infoDto = mapper.readValue(message, InfoDto.class);
                logger.info("Server -> received from client id=[{}]: [{}]",
                    session.getId(), infoDto.toString());

              } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
              }
            })
            .then();
  }
}