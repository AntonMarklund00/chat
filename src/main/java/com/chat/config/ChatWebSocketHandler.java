package com.chat.config;

import com.chat.controller.ChatController;
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
  ChatController chatController;

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
}
