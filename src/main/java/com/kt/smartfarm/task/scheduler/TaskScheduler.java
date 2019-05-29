package com.kt.smartfarm.task.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.kt.cmmn.util.DateUtil;
import com.kt.smartfarm.service.HouseEnvService;

/*
 * 1시간 단위 동네예보 스케쥴러 
 * 노지채소 전용
 * DateUtil, HttpUtil
 * TODO : profile리스트 가지고 오는것. db에 넣는 것.
 */
@Component
@PropertySource(value={"classpath:application.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
public class TaskScheduler {
	private static final Logger log = LoggerFactory.getLogger(TaskScheduler.class);
	@Value("${smartfarm.system.type}")
    public String SYSTEM_TYPE;
	
	@Autowired
	public HouseEnvService houseEnvService;
	
	// 매시간 41분마다 API 데이터를 제공한다.
	// 기준 시간은 
	
	static String FORECAST_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData";
	static String weatherCertKey = "Y0VECgYwtbnfuvNYklX9OChRmOn3vCibz%2Fxe3YFrqoWiyCjkSnKRMpC9I6ybpZbHnKA5OxhNjyBGy28lrfomjQ%3D%3D";
	
	static String SUNRISE_URL = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getLCRiseSetInfo";
	static String sunCertKey = "l%2FgDtDXE6ZralE2VJSrcon%2FKyKps%2FPANA9o497NfusyEYyei0Zv1fAWqJoxz8jaah7nv853ln7cxCWJypWOMLA%3D%3D";
	
	public void runWeatherSchedule(){
		List<HashMap<String,Object>> houseList = new ArrayList<>();
//		DateUtil dateUtil = new DateUtil();
//		String regDay = dateUtil.getCurrentDateString();
//		String regTimeString   = new java.text.SimpleDateFormat("HH").format(new java.util.Date());
//		regTimeString = getBaseTime(regTimeString);

		//시간계산이 복잡하여 단순화함
		if(SYSTEM_TYPE.equalsIgnoreCase("supervisor")){
			Calendar c = Calendar.getInstance();
			int h = c.get(Calendar.HOUR_OF_DAY);
			c.add(Calendar.HOUR_OF_DAY, -(h%3+1));
	
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH00");
			String regDay = sdf1.format(c.getTime());
			String regTimeString = sdf2.format(c.getTime());
	
			houseList = houseEnvService.getAllList();
			for(int i=0; i<houseList.size(); i++){
				if(houseList.get(i).get("latitude") != null && houseList.get(i).get("longitude") != null){
					Double longitude = Double.parseDouble(houseList.get(i).get("longitude").toString());
					Double latitude = Double.parseDouble(houseList.get(i).get("latitude").toString());
					HashMap<String,Object> gridXY = getGridxy(latitude,longitude);
					String nx = gridXY.get("x").toString();
					String ny = gridXY.get("y").toString();
					Integer houseId = (Integer)houseList.get(i).get("id");
					if(Integer.parseInt(nx) > 0 && Integer.parseInt(ny) >0){
						try{
							URL url = new URL(FORECAST_URL
									+"?ServiceKey="+weatherCertKey
									+"&base_date="+regDay
									+"&base_time="+regTimeString
									+"&numOfRows=1000"
									+"&nx="+nx
									+"&ny="+ny
									+"&_type=json"
									);
						 	HttpURLConnection http = (HttpURLConnection) url.openConnection();
					        http.setConnectTimeout(10000);
					        http.setUseCaches(false);
					
					        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
					        StringBuilder sb = new StringBuilder();
					        while (true) {
					            String line = br.readLine();
					            if (line == null){
					                break;
					            }
					            sb.append(line);
					        }
					        br.close();
					        http.disconnect();
					        
					        JSONObject json = new JSONObject(sb.toString());
					        if(!json.getJSONObject("response").getJSONObject("header").get("resultCode").toString().equals("0000")){
					        	continue;
					        }
					        JSONObject body = json.getJSONObject("response").getJSONObject("body");
					        JSONObject items = body.getJSONObject("items");
					        JSONArray itemArray = items.getJSONArray("item");
	
					        String baseDate = itemArray.getJSONObject(0).get("baseDate").toString();
					        String baseTime = itemArray.getJSONObject(0).get("baseTime").toString();
					      
				        	for(int j=0 ; j<itemArray.length();j++){
					        	LinkedHashMap<String,Object> hm = new LinkedHashMap<String,Object>();
					        	JSONObject item = itemArray.getJSONObject(j);
					        	// 당일 예보가 아닌경우 break
					        	if(item.get("fcstDate").equals(baseDate)){
					        		break;
					        	}				        	
					        	hm.put("base_date", baseDate);//발표일자
					        	hm.put("base_time", baseTime);//발표시각
					        	hm.put("fcst_date", item.get("fcstDate").toString());//예보일자
					        	hm.put("fcst_time", item.get("fcstTime").toString());//에보시각
					        	hm.put("fcst_value", item.get("fcstValue").toString());//카테고리에 해당하는 예보 값
					        	hm.put("nx", item.get("nx").toString());//예보지점 X좌표
					        	hm.put("ny", item.get("ny").toString());//예보지점 Y좌표
					        	hm.put("category", item.get("category").toString());
					        	hm.put("house_id", houseId);
					        	houseEnvService.insertForecastData(hm);
				        	}		        	
			        	}catch(Exception e){
			        		log.debug(e.getMessage());
			        	}
					}				
				}
			}
		}
	}
	
		// 온실 리스트 가져와서 위경도 겹치는거 빼고 
	
	
	public void runSunriseSchedule(){
		if(SYSTEM_TYPE.equalsIgnoreCase("supervisor")){
			List<HashMap<String,Object>> houseList = new ArrayList<>();
			DateUtil dateUtil = new DateUtil();
			String regDay = dateUtil.getCurrentDateString();		
			houseList = houseEnvService.getAllList();
			for(int i=0; i<houseList.size(); i++){
				if(houseList.get(i).get("latitude") != null && houseList.get(i).get("longitude") != null){
					HashMap<String,Object> hm = new HashMap<>();
					Double longitude = Double.parseDouble(houseList.get(i).get("longitude").toString());
					Double latitude = Double.parseDouble(houseList.get(i).get("latitude").toString());
					
					try{
						URL url = new URL(SUNRISE_URL
								+"?ServiceKey="+sunCertKey
								+"&longitude="+longitude
								+"&latitude="+latitude
								+"&locdate="+regDay
								+"&dnYn=Y"						
								+"&_type=json"
								);
						HttpURLConnection http = (HttpURLConnection) url.openConnection();
				        http.setConnectTimeout(10000);
				        http.setUseCaches(false);
				
				        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
				        StringBuilder sb = new StringBuilder();
				        while (true) {
				            String line = br.readLine();
				            if (line == null){
				                break;
				            }
				            sb.append(line);
				        }
				        br.close();
				        http.disconnect();
				        
				        JSONObject json = new JSONObject(sb.toString());
				        JSONObject body = json.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
				        hm.put("house_id", (Integer)houseList.get(i).get("id"));
				        hm.put("sunrise", body.get("sunrise"));
				        hm.put("sunset", body.get("sunset"));
				        hm.put("loc_date", body.get("locdate").toString());
				        houseEnvService.insertSunriseData(hm);
					}catch(Exception e){
						log.debug(e.getMessage());
					}
				}
			}
		}
	}
	
	private String getBaseTime(String regTimeString) {
		final int standardTime[] = {2,5,8,11,14,17,20,23};
		String regTimeToString="";
		int regTime = Integer.parseInt(regTimeString);		
		int tempTime= 0;
		int minTime = 25;
		for(int i=0; i<standardTime.length; i++){

			tempTime = regTime - standardTime[i];
			if(minTime > tempTime){
				if(tempTime < 0){
					break;
				}else{
					minTime = tempTime;	
				}
			}
		}		
		if(regTime != 0 && regTime != 1){
			regTime = regTime - minTime;	
		}else if (regTime == 0){
			regTime = 23;
		}else if (regTime == 1){
			regTime = 1;
		}
		
		if(regTime<10){
			regTimeToString = "0"+regTime+"00";
		}else{
			regTimeToString = regTime+"00";
		}
		return regTimeToString;
	}	
	
	private HashMap<String, Object> getGridxy(double latitude, double longitude) {
        double re1 = 6371.00877; // 지구 반경(km)
        double grid = 5.0; // 격자 간격(am)
        double slatA = 30.0; // 투영 위도1(degree)
        double slatB = 60.0; // 투영 위도2(degree)
        double olonD = 126.0; // 기준점 경도(degree)
        double olatD = 38.0; // 기준점 위도(degree)
        double xo = 43; // 기준점 X좌표(GRID)
        double yo = 136; // 기1준점 Y좌표(GRID)
        double degrad = Math.PI / 180.0;
        // double RADDEG = 180.0 / Math.PI;
 
        double re = re1 / grid;
        double slat1 = slatA * degrad;
        double slat2 = slatB * degrad;
        double olon = olonD * degrad;
        double olat = olatD * degrad;        
 
        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        HashMap<String, Object> map = new HashMap<String, Object>();
       
        double ra = Math.tan(Math.PI * 0.25 + (latitude) * degrad * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = longitude * degrad - olon;
        if (theta > Math.PI){
            theta -= 2.0 * Math.PI;
        }
        if (theta < -Math.PI){
            theta += 2.0 * Math.PI;
        }
        theta *= sn;
        map.put("lat", latitude);
       map.put("lng", longitude);
        map.put("x", (int)Math.floor(ra * Math.sin(theta) + xo + 0.5));
        map.put("y", (int)Math.floor(ro - ra * Math.cos(theta) + yo + 0.5));
 
        return map;
    }
}

