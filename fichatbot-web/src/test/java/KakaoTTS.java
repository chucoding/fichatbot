import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class KakaoTTS {
	public static void main(String[] args) {
		String openApiURL = "https://kakaoi-newtone-openapi.kakao.com/v1/synthesize";
		String body = String.format("<speak><voice name=\"%s\">%s</voice></speak>", "MAN_DIALOG_BRIGHT", "테스트 입니다.");
		
        URL url;
        Integer responseCode = null;
        String responBody = null;
        
        try {
            url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/xml");
            con.setRequestProperty("Authorization", String.format("KakaoAK %s", "4342a446786a28fd0b97b08407212f31"));
            
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(body.getBytes("UTF-8"));
            wr.flush();
            wr.close();
            
             System.out.println(con.getContent());
             System.out.println(con.getContentType());
             
            responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            byte[] buffer = new byte[is.available()];
            int byteRead = is.read(buffer);
            responBody = new String(buffer);
            
            System.out.println(responBody);
            System.out.println("한글");
            
            
        } catch (MalformedURLException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
	}
}
