package com.kt.cmmn.util;

import java.util.HashMap;

public class CommonUtil {

	
	// 위,경도로 격자 좌표 구하기
	public HashMap<String, Object> getGridxy(double latitude, double longitude) {
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
