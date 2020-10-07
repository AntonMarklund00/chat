package com.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.chat.dao.Chat;
import com.chat.repository.ChatRepository;

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

	/*
	 * Post message
	 */
	public boolean post(String message) {
		Chat latestId = chatRepository.findTopByOrderByIdDesc();
		if(latestId != null) {
			id = latestId.getId()+1;
		}else {
		 id = 0;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();

		Chat chat = new Chat(id, "Anton", message, formatter.format(date));

		if(chatRepository.save(chat) != null) {
			//return true;
		}
		return false;
	}


	/*
	 * Get 5 latest chats
	 */
	public List<Chat> getFiveLatestChat(){

		Pageable pageable = PageRequest.of(0, 6, Sort.by(Order.desc("id")));

		//last 5 chats
		Page<Chat> allChatPage = chatRepository.findAll(pageable);
		List<Chat> allChatArray = allChatPage.getContent();

		ArrayList<Chat> chats = new ArrayList<>();
		for(int i = 0; i < allChatArray.size()-1; i++){
      chats.add(allChatArray.get(i));
    }

		Collections.reverse(chats);
		return chats;
	}

  public List<Chat> getAllChat(){

    //last 5 chats
    List<Chat> allChatPage = chatRepository.findAll();

    return allChatPage;
  }

}
