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

import com.kt.smartfarm.service.ControllerDepDeviceTypeVO;
import com.kt.smartfarm.service.ControllerEnvService;
import com.kt.smartfarm.service.ControllerEnvVO;
import com.kt.smartfarm.service.DeviceEnvVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.kt.smartfarm.supervisor.mapper.ControllerEnvMapper;
import com.kt.smartfarm.supervisor.mapper.DeviceEnvMapper;

@Slf4j
@Service("controllerEnvService")
public class ControllerEnvServiceImpl extends EgovAbstractServiceImpl implements ControllerEnvService {

	@Resource(name="controllerEnvMapper")
	ControllerEnvMapper controllerEnvMapper;

	@Resource(name="deviceEnvMapper")
	DeviceEnvMapper deviceEnvMapper;
	
	@Override
	public ControllerEnvVO insert(ControllerEnvVO vo) {				
		controllerEnvMapper.insert(vo);
		if( vo.getDeviceList() == null) {
			vo.setDeviceList( new ArrayList<>());
		}
        final List<ControllerDepDeviceTypeVO> dependencyDeviceTypeList = controllerEnvMapper.getDependencyDeviceTypeList(vo.getControllerInfoId());
        if (dependencyDeviceTypeList != null && dependencyDeviceTypeList.size() > 0 && vo.getDeviceList().size() == 0) {
        	//아직 안들어간경우에만 넣자.
            dependencyDeviceTypeList.stream().filter(d -> d.getAutoCreate() == 1).forEach(d -> {
                DeviceEnvVO device = new DeviceEnvVO();
                device.setControllerId(vo.getId());
                device.setGsmKey(vo.getGsmKey());
                device.setDeviceTypeId(d.getDeviceTypeId());
                device.setModbusAddress1(d.getDefaultAddress1());
                device.setModbusAddress2(d.getDefaultAddress2());
                device.setModbusAddress3(d.getDefaultAddress3());
                device.setDeviceTypeNickname(d.getDeviceTypeName());
                device.setNickname(d.getDefaultName());
                device.setDeviceTypeIdx(deviceEnvMapper.selectMaxDeviceTypeIdx(device));
                deviceEnvMapper.insert(device);
				vo.getDeviceList().add(device);
            });
        } else if( vo.getDeviceList().size() > 0 ) {
        	vo.getDeviceList().forEach( d -> {
				try {
					deviceEnvMapper.insert(d);
				} catch (Exception e) {
					log.info("Device Insert Fail in Controller : {}, {}", d, e.getMessage());
				}
			});
		}
		return vo;
	}

	@Override
	public Integer delete(Integer gsmKey, Integer controllerId) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsmKey",  gsmKey);
		map.put("id",  controllerId);
		return controllerEnvMapper.delete(map);
	}

	@Override
	public ControllerEnvVO update(ControllerEnvVO vo) {			
		controllerEnvMapper.update(vo);
		return vo;
	}

	@Override
	public ControllerEnvVO get(Integer gsmKey, Integer controllerId) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		map.put("id",  controllerId);		 
		return controllerEnvMapper.get(map).get(0);
	}

	@Override
	public List<ControllerEnvVO> list(Integer gsmKey) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);		
		return controllerEnvMapper.list(map);
	}

	@Override
	public List<HashMap<String, Object>> listType() {	
		return controllerEnvMapper.listType();
	}

	@Override
	public List<ControllerEnvVO> controllerDeviceList(Integer gsmKey) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		List<ControllerEnvVO> controllerList = controllerEnvMapper.list(map);
		for(ControllerEnvVO controller : controllerList){
			Integer controllerId = controller.getId();
			map.put("controller_id", controllerId);
			controller.setDeviceList(deviceEnvMapper.list(map));
			controller.getDeviceList().forEach( d ->
			{
				d.setRelationDeviceList(deviceEnvMapper.getVDeviceEnvList(d.getId()));
			});
		}
		return controllerList;

	}

	@Override
	public Integer copyToNewGSM(Integer fromGsmKey, Integer toGsmKey) {
		return controllerEnvMapper.copyToNewGSM(fromGsmKey,toGsmKey);
	}

}
