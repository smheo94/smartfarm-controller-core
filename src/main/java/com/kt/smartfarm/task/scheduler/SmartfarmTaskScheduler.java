package com.kt.smartfarm.task.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.cmmn.util.ClassUtil;
import com.kt.cmmn.util.DateUtil;
import com.kt.cmmn.util.RestClientUtil;
import com.kt.cmmn.util.WeatherCastGPSUtil;
import com.kt.smartfarm.service.HouseEnvService;
import com.kt.smartfarm.service.UltraShortWeatherVO.UltraShortWeatherVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*
 * 1시간 단위 동네예보 스케쥴러 
 * 노지채소 전용
 * DateUtil, HttpUtil
 * TODO : profile리스트 가지고 오는것. db에 넣는 것.
 */
@Component
//@PropertySource(value={"classpath:application.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
public class SmartfarmTaskScheduler {
	private static final Logger log = LoggerFactory.getLogger(SmartfarmTaskScheduler.class);
	@Value("${smartfarm.system.type}")
    public String SYSTEM_TYPE;
	
	@Autowired
	public HouseEnvService houseEnvService;
	
	// 매시간 41분마다 API 데이터를 제공한다.
	// 기준 시간은 
	
	static String FORECAST_URL =  "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";
	static String weatherCertKey = "RxrU3O5OC4pUiE6GEGShKBl181iacSPsFyXR32lZv0ohgQy6Frr5CRikB1qGdSVOZqHtX55VFoMoje2o3HJegg%3D%3D";
	//static String weatherCertKey = "Y0VECgYwtbnfuvNYklX9OChRmOn3vCibz%2Fxe3YFrqoWiyCjkSnKRMpC9I6ybpZbHnKA5OxhNjyBGy28lrfomjQ%3D%3D";
	
	static String SUNRISE_URL = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getLCRiseSetInfo";
	static String sunCertKey = "l%2FgDtDXE6ZralE2VJSrcon%2FKyKps%2FPANA9o497NfusyEYyei0Zv1fAWqJoxz8jaah7nv853ln7cxCWJypWOMLA%3D%3D";

	static String ULTRASRTNCST_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst";
	static String ultraCertKey = "RxrU3O5OC4pUiE6GEGShKBl181iacSPsFyXR32lZv0ohgQy6Frr5CRikB1qGdSVOZqHtX55VFoMoje2o3HJegg%3D%3D";


	@Scheduled(cron="0 15 2,5,8,11,14,17,20,23 * * *")
	public void runWeatherSchedule(){
		List<HashMap<String,Object>> houseList = new ArrayList<>();
		//시간계산이 복잡하여 단순화함
		if(SYSTEM_TYPE.equalsIgnoreCase("supervisor")){
			Calendar c = Calendar.getInstance();
			int h = c.get(Calendar.HOUR_OF_DAY);
			c.add(Calendar.HOUR_OF_DAY, -(h%3+1));
			String regDay = DateUtil.getDateStrOnly(c.getTime(), "yyyyMMdd");
			String regTimeString = DateUtil.getDateStrOnly(c.getTime(), "HH00");
			houseEnvService.deleteOldForecastData();
			houseList = houseEnvService.getAllList();
			Map<String,JSONObject> positionJson = new HashMap<>();
			for(int i=0; i<houseList.size(); i++){
				HashMap<String, Object> house = houseList.get(i);
				if(house.get("latitude") != null && house.get("longitude") != null){
					Double longitude = Double.parseDouble(house.get("longitude").toString());
					Double latitude = Double.parseDouble(house.get("latitude").toString());
					HashMap<String,Object> gridXY = WeatherCastGPSUtil.getGridxy(latitude,longitude);
					String nx = gridXY.get("x").toString();
					String ny = gridXY.get("y").toString();
					Long houseId = ClassUtil.castToSomething(house.get("id"), Long.class);
					String positionKey = nx +"_" + ny;
					if(Integer.parseInt(nx) > 0 && Integer.parseInt(ny) >0){
						if( positionJson.containsKey(positionKey)) {
							insertForeCastFromJson(houseId, positionJson.get(positionKey));
						} else {
							try {
								URL url = new URL(FORECAST_URL
										+ "?serviceKey=" + weatherCertKey
										+ "&dataType=JSON"
										+ "&base_date=" + regDay
										+ "&base_time=" + regTimeString
										+ "&numOfRows=100"
										+ "&nx=" + nx
										+ "&ny=" + ny
								);
								HttpURLConnection http = (HttpURLConnection) url.openConnection();
								http.setConnectTimeout(10000);
								http.setUseCaches(false);

								BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
								StringBuilder sb = new StringBuilder();
								while (true) {
									String line = br.readLine();
									if (line == null) {
										break;
									}
									sb.append(line);
								}
								br.close();
								http.disconnect();
								JSONObject json = new JSONObject(sb.toString());

								if (insertForeCastFromJson(houseId, json)) continue;
							} catch (Exception e) {
								log.debug(e.getMessage());
							}
						}
					}				
				}
			}
		}
	}

	private boolean insertForeCastFromJson(Long houseId, JSONObject json) {
		if(!json.getJSONObject("response").getJSONObject("header").get("resultCode").toString().equals("00")){
			return true;
		}
		JSONObject body = json.getJSONObject("response").getJSONObject("body");
		JSONObject items = body.getJSONObject("items");
		JSONArray itemArray = items.getJSONArray("item");

		String baseDate = itemArray.getJSONObject(0).get("baseDate").toString();
		String baseTime = itemArray.getJSONObject(0).get("baseTime").toString();
		Map<String, LinkedHashMap<String,Object>> simpleForecastMap = new HashMap<>();

		for(int j=0 ; j<itemArray.length();j++){
			JSONObject item = itemArray.getJSONObject(j);
//					        	// 당일 예보가 아닌경우 break ( 상관있나? )
//					        	if(!item.get("fcstDate").equals(baseDate)){
//					        		break;
//					        	}
			String forecastKey = item.get("fcstDate").toString() + item.get("fcstTime").toString();
			LinkedHashMap<String,Object> hm = simpleForecastMap.getOrDefault(forecastKey, new LinkedHashMap<String,Object>() {
				{
					put("base_date", baseDate);//발표일자
					put("base_time", baseTime);//발표시각
					put("house_id", houseId);
					put("fcst_date", item.get("fcstDate"));//예보일자
					put("fcst_time", item.get("fcstTime"));//에보시각
					put("nx", item.get("nx"));//예보지점 X좌표
					put("ny", item.get("ny"));//예보지점 Y좌표
					simpleForecastMap.put(forecastKey, this);
				}
			});
			hm.put("fcst_value", item.get("fcstValue"));//카테고리에 해당하는 예보 값
			hm.put("category", item.get("category"));
			hm.put(item.get("category").toString(), item.get("fcstValue"));
			houseEnvService.insertForecastData(hm);
		}
		for(Map.Entry<String, LinkedHashMap<String,Object>> hmEntry : simpleForecastMap.entrySet()) {
			houseEnvService.insertForecastV2Data(hmEntry.getValue());
		}
		return false;
	}

	// 온실 리스트 가져와서 위경도 겹치는거 빼고

//	@Scheduled(cron="0 1 0 * * *")
//	public void runSunriseSchedule(){
//		if(SYSTEM_TYPE.equalsIgnoreCase("supervisor")){
//			List<HashMap<String,Object>> houseList = new ArrayList<>();
//			DateUtil dateUtil = new DateUtil();
//			String regDay = DateUtil.getCurrentDateString();
//			houseList = houseEnvService.getAllList();
//			for(int i=0; i<houseList.size(); i++){
//				HashMap<String, Object> house = houseList.get(i);
//				if(house.get("latitude") != null && house.get("longitude") != null){
//					HashMap<String,Object> hm = new HashMap<>();
//					Double longitude = Double.parseDouble(house.get("longitude").toString());
//					Double latitude = Double.parseDouble(house.get("latitude").toString());
//
//					try{
//						URL url = new URL(SUNRISE_URL
//								+"?ServiceKey="+sunCertKey
//								+"&longitude="+longitude
//								+"&latitude="+latitude
//								+"&locdate="+regDay
//								+"&dnYn=Y"
//								+"&_type=json"
//								);
//						HttpURLConnection http = (HttpURLConnection) url.openConnection();
//				        http.setConnectTimeout(10000);
//				        http.setUseCaches(false);
//
//				        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
//				        StringBuilder sb = new StringBuilder();
//				        while (true) {
//				            String line = br.readLine();
//				            if (line == null){
//				                break;
//				            }
//				            sb.append(line);
//				        }
//				        br.close();
//				        http.disconnect();
//
//				        JSONObject json = new JSONObject(sb.toString());
//				        JSONObject body = json.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
//				        hm.put("house_id", house.get("id"));
//				        hm.put("sunrise", body.get("sunrise"));
//				        hm.put("sunset", body.get("sunset"));
//				        hm.put("loc_date", body.get("locdate").toString());
//				        houseEnvService.insertSunriseData(hm);
//					}catch(Exception e){
//						log.debug(e.getMessage());
//					}
//				}
//			}
//		}
//	}



	@Scheduled(cron="0 40 0/1 * * *")
	public void runUltraShortWeatherSchedule() throws URISyntaxException {
		log.info("Get ultraShortWeather Data...");
		if(SYSTEM_TYPE.equalsIgnoreCase("supervisor")){
			houseEnvService.deleteOldWeatherData();
			List<HashMap<String,Object>> houseList = new ArrayList<>();
			houseList = houseEnvService.getAllList();
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter baseDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			DateTimeFormatter baseTimeFormatter = DateTimeFormatter.ofPattern("HH00");
			String baseDate = now.format(baseDateFormatter);
			String baseTime = now.format(baseTimeFormatter);
			HashMap<Double, Double> locationMap = new HashMap<>();
			for(int i=0; i<houseList.size(); i++){
				HashMap<String, Object> house = houseList.get(i);
				if(house.get("latitude") != null && house.get("longitude") != null){
					Double longitude = Double.parseDouble(house.get("longitude").toString());
					Double latitude = Double.parseDouble(house.get("latitude").toString());

					//중복 제거
					Double existLocation = locationMap.get(latitude);
					if(existLocation == null) {
						locationMap.put(latitude, longitude);

						HashMap<String,Object> gridXY = WeatherCastGPSUtil.getGridxy(latitude,longitude);
						String nx = gridXY.get("x").toString();
						String ny = gridXY.get("y").toString();
						String positionKey = nx +"_" + ny;
						if(Integer.parseInt(nx) > 0 && Integer.parseInt(ny) >0){


							try{
								URI uri = new URI(ULTRASRTNCST_URL
										+"?serviceKey=" + ultraCertKey
										+"&pageNo=1"
										+"&numOfRows=10"
										+"&dataType=JSON"
										+"&base_date="+baseDate
										+"&base_time="+baseTime
										+"&nx=" + nx
										+"&ny="+ny
								);
								RestTemplate restTemplate = new RestTemplate();
								RestClientUtil.setIgnoreCertificateSSL(restTemplate);
								String result = restTemplate.getForObject(uri, String.class);
								ObjectMapper mapper = new ObjectMapper();
								UltraShortWeatherVO ultraShortWeatherVO = mapper.readValue(result , UltraShortWeatherVO.class);
								if(ultraShortWeatherVO.getResponse().getHeader().getResultCode().equals("00")){
									LinkedHashMap<String, Object> ultraSrtMap = ultraShortWeatherVO.ultraOjbToMap(baseDate);
									if(!(ultraSrtMap == null)) {
										houseEnvService.insertUltraShortWeather(ultraSrtMap);
									}

								}
							}catch(Exception e){
								log.error("run Ultra Short Ncst Exception : {}" , e);
							}
						}
					}
				}
			}
		}
	}
	
	private String getBaseTime(String regTimeString) {
		final int[] standardTime = {2, 5, 8, 11, 14, 17, 20, 23};
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

}

