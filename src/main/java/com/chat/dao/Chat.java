package com.chat.dao;

public class Chat {
	
	private int id;
	private String name;
	private String message;
	private String time;
	
	public Chat() {}
	
	public Chat(int id, String name, String message, String string) {
		super();
		this.id = id;
		this.time = string;
		this.name = name;
		this.message = message;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	

}
