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
package egovframework.customize.service.impl;

import egovframework.customize.service.DeviceEnvService;
import egovframework.customize.service.DeviceEnvVO;
import egovframework.customize.service.DeviceTypeVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kt.smartfarm.supervisor.mapper.DeviceEnvMapper;


@Service("deviceEnvService")
public class DeviceEnvServiceImpl extends EgovAbstractServiceImpl implements DeviceEnvService {

	@Autowired
	DeviceEnvMapper deviceEnvMapper;
	
	@Override
	public List<DeviceEnvVO> insert(List<DeviceEnvVO> device) {			
		try{
			for(DeviceEnvVO vo :device){
				deviceEnvMapper.insert(vo);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return device;
	}

	@Override
	public Integer delete(String gsmKey, Integer deviceId) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		map.put("id",  deviceId);
		return deviceEnvMapper.delete(map);
	}

	@Override
	public List<DeviceEnvVO> update(List<DeviceEnvVO> device) {
		for(DeviceEnvVO vo :device){
			deviceEnvMapper.update(vo);	
		}		
		return device;
	}

	@Override
	public DeviceEnvVO get(String gsmKey, Integer controllerId) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		map.put("id",  controllerId);
		return deviceEnvMapper.list(map).stream().findFirst().orElse(null);
	}

	@Override
	public List<DeviceEnvVO> list(String gsmKey, Integer controllerId) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		map.put("controller_id",  controllerId);
		return deviceEnvMapper.list(map);
	}

	@Override
	public List<DeviceTypeVO> getDeviceList(String houseType, String kind) {
		String [] controllerTypeArr = houseType.split(",");
		Integer [] controllerTypeList =new Integer[controllerTypeArr.length];
		List<Integer> idsList = new ArrayList<>();	
		for(int i=0; i<controllerTypeList.length; i++){
			idsList.add(Integer.parseInt(controllerTypeArr[i]));
		}
		HashMap<String,Object> param = new HashMap<>();
		param.put("kind", kind);
		param.put("idsList", idsList);
		return deviceEnvMapper.getDeviceList(param);		
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
		return deviceEnvMapper.getDeviceTypeList();
	}
  
}
