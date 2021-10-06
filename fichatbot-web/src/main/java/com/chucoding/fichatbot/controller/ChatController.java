package com.chucoding.fichatbot.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chucoding.fichatbot.service.ChatService;
import com.chucoding.fichatbot.utils.Utils;

@RestController
public class ChatController {
	
	@Autowired ChatService chatService;	
		
	@CrossOrigin("*")
	@PostMapping(value = "/chat/open")
	public Map open(@RequestBody Map<String, Object> data) {
		System.out.println(data);
		return chatService.open();
	}
	
	@CrossOrigin("*")
	@PostMapping(value = "/chat/message")
	public Map message(@RequestBody Map<String, Object> data, HttpServletRequest req) throws IOException {
		
		
		//Utils.stream(chatService.tts(data), res.getOutputStream());
		return chatService.message(data, req);
	}
}
