package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.service.UserService;

@RestController
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@GetMapping("hello")
	public String hello() {
		
		return userService.hello();
		
	}
	
	
	

}
