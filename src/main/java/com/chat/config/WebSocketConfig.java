package com.chat.config;

import com.chat.service.ChatService;
import org.riversun.slacklet.Slacklet;
import org.riversun.slacklet.SlackletRequest;
import org.riversun.slacklet.SlackletResponse;
import org.riversun.slacklet.SlackletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

import java.io.IOException;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {


  @Autowired
  ChatService chatService;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/socket")
      .setAllowedOrigins("*")
      .withSockJS();
  }
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app")
      .enableSimpleBroker("/message");
  }

  public void slack(){
    String botToken = "Token";
    SlackletService slackService = new SlackletService(botToken);
    slackService.addSlacklet(new Slacklet() {
      @Override
      public void onMessagePosted(SlackletRequest req, SlackletResponse resp) {

        // get message content
        String content = req.getContent();
        if(content.startsWith("%")){
          String displayC = content.replace("%", "");
          System.out.println("asd");
          chatService.post(null, "Admin", displayC);

        }
      }
    });

    try {
      slackService.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
