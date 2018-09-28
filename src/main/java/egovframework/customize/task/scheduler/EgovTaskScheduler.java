package egovframework.customize.task.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import egovframework.cmmn.util.CommonUtil;
import egovframework.cmmn.util.DateUtil;
import egovframework.customize.service.EgovSampleService;
import egovframework.customize.service.HouseEnvService;

/*
 * 1시간 단위 동네예보 스케쥴러 
 * 노지채소 전용
 * DateUtil, HttpUtil
 * TODO : profile리스트 가지고 오는것. db에 넣는 것.
 */
@Service
public class EgovTaskScheduler {	

	@Resource(name ="houseEnvService")
	private HouseEnvService houseEnvService;
	
	// 매시간 41분마다 API 데이터를 제공한다.
	// 기준 시간은 
	
	final String FORECAST_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData";
	final String certKey = "Y0VECgYwtbnfuvNYklX9OChRmOn3vCibz%2Fxe3YFrqoWiyCjkSnKRMpC9I6ybpZbHnKA5OxhNjyBGy28lrfomjQ%3D%3D";
	final String fcstCategory[] ={"POP","PTY","R06","REH","S06","SKY","T3H","TMN","TMX","UUU","VVV","WAV","VEC","WSD"};
	final String fcstCategoryDesc []={"강수확률","강수형태","6시간 강수량","습도","6시간 신적설",
			"하늘상태","3시간 기온","아침 최저기온","낮 최고기온","풍속(동서성분)","풍속(남북성분)","파고",
			"풍향","풍속"};
	public void runWeatherSchedule(){		
		System.out.println("Scheduler");
		List<HashMap<String,Object>> houseList = new ArrayList<>();
		DateUtil dateUtil = new DateUtil();
		String regDay = dateUtil.getCurrentDateString();
		String regTimeString   = new java.text.SimpleDateFormat("HH").format(new java.util.Date());
		houseList = houseEnvService.list(null);
		for(int i=0; i<houseList.size(); i++){
			Double latitude = Double.parseDouble(houseList.get(i).get("latitude").toString());
			Double longitude = Double.parseDouble(houseList.get(i).get("longitude").toString());
			HashMap<String,Object> gridXY = getGridxy(latitude,longitude);
			//당일것만 조회해
			String nx = gridXY.get("x").toString();
			String ny = gridXY.get("y").toString();
			try{
				URL url = new URL(FORECAST_URL
						+"?ServiceKey="+certKey
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
		        	String category = item.get("category").toString();
		        	hm.put("base_date", baseDate);//발표일자
		        	hm.put("base_time", baseTime);//발표시각
		        	hm.put("fcst_date", item.get("fcstDate").toString());//예보일자
		        	hm.put("fcst_time", item.get("fcstTime").toString());//에보시각
		        	hm.put("fcst_value", item.get("fcstValue").toString());//카테고리에 해당하는 예보 값
		        	hm.put("nx", item.get("nx").toString());//예보지점 X좌표
		        	hm.put("ny", item.get("ny").toString());//예보지점 Y좌표
		        	hm.put("category", item.get("category").toString());
		        	hm.put("term_category","forecastSpace");
		        	for(int k=0; k<fcstCategory.length;k++){
		        		if(category.equals(fcstCategory[k])){
		        			hm.put("category_desc", fcstCategoryDesc[k]);
		        			break;
		        		}
		        	}
	        	}
		        	//DB INSERT
//		        	dashboardDao.insertForecastData(hm);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
		}
	}	
		// 온실 리스트 가져와서 위경도 겹치는거 빼고 
	
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

