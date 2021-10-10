package com.chucoding.fichatbot.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chucoding.fichatbot.service.ChatService;

@RestController
public class ChatController {
	
	@Autowired ChatService chatService;	
		
	@CrossOrigin("*")
	@PostMapping(value = "/chat/open")
	public Map<String, Object> open(@RequestBody Map<String, Object> data) {
		
		System.out.println(data);
		String store = (String) data.get("store");
		
		Map<String, Object> answer = new HashMap<String, Object>();
		String message = "";
		
		if(store.equals("KT")) {
			message = "어서오세요  KT 입니다. https://shop.kt.com/display/olhsPlan.do?plnDispNo=1017&cmpid=tf_shop_wire_180801-cpc-internettv_tvdirect-google-kt&gclid=Cj0KCQjw-4SLBhCVARIsACrhWLXGw6k0iQb-eOpdf8G1YcmMSHBYbIMGLm5gc0V8iCMfirYcFlp5AYkaApZcEALw_wcB";
		}
		else if(store.equals("LG")){
			message = "어서오세요 LG 입니다.";
		} else {
			message = "잘못된 지점명 입니다.";
		}
		
		answer.put("position", "left");
		answer.put("type", "text");
		answer.put("text", message);
		answer.put("date", new Date());
		
		//return answer;
		return chatService.open();
	}
	
	@CrossOrigin("*")
	@PostMapping(value = "/chat/message")
	public Map message(@RequestBody Map<String, Object> data, HttpServletRequest req) throws IOException {
		//Utils.stream(chatService.tts(data), res.getOutputStream());
		return chatService.message(data, req);
	}
}
