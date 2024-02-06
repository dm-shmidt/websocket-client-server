package org.dms.wbsserver.server;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class ServerConfiguration {

  @Value("${ws.path}")
  private String wsPath;

  @Bean
  public HandlerMapping handlerMapping(final ServerHandler serverHandler) {
    Map<String, WebSocketHandler> handlerByPathMap = new HashMap<>();
    handlerByPathMap.put(wsPath, serverHandler);

    SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
    handlerMapping.setUrlMap(handlerByPathMap);
    handlerMapping.setOrder(-1);

    return handlerMapping;
  }

  @Bean
  public WebSocketHandlerAdapter handlerAdapter() {
    return new WebSocketHandlerAdapter();
  }
}
