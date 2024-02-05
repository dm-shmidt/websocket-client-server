package org.dms.wbsclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.dms.dto.CpuUsageDto;
import org.dms.wbsclient.CpuInfo;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ClientLogic {

  private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

  public void doLogic(Client client, long sendInterval) {

    Flux.interval(Duration.ofSeconds(sendInterval))
        .publishOn(Schedulers.boundedElastic())
        .takeUntil(n -> isSubscriptionClosed(client))
        .doOnNext(n -> {
          try {
            if (!isSubscriptionClosed(client)) {
              client.send(
                  mapper.writeValueAsString(
                      new CpuUsageDto(LocalDateTime.now(ZoneId.of("Europe/Prague")),
                          CpuInfo.getCpuUsage())));
            }
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        })
        .subscribe();
  }

  private boolean isSubscriptionClosed(Client client) {
    return client.subscription() == null || client.subscription().isDisposed();
  }

}
