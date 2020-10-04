package com.chat.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.dao.Chat;

public interface ChatRepository extends MongoRepository<Chat, String>{

	ArrayList<Chat> findAll();
	Chat findTopByOrderByIdDesc();
	ArrayList<Chat> findByIdGreaterThan(int id);
	
}
