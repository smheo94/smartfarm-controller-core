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

import java.util.HashMap;

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
@Mapper("commonEnvMapper")
public interface CommonEnvMapper {
	CommonEnvVO getOutWeather(Long gsmKey);
	void updateOutWeather(CommonEnvVO vo);
	CommonEnvVO getOutWeatherDefault(Long gsmKey);
	void updateOutWeatherDefault(CommonEnvVO vo);
	
	CommonEnvVO getThreshold(Long gsmKey);
	void updateThreshold(CommonEnvVO vo);
	CommonEnvVO getThresholdDefault(Long gsmKey);
	void updateThresholdDefault(CommonEnvVO vo);
	
	void updateEnvInfo(CommonEnvVO vo);
	CommonEnvVO getOutweatherInfo(HashMap<String, Object> param);
	
	HashMap<String, Object> getSensorInfoList();
	HashMap<String, Object> getDeviceInfoList();
	HashMap<String, Object> insertController(HashMap<String, Object> actuatorModuleInfo);
	HashMap<String, Object> insertSensorList();
	
	void insertDeviceList(HashMap<String, Object> row);
	void updateController(HashMap<String, Object> controllerInfo);	
	
}
