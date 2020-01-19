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
import java.util.List;

import com.kt.smartfarm.service.CommonEnvVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.springframework.stereotype.Repository;

/**
 * monitoring에 관한 데이터처리 매퍼 클래스
 *
 * @author  Sean
 * @since 2018.07.23
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *
 *  수정일          			수정자           	수정내용
 *  -----------    		-----   	---------------------------
 *  2018.07.23        	Sean        최초 생성
 *
 * </pre>
 */
@Repository
@Mapper("commonEnvMapper")
public interface CommonEnvMapper {
	CommonEnvVO getOutWeather(int gsmKey);
	void updateOutWeather(CommonEnvVO vo);
	CommonEnvVO getOutWeatherDefault(int gsmKey);
	void updateOutWeatherDefault(CommonEnvVO vo);
	
	CommonEnvVO getThreshold(int gsmKey);
	void updateThreshold(CommonEnvVO vo);
	CommonEnvVO getThresholdDefault(int gsmKey);
	void updateThresholdDefault(CommonEnvVO vo);
	
	void updateEnvInfo(CommonEnvVO vo);
	CommonEnvVO getOutweatherInfo(HashMap<String, Object> param);
	
	HashMap<String, Object> getSensorInfoList();
	HashMap<String, Object> getDeviceInfoList();
	HashMap<String, Object> insertController(HashMap<String, Object> actuatorModuleInfo);
	HashMap<String, Object> insertSensorList();
	
	void insertDeviceList(HashMap<String, Object> row);
	void updateController(HashMap<String, Object> controllerInfo);	
//	int deleteDeviceOfChangedDeviceType(HashMap<String, Object> row);
//	void insertDeviceStatusUpdate(HashMap<String, Object> row);
//	void updateDeviceList(HashMap<String, Object> row);
//	void deleteDeviceOfRemovedDevice(String controllerId, List<String> deviceIdList);
//	void deleteDeviceInfoUsingControllerId(HashMap<String, Object> param);
//	void deleteControllerInfo(int controllerId);
	
	
//	void insertSensorControllerInfo(HashMap<String, Object> sensorListInfo);
//	void insertSensorDevice(HashMap<String, Object> sensorListInfo);
//	void updateSensorControllerInfo(HashMap<String, Object> sensorListInfo);
//	List<HashMap<String, Object>> selectedControllerInfo(HashMap<String, Object> param);
	
//	List<HashMap<String, Object>> selectSensorList(Integer sensorType);
//	Object getSensorChannel(boolean b);
//	Object getSensorGroupList(Object object);
	
//	List<HashMap<String, Object>> selectControllerListOfControlModule(HashMap<String, Object> param);
//	List<HashMap<String, Object>> selectControllerHouseMapList(HashMap<String, Object> param);
//	List<HashMap<String, Object>> selectDeviceOfControlModule(HashMap<String, Object> param);
	
	
}
