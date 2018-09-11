package egovframework.cmmn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;

import egovframework.customize.service.ControllerEnvVO;
import egovframework.customize.service.DeviceEnvVO;

public class HttpUtil {	
	private String url;
	
	public String postHandler(HashMap<String,Object> param, HashMap<String,Object> controllerInfo){		
		try{			
			String result ="";

			JSONObject dataResult = new JSONObject();

			String controllerUrl=(String)param.get("controllerUrl");
			String api = (String)param.get("api");
			String method = (String)param.get("method");
//			String body = controllerInfo.toString();
			JSONObject dataJson = new JSONObject(controllerInfo);
//			dataResult.put("data", requestBody);
			
//			String body = dataResult.toString();
			String body = dataJson.toString();
			HttpURLConnection conn = getRestAPiConnection(controllerUrl, method, api,  body);		
			result = getRestApiResponseData(result, conn);
         return result;
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	// API별 Connection 생성
	private HttpURLConnection getRestAPiConnection(String controllerUrl, String method, String api, String body) throws MalformedURLException, IOException {
		URL url = new URL(controllerUrl+api );
					
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Accept","application/json, text/plain, */*");
		conn.setRequestProperty("Accept-Encoding","gzip, deflate, br");
		conn.setRequestProperty("Accept-Language","ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");		
		conn.setRequestProperty("Connection","keep-alive");
		conn.setRequestProperty("Conent-Type", "application/json");
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		
		
		if( body != null && body.length() > 0 ) {
			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes("utf-8"));
			os.flush();
		} 
		return conn;
	}
	
	private String getRestApiResponseData(String result, HttpURLConnection conn)
			throws UnsupportedEncodingException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		String inputLine="";
		String data ="";
		StringBuffer response = new StringBuffer();

         while (true) {
        	 inputLine = in.readLine();
        	 if(inputLine == null){
        		 break;
        	 }
             response.append(inputLine);                         	 
        	 JSONObject json = new JSONObject(response.toString());
             
        	 data = json.getJSONObject("data").get("data").toString();
             result = data;
         }         
		return result;
	}
	
	public String strToJson(String str){
		StringBuffer rtnSb = new StringBuffer();
		rtnSb.append("{");
		rtnSb.append(str);
		rtnSb.append("}");
		return rtnSb.toString();		
	}
}
