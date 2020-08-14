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
package com.kt.smartfarm.mapper;

import com.kt.smartfarm.service.*;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Mapper("houseEnvMapper")
public interface HouseEnvMapper {
	Long insert(HouseEnvVO map);
	Integer delete(Map<String,Object> map);
	Integer update(HouseEnvVO map);	
	HouseEnvVO get(@Param("gsm_key")Long gsmKey, @Param("green_house_id") Long greenHouseId);
	List<HouseEnvVO> list(Map<String,Object> map);
	
	List<HashMap<String, Object>> selectHouseTypeList();
	
	List<ProductVO> selectProductList();
	List<HashMap<String, Object>> selectProductMethod(HashMap<String, Object> param);
	
	void insertHouseDeviceMap(HashMap<String, Object> map);
	void deleteHouseDeviceMap(HashMap<String, Object> param);
	
    List<HashMap<String, Object>> getHouseDetail(Map<String,Object> map);
//	List<HashMap<String, Object>> getMappedDevice(Map<String, Object> map);
	List<Long> getMappedDevice(Map<String, Object> map);
	List<HashMap<String, Object>> getMappedController(Map<String, Object> map);
	List<HashMap<String, Object>> getNutrientController(Map<String, Object> map);
	List<DeviceEnvVO> getHouseDeviceList(Long houseId);
	List<ControlLogicSettingVO> getHouseControlSettings(Long houseId);
	
	Integer insertCCTVSetting(CCTVSettingVO vo);
	List<CCTVSettingVO> getCctvList(@Param("houseId")Long houseId);
	Integer updateCctv(CCTVSettingVO vo);
	Integer updateMiniVmsHash(CCTVMiniVMSVO v);
	Integer deleteCctv(Integer cctvId);
	List<HashMap<String, Object>> getAllList();

	Integer insertHouseProduct(HouseProductVO vo);
	Integer updateHouseProduct(HouseProductVO vo);
	Integer deleteHouseProduct(@Param("gsmKey")Long gsmKey, @Param("greenHouseId") Long greenHouseId, @Param("id")Long houseProductId);
	List<HouseProductVO> listHouseProduct(@Param("gsmKey")Long gsmKey, @Param("greenHouseId") Long greenHouseId, @Param("id")Long houseProductId);

	Integer insertForecastData(LinkedHashMap<String, Object> hm);
//	Integer insertSunriseData(HashMap<String, Object> hm);
	Integer insertUltraShortWeatherData(LinkedHashMap<String , Object> hm);

	Integer getWeatherConfigExists();
	
	List<HashMap<String, Object>> getWeatherCast(HashMap<String, Object> param);
	List<HashMap<String, Object>> getWeatherCategory();
//	HashMap<String, Object> getSunriseInfo(Map<String, Object> map);
	
	List<HashMap<String, Object>> getGroundDeviceList(Long houseId);
	List<DeviceTypeVO> getHouseDeviceTypeList(Long houseId);

	Integer copyToNewGSM(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );
	Integer copyToNewGSMMapGreenHouseDevice(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );
	Integer copyToNewGSMHouseProduct(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );

    void deleteOldWeatherData();
	void deleteOldForecastData();
	void deleteOldForecastDataV2();
	void insertForecastV2Data(Map<String, Object> foreCastData);
	List<HashMap<String, Object>> getWeatherCastV2(Map<String, Object> param);

	UltraShortWeatherDataVO getUltraShortWeatherData(HashMap<String, Object> param);
}
