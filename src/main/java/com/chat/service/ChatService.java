package com.chat.service;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.rtm.RTMStartRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.rtm.RTMStartResponse;
import com.slack.api.model.event.UserTypingEvent;
import com.slack.api.rtm.RTMClient;
import com.slack.api.rtm.RTMEventHandler;
import com.slack.api.rtm.RTMEventsDispatcher;
import com.slack.api.rtm.RTMEventsDispatcherFactory;
import com.slack.api.rtm.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.stereotype.Service;

import com.chat.dao.Chat;
import com.chat.repository.ChatRepository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


import javax.servlet.http.HttpSession;

@Service
public class ChatService {


	@Autowired
	ChatRepository chatRepository;

	@Autowired
	HttpSession session;

	int id;


  private SimpMessagingTemplate template;

  @Autowired
  public ChatService(SimpMessagingTemplate template) {
    this.template = template;
  }


  /*
	 * Post message
	 */

	public boolean post(String room, String name, String message) {

    System.out.println(template.getMessageChannel());
    this.template.convertAndSend("/message",  name + ": " + message);



		Chat latestId = chatRepository.findTopByOrderByIdDesc();
		if(latestId != null) {
			id = latestId.getId()+1;
		}else {
		 id = 0;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		Chat chat = new Chat(id, name, message, formatter.format(date));

		if(chatRepository.save(chat) != null) {
			return true;
		}


		return false;
	}

	/*
	 * Get 5 latest chats
	 */
	public List<Chat> getFiveLatestChat(){
		Pageable pageable = PageRequest.of(0, 5, Sort.by(Order.desc("id")));

		//last 5 chats
		Page<Chat> allChatPage = chatRepository.findAll(pageable);
		List<Chat> allChatArray = allChatPage.getContent();
		ArrayList<Chat> chats = new ArrayList<>();
		if(allChatArray.size() > 1){
      for(int i = 0; i < allChatArray.size(); i++){
        chats.add(allChatArray.get(i));
      }
    }else{
		  chats.add(allChatArray.get(0));
    }

		Collections.reverse(chats);
		return chats;
	}

  public List<Chat> getAllChat(){

    //last 5 chats
    List<Chat> allChatPage = chatRepository.findAll();

    return allChatPage;
  }

  public void slack(Chat message){

    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);
    String token = "Token";
    MethodsClient methods = slack.methods(token);
    ChatPostMessageRequest request = ChatPostMessageRequest.builder()
      .channel("#chat")
      .username(message.getName())
      .text(message.getMessage())
      .build();

    try {
      ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
        .channel("#chat")
        .username(message.getName())
        .text(message.getMessage()));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SlackApiException e) {
      e.printStackTrace();
    }

  }

}
