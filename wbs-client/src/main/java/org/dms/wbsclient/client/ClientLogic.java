package org.dms.wbsclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.time.LocalDateTime;
import org.dms.dto.CpuUsageDto;
import org.dms.wbsclient.CpuInfo;
import reactor.core.publisher.Flux;

public class ClientLogic {

  private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

  public void doLogic(Client client, long sendInterval) {

    Flux.interval(Duration.ofSeconds(sendInterval))
        .doOnNext(n -> {
          try {
            client.send(
                mapper.writeValueAsString(
                    new CpuUsageDto(LocalDateTime.now(), CpuInfo.getCpuUsage())));
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        })
        .takeWhile(n -> n < 5)
        .subscribe();
  }

}
