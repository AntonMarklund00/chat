package com.chat;

import com.chat.config.ChatWebSocketHandler;
import com.chat.service.ChatService;
import com.slack.api.Slack;
import com.slack.api.model.event.UserTypingEvent;
import com.slack.api.rtm.*;
import com.slack.api.rtm.message.*;
import com.chat.dao.Chat;
import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.model.Message;
import com.slack.api.model.event.UserTypingEvent;
import com.slack.api.rtm.RTMClient;
import com.slack.api.rtm.RTMEventHandler;
import com.slack.api.rtm.RTMEventsDispatcher;
import com.slack.api.rtm.RTMEventsDispatcherFactory;
import com.slack.api.rtm.message.PresenceQuery;
import com.slack.api.rtm.message.PresenceSub;
import com.slack.api.webhook.Payload;
import org.riversun.slacklet.Slacklet;
import org.riversun.slacklet.SlackletRequest;
import org.riversun.slacklet.SlackletResponse;
import org.riversun.slacklet.SlackletService;
import org.riversun.xternal.simpleslackapi.SlackChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);

    String botToken = "Token";
    SlackletService slackService = new SlackletService(botToken);
    slackService.addSlacklet(new Slacklet() {
      @Override
      public void onMessagePosted(SlackletRequest req, SlackletResponse resp) {

        // get message content
        String content = req.getContent();
        TextMessage message = new TextMessage(content);
        ChatWebSocketHandler.sendMessageFromSlack(message);

      }
    });

    try {
      slackService.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
