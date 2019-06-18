package com.kt.smartfarm.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface HouseEnvService {	
	HouseEnvVO insert( HouseEnvVO house);
	Integer delete(Integer gsmKey, Integer greenHouseId);
	HouseEnvVO update(HouseEnvVO house);
	HashMap<String,Object>  get(Integer gsmKey, Integer greenHouseId, Boolean isSmartfarmSystem);
	List<HashMap<String, Object>> list(Integer gsmKey, boolean all, boolean detail, Boolean isSmartfarmSystem);
	List<HashMap<String, Object>> list(Integer gsmKey, boolean all, boolean detail, Boolean isSmartfarmSystem, Boolean isCCTVOnly);
	List<HashMap<String,Object>> selectHouseTypeList();
	List<ProductVO> selectProductList();
	HashMap<String,Object> insertHouseDeviceMap(HashMap<String, Object> map);
	HashMap<String,Object> deleteHouseDeviceMap(HashMap<String, Object> map);
	List<DeviceEnvVO> houseDeviceList(Integer houseId);
	List<HashMap<String, Object>> getAllList();
	Integer insertForecastData(LinkedHashMap<String, Object> hm);
	List<HashMap<String,Object>> getWeatherCast(Integer houseId, String fromDate, String toDate, Boolean isSmartfarmSystem);
	List<HashMap<String,Object>> getWeatherCategory();
	Integer insertSunriseData(HashMap<String, Object> hm);

	List<HashMap<String,Object>> groundDeviceList(Integer houseId);

	// CCTV
	HashMap<String, Object> insertCctv(CCTVSettingVO cctv);
	Integer deleteCctv(Integer id);
	Integer updateCctv(CCTVSettingVO cctv);
	List<HashMap<String, Object>> getCctvsByHouseId(Integer houseId);
	HashMap<String,Object> houseDeviceInfoList(Integer houseId);

	Integer copyToNewGSM(Integer fromGsmKey, Integer toGsmKey);
}