package com.chat.config;

import com.chat.controller.ChatController;
import com.chat.service.ChatService;
import org.riversun.slacklet.Slacklet;
import org.riversun.slacklet.SlackletRequest;
import org.riversun.slacklet.SlackletResponse;
import org.riversun.slacklet.SlackletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatWebSocketHandler extends TextWebSocketHandler {

  @Autowired
  ChatService chatService;

  private static final List<WebSocketSession> webSocketSessions = new ArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    webSocketSessions.add(session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    for (WebSocketSession webSocketSession: webSocketSessions){
      webSocketSession.sendMessage(message);
    }
  }

  public static void sendMessageFromSlack(TextMessage message){
    for (WebSocketSession webSocketSession: webSocketSessions){
      try {
        webSocketSession.sendMessage(message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    webSocketSessions.remove(session);
  }


  public static void slack(){
    String botToken = "token";
    SlackletService slackService = new SlackletService(botToken);
    slackService.addSlacklet(new Slacklet() {
      @Override
      public void onMessagePosted(SlackletRequest req, SlackletResponse resp) {

        // get message content
        String content = req.getContent();
        if(content.startsWith("%")){
          String displayC = content.replace("%", "");
          TextMessage message = new TextMessage(displayC);
          ChatWebSocketHandler.sendMessageFromSlack(message);
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
