package org.dms.wbsclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.dms.dto.InfoDto;
import reactor.core.publisher.Flux;

public class ClientLogic {

  private final ObjectMapper mapper = new ObjectMapper();

  private final static AtomicInteger MESSAGE_ID;

  static {
    MESSAGE_ID = new AtomicInteger(0);
  }

  public void doLogic(Client client, long sendInterval) {
    Flux.interval(Duration.ofSeconds(sendInterval))
        .doOnNext(n -> {
          try {
            client.send(
                mapper.writeValueAsString(
                    new InfoDto("Test message " + MESSAGE_ID.getAndIncrement(), n * 5)));
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        })
        .takeWhile(n -> n < 3)
        .subscribe();
  }

}
