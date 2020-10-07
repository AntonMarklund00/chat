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

	@PostMapping("/post/{name}/{message}")
	public Boolean post(@PathVariable String name, @PathVariable String message) {
	  System.out.println("1");
		return chatService.post(name, message);
	}

	@GetMapping("get/start")
	public java.util.List<Chat> getFiveLatestChat(){
		return chatService.getFiveLatestChat();

	}


}
