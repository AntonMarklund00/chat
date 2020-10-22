package com.chat.controller;

import java.util.ArrayList;

import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.chat.dao.Chat;
import com.chat.service.ChatService;

@Controller
public class ChatController {


  @Autowired
  ChatService chatService;

  @MessageMapping("/send/message/")
  public void sendMessage(@Payload Chat message){
    chatService.slack(message);
    chatService.post(null, message.getName(), message.getMessage());
  }


  @GetMapping("get/start")
  public java.util.List<Chat> getFiveLatestChat(){
    return chatService.getFiveLatestChat();

  }

  /*@PostMapping("/post/{room}/{name}/{message}")
  public Boolean post(@PathVariable String room, @PathVariable String name, @PathVariable String message) {
    return chatService.post(room, name, message);
  }*/
}
