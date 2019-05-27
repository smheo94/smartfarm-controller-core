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
import java.util.Map;

import egovframework.customize.service.ArrayDeviceVO;
import egovframework.customize.service.ControllerEnvVO;
import egovframework.customize.service.DeviceEnvVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper("controllerEnvMapper")
public interface ControllerEnvMapper {
	
	Integer insert(ControllerEnvVO map);
	Integer delete(Map<String,Object> map);
	Integer update(ControllerEnvVO map);	
	List<ControllerEnvVO> list(Map<String,Object> map);
	void insertDeviceList(ArrayDeviceVO row);
	void insertDeviceStatusUpdate(ArrayDeviceVO row);
	List<HashMap<String, Object>> listType();
	List<ControllerEnvVO> controllerDeviceList(Map<String, Object> map);
	List<ControllerEnvVO> get(Map<String, Object> map);
	
	List<DeviceEnvVO> getDeviceList(Integer controller_id);


	Integer copyToNewGSM(@Param("from_gsm_key") Integer fromGsmKey, @Param("to_gsm_key") Integer toGsmKey );
}
