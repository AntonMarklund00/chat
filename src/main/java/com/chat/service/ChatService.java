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
	
	public void session(int id) {
		
		session.setAttribute("latestID", id);
		
	}
	
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
	 * Get latest chats
	 */
	
	
	
	public List<Chat> getAllChat(){
		//get last chat
		
		Pageable pageable = PageRequest.of(0, 5, Sort.by(Order.asc("id")));
		
		//last 5 chats
		Page<Chat> allChatPage = chatRepository.findAll(pageable);
		List<Chat> allChatArray = allChatPage.getContent();

		
		//Add last id to session
		Chat lastChat = allChatArray.get(allChatArray.size()-1);
		session(lastChat.getId());
		
		return allChatArray;
	}
	
	public ArrayList<Chat> getLatestChat(){
		if(session.getAttribute("latestID") == null) {
			return null;
		}
		
		ArrayList<Chat> latest = chatRepository.findByIdGreaterThan((int) session.getAttribute("latestID"));
		
		Chat lastChat = latest.get(latest.size());
		session(lastChat.getId());
		
		return latest;
	}
	
}
