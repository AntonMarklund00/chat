package com.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.dao.Chat;
import com.chat.repository.ChatRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;    

@Service
public class ChatService {
	
	
	@Autowired
	ChatRepository chatRepository;

	
	int id;
	
	//next -> make as session
	Chat latestId;
	public boolean post(String message) {
		latestId = chatRepository.findTopByOrderByIdDesc();
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
	
	
	public ArrayList<Chat> getAllChat(){
		latestId = chatRepository.findTopByOrderByIdDesc();
		ArrayList<Chat> allChat = chatRepository.findAll();
		return allChat;
	}
	
	public ArrayList<Chat> getLatestChat(){
		if(latestId == null) {
			return null;
		}
		
		ArrayList<Chat> latest = chatRepository.findByIdGreaterThan(latestId.getId());
		latestId = chatRepository.findTopByOrderByIdDesc();
		return latest;
	}
	
}
