package org.dms.wbsclient.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class ClientComponent implements ApplicationListener<ApplicationReadyEvent> {

  private final ConfigurableApplicationContext applicationContext;

  @Value("${ws.port}")
  private int serverPort;

  @Value("${ws.path}")
  private String samplePath;

  @Value("${ws.send-interval}")
  private long sendInterval;

  public ClientComponent(ConfigurableApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();

    Client client = new Client();

    client.connect(webSocketClient, getURI());

    new ClientLogic().doLogic(client, sendInterval);

    Mono
        .delay(Duration.ofSeconds(10))
        .publishOn(Schedulers.boundedElastic())
        .subscribe(value -> {
          client.disconnect();
          SpringApplication.exit(applicationContext, () -> 0);
        });
  }

  private URI getURI() {
    try {
      return new URI("ws://localhost:" + serverPort + samplePath);
    } catch (URISyntaxException USe) {
      throw new IllegalArgumentException(USe);
    }
  }
}
