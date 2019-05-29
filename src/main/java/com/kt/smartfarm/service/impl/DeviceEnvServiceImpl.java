/*
 * Copyright 2008-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kt.smartfarm.service.impl;

import com.kt.smartfarm.service.DeviceEnvService;
import com.kt.smartfarm.service.DeviceEnvVO;
import com.kt.smartfarm.service.DeviceTypeVO;
import com.kt.smartfarm.service.VDeviceEnvVO;
import com.kt.smartfarm.service.VDeviceInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.kt.smartfarm.supervisor.mapper.DeviceEnvMapper;

import javax.annotation.Resource;


@Service("deviceEnvService")
public class DeviceEnvServiceImpl extends EgovAbstractServiceImpl implements DeviceEnvService {

	private static final Logger log = LoggerFactory.getLogger(DeviceEnvServiceImpl.class);
	@Resource(name="deviceEnvMapper")
	DeviceEnvMapper deviceEnvMapper;
	
	@Override
	public List<DeviceEnvVO> insert(List<DeviceEnvVO> device) {			
		try{
            log.info("Insert List : {}", device);
			for(DeviceEnvVO vo :device){
			    log.info("Insert Device : {}", vo);
				deviceEnvMapper.insert(vo);
				if(vo.getRelationDeviceList() != null){
					for(VDeviceEnvVO vDevice: vo.getRelationDeviceList()){
						vDevice.setParentDeviceId(vo.getId());
						deviceEnvMapper.insertVDeviceEnv(vDevice);
					}	
				}
				
			}			
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return device;
	}

	@Override
	public Integer deleteControllerDeivces(Integer gsmKey, Integer controller_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		map.put("controller_id",  controller_id);
		return deviceEnvMapper.deleteControllerDeivces(map);
	}

	@Override
	public List<DeviceEnvVO> update(List<DeviceEnvVO> device) {
		for(DeviceEnvVO vo :device){
			deviceEnvMapper.update(vo);	
		}		
		return device;
	}

//	@Override
//	public DeviceEnvVO getDevice(Integer gsmKey, Integer controllerId) {
//		Map<String, Object> map = new HashMap<>();
//		map.put("gsm_key",  gsmKey);
//		map.put("id",  controllerId);
//		return deviceEnvMapper.list(map).stream().findFirst().orElse(null);
//	}

	@Override
	public List<DeviceEnvVO> list(Integer gsmKey, Integer controllerId, Boolean withVDeviceList) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key", gsmKey);
		map.put("controller_id", controllerId);
		final List<DeviceEnvVO> deviceList = deviceEnvMapper.list(map);
		if (withVDeviceList) {
			for (DeviceEnvVO vo : deviceList) {
				vo.setRelationDeviceList(getVDeviceEnvList(vo.getId()));
			}
		}
		return deviceList;
	}

	@Override
	public DeviceEnvVO getDevice(Integer deviceId, Boolean withVDeviceList) {
		Map<String, Object> map = new HashMap<>();
		map.put("device_id",  deviceId);
		DeviceEnvVO device = deviceEnvMapper.list(map).stream().findFirst().orElse(null);
		if( withVDeviceList ) {
			device.setRelationDeviceList(getVDeviceEnvList(deviceId));
		}
		return device;
	}

	@Override
	public List<DeviceTypeVO> getDeviceTypeByHouseType(String houseType, String kind) {
		String [] controllerTypeArr = houseType.split(",");
		Integer [] controllerTypeList =new Integer[controllerTypeArr.length];
		List<Integer> idsList = new ArrayList<>();	
		for(int i=0; i<controllerTypeList.length; i++){
			idsList.add(Integer.parseInt(controllerTypeArr[i]));
		}
		HashMap<String,Object> param = new HashMap<>();
		param.put("kind", kind);
		param.put("idsList", idsList);
		return deviceEnvMapper.getDeviceTypeByHouseType(param);
	}

	@Override
	public HashMap<String, Object> gethouseTypeKindInfo() {
		HashMap<String,Object> result = new HashMap<>();
		
		result.put("houseType", deviceEnvMapper.getHouseType());
		result.put("kind", deviceEnvMapper.getKind());
		return result;
	}

	@Override
	public List<HashMap<String, Object>> getDeviceTypeList() {
//		return deviceEnvMapper.getDeviceTypeByHouseType();
		return deviceEnvMapper.getDeviceTypeList();
	}

	@Override
	public List<VDeviceInfoVO> getVDeviceList() {
		return deviceEnvMapper.getVDeviceList();		
	}

	@Override
	public List<VDeviceEnvVO> insertVDeviceEnv(List<VDeviceEnvVO> voList) {
		try{
			for(VDeviceEnvVO vo :voList){
				deviceEnvMapper.insertVDeviceEnv(vo);
			}
		}catch(Exception e){
			log.debug(e.getMessage());
		}		
		return voList;
	}

	@Override
	public List<VDeviceEnvVO> getVDeviceEnvList(Integer deviceId) {
		try {
			return deviceEnvMapper.getVDeviceEnvList(deviceId);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}

	
	@Override
	public VDeviceEnvVO updateVDeviceEnv(VDeviceEnvVO vo) {
		try{
			return deviceEnvMapper.updateVDeviceEnv(vo);
		}catch(Exception e){
			log.debug(e.getMessage());
			return null;
		}		
	}

	@Override
	public Integer deleteVDeviceEnv(Integer id) {
		try{
			return deviceEnvMapper.deleteVDeviceEnv(id);
		}catch(Exception e){
			log.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public Integer copyToNewGSM(Integer fromGsmKey, Integer toGsmKey) {
		deviceEnvMapper.copyToNewGSM(fromGsmKey, toGsmKey);
		return deviceEnvMapper.copyToNewGSMVDeviceEnv(fromGsmKey, toGsmKey);
	}

}
