/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kt.smartfarm.supervisor.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import egovframework.customize.service.CCTVSettingVO;
import egovframework.customize.service.DeviceEnvVO;
import egovframework.customize.service.DeviceTypeVO;
import egovframework.customize.service.HouseEnvVO;
import egovframework.customize.service.ProductMethodVO;
import egovframework.customize.service.ProductVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper("houseEnvMapper")
public interface HouseEnvMapper {	
	Integer insert(HouseEnvVO map);
	Integer delete(Map<String,Object> map);
	Integer update(HouseEnvVO map);	
	HouseEnvVO get(@Param("gsm_key")Integer gsmKey, @Param("green_house_id") Integer greenHouseId);
	List<HouseEnvVO> list(Map<String,Object> map);
	
	List<HashMap<String, Object>> selectHouseTypeList();
	
	List<ProductVO> selectProductList();
	List<HashMap<String, Object>> selectProductMethod(HashMap<String, Object> param);
	
	void insertHouseDeviceMap(HashMap<String, Object> map);
	void deleteHouseDeviceMap(HashMap<String, Object> param);
	
    List<HashMap<String, Object>> getHouseDetail(Map<String,Object> map);
//	List<HashMap<String, Object>> getMappedDevice(Map<String, Object> map);
	List<Integer> getMappedDevice(Map<String, Object> map);
	List<HashMap<String, Object>> getMappedController(Map<String, Object> map);
	List<DeviceEnvVO> getHouseDeviceList(Integer houseId);
	
	HashMap<String, Object> insertCCTVSetting(CCTVSettingVO vo);
	List<HashMap<String, Object>> getCctvList(@Param("houseId")Integer houseId);
	Integer updateCctv(CCTVSettingVO vo);
	Integer deleteCctv(Integer cctvId);
	List<HashMap<String, Object>> getAllList();
	
	Integer insertForecastData(LinkedHashMap<String, Object> hm);
	Integer insertSunriseData(HashMap<String, Object> hm);
	
	List<HashMap<String, Object>> getWeatherCast(HashMap<String, Object> param);
	List<HashMap<String, Object>> getWeatherCategory();
	HashMap<String, Object> getSunriseInfo(Map<String, Object> map);
	
	List<HashMap<String, Object>> getGroundDeviceList(Integer houseId);
	List<DeviceTypeVO> getHouseDeviceTypeList(Integer houseId);
	
	
	
//	List<ProductMethodVO> selectProductMethodList(HashMap<String, Object> product);
//	List<HashMap<String, Object>> getProductSpeciesAndCategory(String productName);
	
	
}
