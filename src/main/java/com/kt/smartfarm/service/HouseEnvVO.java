package com.kt.smartfarm.service;

import com.kt.cmmn.util.MapUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;


/**
 * 외부기상대, 임계치 설정 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class HouseEnvVO {

	Long id;
	Integer productMethodId;
	Integer greenHouseTypeId;
	String houseName;
	String latitude;
	String longitude;
	String description;
	String buildDate;
	String lastUpdate;
	String addr1;
	String addr2;
	String zip;
	Integer nutrientCommonId;
	Integer changeGh;
	Integer changeTemperature;
	Double floorSpace;
	Double houseHorizontal;
	Double houseVertical;
	Double houseHeight;
	Double plantSpacing;
	Integer cropCnt;
	Double houseDirection;
	Double selectTempSensor;
	Double selectHumSensor;
	Long gsmKey;
	String houseNameI18n;
	HashMap<String,Object> properties = new HashMap<>();

	public void setProperties(String properties) {
		this.properties = (HashMap<String,Object>)MapUtils.fromJson(properties);
	}
	
	List<CCTVSettingVO> cctv;
}
