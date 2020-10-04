package com.chat.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.dao.Chat;
import com.chat.service.ChatService;

@RestController
public class ChatController {
	
	
	@Autowired
	ChatService chatService;
	
	@PostMapping("/post/{message}")
	public Boolean post(@PathVariable String message) {
		
		return chatService.post(message);
				
		
	}
	
	@GetMapping("allChat")
	public ArrayList<Chat> getAllChat(){
		
		return chatService.getAllChat();
	}
	
	
	@GetMapping("getLatest")
	public ArrayList<Chat> getLatest(){
		
		return chatService.getLatestChat();
	}

}
