package com.kt.smartfarm.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public interface HouseEnvService {	
	HouseEnvVO insert( HouseEnvVO house);
	Integer delete(Long gsmKey, Long greenHouseId);
	HouseEnvVO update(HouseEnvVO house);
	HashMap<String,Object>  get(Long gsmKey, Long greenHouseId, Boolean isSmartfarmSystem);
	List<HashMap<String, Object>> list(Long gsmKey, boolean all, boolean detail, Boolean isSmartfarmSystem);
	List<HashMap<String, Object>> list(Long gsmKey, boolean all, boolean detail, Boolean isSmartfarmSystem, Boolean isCCTVOnly);
	List<HashMap<String,Object>> selectHouseTypeList();
	List<ProductVO> selectProductList();
	HashMap<String,Object> insertHouseDeviceMap(HashMap<String, Object> map);
	HashMap<String,Object> deleteHouseDeviceMap(HashMap<String, Object> map);
	List<DeviceEnvVO> houseDeviceList(Long houseId);
	List<HashMap<String, Object>> getAllList();
	Integer insertForecastData(LinkedHashMap<String, Object> hm);
	List<HashMap<String,Object>> getWeatherCast(Long houseId, String fromDate, String toDate, Boolean isSmartfarmSystem);

	UltraShortWeatherDataVO getUltraShortWeatherData(Long houseId, Boolean isSmartfarmSystem);

	List<HashMap<String,Object>> getWeatherCastV2(Long houseId, String fromDate, String toDate, Boolean isSmartfarmSystem);

	List<HashMap<String,Object>> getWeatherCategory();
//	Integer insertSunriseData(HashMap<String, Object> hm);
	Integer insertUltraShortWeather(LinkedHashMap<String, Object> ultraVO);

	List<HashMap<String,Object>> groundDeviceList(Long houseId);

	// CCTV
	Integer insertCctv(CCTVSettingVO cctv);
	Integer deleteCctv(Integer id);
	Integer updateCctv(CCTVSettingVO cctv);
	List<CCTVSettingVO> getCCTVListByHouseId(Long houseId);
	HashMap<String,Object> houseDeviceInfoList(Long houseId);

	Integer copyToNewGSM(Long fromGsmKey, Long toGsmKey);

	List<HouseProductVO> listHouseProduct(Long greenHouseId);

	List<HouseProductVO> insertHouseProducts(List<HouseProductVO> listHouseProduct);

	HouseProductVO insertHouseProduct(HouseProductVO houseProduct);

	HouseProductVO updateHouseProduct(HouseProductVO houseProduct);

	Integer deleteHouseProduct(Long gsmKey, Long houseId, Long houseProductId);

	Integer updateMiniVmsHash(List<CCTVMiniVMSVO> miniVmsList);

	void deleteOldForecastData();

	void insertForecastV2Data(LinkedHashMap<String, Object> value);


	void deleteOldWeatherData();
	Integer getWeatherConfigExists();
 void updateDataTime();
}