package com.chucoding.fichatbot.dao;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;

public class ChatDao {

	private static final String ACCESS_KEY = "417ac904-4b08-4ba6-9f5e-ea214b0994ad";
	private static final String KAKAO_ACCESS_KEY = "4342a446786a28fd0b97b08407212f31";
	
	public String open() {
		String openApiURL = "http://aiopen.etri.re.kr:8000/Dialog";
        String accessKey = ACCESS_KEY;    // 발급받은 API Key
        String domain = "Genie_Pizza";          // 도메인 명
        String access_method = "internal_data";   // 도메인 방식
        String method = "open_dialog";                      // method 호출 방식
        Gson gson = new Gson();
 
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();
 
        ////////////////////////// OPEN DIALOG //////////////////////////
 
        argument.put("name", domain);
        argument.put("access_method", access_method);
        argument.put("method", method);
 
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
	
	
	public String message(Map map) {
	
		String openApiURL = "http://aiopen.etri.re.kr:8000/Dialog";
        String accessKey = ACCESS_KEY;    // 발급받은 API Key
        String uuid = (String) map.get("uuid");  // Open Dialog로 부터 생성된 UUID
        String method = "dialog";           // method 호출 방식
        String text = (String) map.get("text");          // method 호출 방식
        Gson gson = new Gson();
 
        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();
 
        ////////////////////////// OPEN DIALOG //////////////////////////
 
        argument.put("uuid", uuid);
        argument.put("method", method);
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


	public String tts(String folderpath, String text) {
		
		String openApiURL = "https://kakaoi-newtone-openapi.kakao.com/v1/synthesize";
		String body = String.format("<speak><voice name=\"%s\">%s</voice></speak>", "MAN_DIALOG_BRIGHT", "테스트");
		
        URL url;
        Integer responseCode = null;
        String responBody = null;
        String filename = "";
        UUID uuid = UUID.randomUUID();
        try {
            url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/xml");
            con.setRequestProperty("Authorization", String.format("KakaoAK %s", KAKAO_ACCESS_KEY));
            
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(body.getBytes("UTF-8"));
            wr.flush();
            wr.close();
            
            responseCode = con.getResponseCode();
            
            File folder = new File(folderpath);
			if (folder.exists() == false) {
				folder.mkdirs();
			}
            
            
            filename = uuid.toString() + ".mp3";
            File file = new File(folderpath+ File.separator + filename);
            copyInputStreamToFile(con.getInputStream(), file);
            
            
        } catch (MalformedURLException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
		return filename;
	}
	
	 @SuppressWarnings("unused")
	private static void copyInputStreamToFile(InputStream inputStream, File file)
	            throws IOException {

	        // append = false
	        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
	            int read;
	            byte[] bytes = new byte[8192];
	            while ((read = inputStream.read(bytes)) != -1) {
	                outputStream.write(bytes, 0, read);
	            }
	        }

	    }
}
