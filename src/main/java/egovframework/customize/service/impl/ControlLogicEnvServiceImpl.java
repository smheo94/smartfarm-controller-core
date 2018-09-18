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

import egovframework.customize.service.ControlLogicEnvService;
import egovframework.customize.service.ControlLogicVO;
import egovframework.customize.service.DeviceEnvVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.smartfarm.supervisor.mapper.ControlLogicMapper;


@Service("controlLogicEnvService")
public class ControlLogicEnvServiceImpl extends EgovAbstractServiceImpl implements ControlLogicEnvService {

	@Autowired
	ControlLogicMapper controlLogicMapper;
	
	@Override
	public HashMap<String,Object> list() {
		HashMap<String,Object> result = new HashMap<>();
		result.put("control_logic", controlLogicMapper.logicList());
		result.put("control_logic_device", controlLogicMapper.logicDeviceList());
		return result;
	}

	@Override
	public List<ControlLogicVO> getRegList(String gsmKey, String houseId) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("gsm_key", gsmKey);
		param.put("house_id", houseId);
		return controlLogicMapper.getRegList(param);
	}

	@Override
	public Object delete(String gsmKey, Integer controllerId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object insert(List<DeviceEnvVO> device) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object update(List<DeviceEnvVO> device) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<HashMap<String, Object>> getLogicProperties() {
		return controlLogicMapper.getLogicProperties();
	}
	
	
}
