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

import com.kt.smartfarm.service.*;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper("deviceEnvMapper")
public interface DeviceEnvMapper {
	Integer insert( DeviceEnvVO device);
	Integer update( DeviceEnvVO vo);	
//	DeviceEnvVO getDevice(Long gsmKey, Long controllerId);
	List<DeviceEnvVO> list(Map<String, Object> map);
	Integer deleteControllerDeivces(Map<String,Object> map);

    Integer selectMaxDeviceTypeIdx(DeviceEnvVO vo);

	List<DeviceTypeVO> getDeviceTypeByHouseType(HashMap<String, Object> param);
	String[] getHouseType();
	String[] getKind();
	List<HashMap<String, Object>> getDeviceTypeByHouseType();
	List<HashMap<String, Object>> getDeviceTypeList();
	List<VDeviceInfoVO> getVDeviceList();

	Integer insertVDeviceEnv(VDeviceEnvVO vo);

	List<VDeviceEnvVO> getVDeviceEnvList(@Param("deviceId") Long deviceId);
	Integer deleteVDeviceEnv(@Param("id") Integer id, @Param("pDeviceId") Long pDeviceId, @Param("deviceNum") Integer deviceNum , @Param("deviceInsertOrder") Integer deviceInsertOrder);
	VDeviceEnvVO updateVDeviceEnv(VDeviceEnvVO vo);

	Integer copyToNewGSM(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );
	Integer copyToNewGSMVDeviceEnv(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );

    Integer deleteDevice(Long deviceId);

	//List<EDeviceEnvVO> getElectricDeviceEnvList(@Param("deviceId") Long deviceId);

	Integer insertEDeviceEnv(EDeviceEnvVO vo);
	EDeviceEnvVO updateEDeviceEnv(EDeviceEnvVO vo);
	//??
	List<EDeviceEnvVO> getEDeviceEnvList(@Param("deviceId") Long deviceId);
	Integer deleteEDeviceEnv(Integer id);
	Integer deleteVDevicesEnv(Long parentDeviceId);


}
