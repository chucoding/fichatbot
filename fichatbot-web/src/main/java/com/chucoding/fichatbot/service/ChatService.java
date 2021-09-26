package com.chucoding.fichatbot.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chucoding.fichatbot.dao.ChatDao;
import com.chucoding.fichatbot.util.CacheUtils;

@Service
public class ChatService {

	@Autowired CacheUtils cache;
	private ChatDao chatDao = new ChatDao();
	
	public Map open() {
		Map resp = chatDao.open();
		
		Map return_object = MapUtils.getMap(resp, "return_object");
		String uuid = MapUtils.getString(return_object,"uuid");
	
		cache.put("uuid", uuid);
		return makeTemplate(resp);
	}
	
	public Map message(Map<String, Object> data) {
		String uuid = (String) cache.get("uuid");
		Map req = new HashMap();
		req.put("uuid", uuid);
		
		Map map = MapUtils.getMap(data, "data");
		req.put("text", MapUtils.getString(map, "text"));
		
		return makeTemplate(chatDao.message(req));
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> makeTemplate(Map<String, Object> resp) {
		
		Map<String, Object> map = new HashMap();
		Map<String, Object> chatbotInfo = new HashMap();
		
		Map<String, Object> return_object = (Map<String, Object>) MapUtils.getMap(resp, "return_object");
		Map<String, Object> result = (Map<String, Object>) MapUtils.getMap(return_object, "result");
		chatbotInfo.put("id","user");
		
		map.put("id","chatbot");
		map.put("text",MapUtils.getString(result,"system_text"));
		map.put("createdAt",new Date());
		map.put("user",chatbotInfo);
		
		System.out.println(map);
		
		return map;
	}
	
}
