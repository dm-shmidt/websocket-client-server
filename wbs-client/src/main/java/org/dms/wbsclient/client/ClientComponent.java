package org.dms.wbsclient.client;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.time.Duration;
import java.util.Objects;
import javax.net.ssl.KeyManagerFactory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

@Component
public class ClientComponent implements ApplicationListener<RunFetchingEvent> {

  private final ConfigurableApplicationContext applicationContext;

  @Value("${ws.port}")
  private int serverPort;

  @Value("${ws.path}")
  private String wsPath;

  @Value("${ws.send-interval}")
  private long sendInterval;

  @Value("${server.ssl.trust-store}")
  private String trustStoreFile;

  @Value("${server.ssl.trust-store-password}")
  private String trustStorePassword;

  public ClientComponent(ConfigurableApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override
  public void onApplicationEvent(@NonNull RunFetchingEvent event) {
    HttpClient httpClient = HttpClient.create().secure(spec ->
        spec.sslContext(Objects.requireNonNull(getSslContext())));

    WebSocketClient webSocketClient = new ReactorNettyWebSocketClient(httpClient);

    Client client = new Client();

    client.connect(webSocketClient, getURI());

    new ClientLogic().doLogic(client, sendInterval);

    Mono
        .delay(Duration.ofSeconds(event.getSecondsToFetch()))
        .publishOn(Schedulers.boundedElastic())
        .subscribe(value -> {
          client.disconnect();
        });
  }

  private URI getURI() {
    try {
      String host;
      if (System.getenv("host") == null) {
        host = "127.0.0.1";
      } else {
        host = System.getenv("host");
      }
      return new URI("wss://" + host + ":" + serverPort + wsPath);
    } catch (URISyntaxException USe) {
      throw new IllegalArgumentException(USe);
    }
  }

  private SslContext getSslContext() {
    try {
      final var keyManagerFactory = KeyManagerFactory.getInstance(
          KeyManagerFactory.getDefaultAlgorithm());
      try (InputStream file = applicationContext.getResource(trustStoreFile).getInputStream()) {
        final var keyStore = KeyStore.getInstance("JKS");
        keyStore.load(file, trustStorePassword.toCharArray());
        keyManagerFactory.init(keyStore, trustStorePassword.toCharArray());
      }
      return SslContextBuilder.forClient().keyManager(keyManagerFactory)
          .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
    } catch (final Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
