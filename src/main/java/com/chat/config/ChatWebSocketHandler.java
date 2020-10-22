package com.chat.config;

import org.riversun.slacklet.Slacklet;
import org.riversun.slacklet.SlackletRequest;
import org.riversun.slacklet.SlackletResponse;
import org.riversun.slacklet.SlackletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class ChatWebSocketHandler extends TextWebSocketHandler {

  private static SimpMessagingTemplate template;

  @Autowired
  public ChatWebSocketHandler(SimpMessagingTemplate template) {
    this.template = template;
  }
  public static void slack(){

    //ChatService chatService = new ChatService();
    String botToken = "TOKEN";
    SlackletService slackService = new SlackletService(botToken);
    slackService.addSlacklet(new Slacklet() {


      @Override
      public void onMessagePosted(SlackletRequest req, SlackletResponse resp) {

        // get message content
        String content = req.getContent();
        if(content.startsWith("%")){
          String displayC = content.replace("%", "");
          TextMessage message = new TextMessage(displayC);
          System.out.println("SNÄLLA");
          template.convertAndSend("/message",  "Admin" + ": " + message);


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
