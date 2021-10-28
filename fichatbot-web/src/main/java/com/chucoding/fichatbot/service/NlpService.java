package com.chucoding.fichatbot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.chucoding.fichatbot.dao.NlpDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NlpService {

	private NlpDao nlpDao = new NlpDao();

	@SuppressWarnings("unchecked")
	public Map<String, Object> open() {
		
		Map<String, Object> res = new HashMap<String, Object>();	
		res.put("left", "chatbot");
		res.put("text", "안녕하세요 챗봇입니다. 무엇을 도와드릴까요?");
		res.put("createdAt", new Date());
	
		return res;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> message(Map<String, Object> data, HttpServletRequest servletReq) {	
		
		Map question = (Map) data.get("question");

		String json = nlpDao.nlp((String) question.get("text"));
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Map<String, Object> map = mapper.readValue(json, Map.class);		
			Map<String, Object> return_object = (Map<String, Object>) map.get("return_object");
			
			List<Map<String, Object>> sentences = (List<Map<String, Object>>) return_object.get("sentence");
			Map<String, Object> sentence = (Map<String, Object>) sentences.get(0);
			List<Map<String, Object>> morp = (List<Map<String, Object>>) sentence.get("morp");
			
			String intent = (String) getSimilarity(morp); //유사한 의도 찾기
			String answer = getAnswer(intent); //의도로 답변찾기
			
			res.put("left", "chatbot");
			res.put("text",answer);
			res.put("createdAt", new Date());
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/* 어떻게 구현할건지 생각~! */
	private String getSimilarity(List<Map<String, Object>> morp) {
		
		//선택 - 1. 객체사전, 2. 문장학습
		
		//1. 객체사전
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("peperoni", "페페로니"); //동의어 구현시 value를 String => List로 변경
		entity.put("pizza", "피자");
		
		
		//유사도 검사

		int count = 0;
		for(Map<String, Object> word : morp) {
			String lemma = (String) word.get("lemma");
			String type = (String) word.get("type");
			
			Iterator<String> keys = entity.keySet().iterator();
	        while( keys.hasNext() ){
	            String key = keys.next();
	            String value = (String) entity.get(key);
	            
	            if(lemma.equals(value)) {
	            	count++;
	            	break;
	            }
	        }   
		}
		
		double similarity = (double) count / morp.size();
		System.out.println(similarity);
		
		String intent = "";
		if(similarity > 0.2) {
			//DB에서 select intent where = ""; --
			intent = "피자 주문";
		} else intent = "미발화";
		
		//2. 문장학습
		//String trainingText = "치즈 피자 라지 사이즈 주세요."; //DB에 저장할 질문
		
		return intent;
	}
	
	private String getAnswer(String intent) {
		//intent로 DB에서 답변 조회해오기
		//DB (intent "피자주문", 질문 "페페로니 피자 주세요", "치즈 피자 라지 사이즈 주세요", 답변 : "피자가 주문되었습니다.";
		// select answer from where intent = intent;
		
		//임시코드
		String answer = "";
		if(intent.equals("피자 주문")) {
			answer = "피자가 주문되었습니다.";
		} else {
			answer = "잘 못들었습니다. 다시 말씀해주세요.";
		}
		
		return answer;
	}
}