package com.demo.xmppchat.database;

public class UserData {
	private String name , time , content , receiver;

	public UserData(String name,String time,String content,String receiver) {
		this.name = name;
		this.content = content;
		this.time = time;
		this.receiver = receiver;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return this.time;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiver() {
		return this.receiver;
	}
}
