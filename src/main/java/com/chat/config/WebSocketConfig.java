package com.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  private final static String chatEndpoint = "/ws";

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(getChatWebSocketHandler(), chatEndpoint);
  }


  @Bean
  public WebSocketHandler getChatWebSocketHandler(){
    return new ChatWebSocketHandler();
  }
}
