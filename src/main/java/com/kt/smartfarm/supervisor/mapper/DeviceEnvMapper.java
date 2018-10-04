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

import egovframework.customize.service.DeviceEnvVO;
import egovframework.customize.service.DeviceTypeVO;
import egovframework.customize.service.VDeviceEnvVO;
import egovframework.customize.service.VDeviceInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper("deviceEnvMapper")
public interface DeviceEnvMapper {
	
	Integer insert( DeviceEnvVO device);
	Integer update( DeviceEnvVO vo);	
//	DeviceEnvVO getDevice(Integer gsmKey, Integer controllerId);
	List<DeviceEnvVO> list(Map<String, Object> map);
	Integer delete(Map<String,Object> map);


	List<DeviceTypeVO> getDeviceTypeByHouseType(HashMap<String, Object> param);
	String[] getHouseType();
	String[] getKind();
	List<HashMap<String, Object>> getDeviceTypeByHouseType();
	List<VDeviceInfoVO> getVDeviceList();

	Integer insertVDeviceEnv(VDeviceEnvVO vo);

	List<VDeviceEnvVO> getVDeviceEnvList(@Param("deviceId") Integer deviceId);

}
