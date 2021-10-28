package com.chucoding.fichatbot.dao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class NlpDao {

	private static final String ACCESS_KEY = "417ac904-4b08-4ba6-9f5e-ea214b0994ad";
	
	public String nlp(String question) {
		
		// 언어 분석 기술(구어)
        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU_spoken"; 
 
        String accessKey = ACCESS_KEY;    // 발급받은 API Key
        String analysisCode = "morp";   // 언어 분석 코드
        String text = question;           // 분석할 텍스트 데이터
        Gson gson = new Gson();
         
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();
 
        argument.put("analysis_code", analysisCode);
        argument.put("text", text);
 
        request.put("access_key", accessKey);
        request.put("argument", argument);
 
 
        URL url;
        Integer responseCode = null;
        String responBody = null;
        try {
            url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
 
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes("UTF-8"));
            wr.flush();
            wr.close();
 
            responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            byte[] buffer = new byte[is.available()];
            int byteRead = is.read(buffer);
            responBody = new String(buffer);
 
            System.out.println("[responseCode] " + responseCode);
            System.out.println("[responBody]");
            System.out.println(responBody);
 
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return responBody;
	}
}
