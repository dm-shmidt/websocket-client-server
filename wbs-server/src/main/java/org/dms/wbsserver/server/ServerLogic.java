package org.dms.wbsserver.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.dms.dto.CpuUsageDto;
import org.dms.wbsserver.service.CpuUsageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
public class ServerLogic {

  private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
  private final CpuUsageService cpuUsageService;

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public Mono<Void> doLogic(WebSocketSession session) {
    return
        session
            .receive()
            .map(WebSocketMessage::getPayloadAsText)
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(message -> {
              try {
                final var infoDto = mapper.readValue(message, CpuUsageDto.class);
                cpuUsageService.saveCpuUsage(infoDto).subscribe();
                logger.info("Server -> received from client id=[{}]: [{}]",
                    session.getId(), infoDto.toString());

              } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
              }
            })
            .then();
  }
}
