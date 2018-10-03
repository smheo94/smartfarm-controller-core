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

import egovframework.customize.service.ControlLogicDeviceVO;
import egovframework.customize.service.ControlLogicEnvService;
import egovframework.customize.service.ControlLogicVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.smartfarm.supervisor.mapper.ControlLogicMapper;


@Service("controlLogicEnvService")
public class ControlLogicEnvServiceImpl extends EgovAbstractServiceImpl implements ControlLogicEnvService {

	@Autowired
	ControlLogicMapper controlLogicMapper;
	@Override
	public List<ControlLogicVO> getAllLogicList() {
		final List<ControlLogicVO> logicList = controlLogicMapper.getLogicList();
		final List<ControlLogicDeviceVO> logicDeviceList = controlLogicMapper.getLogicDeviceList(null);
		final List<HashMap<String, Object>> logicPropertyList = controlLogicMapper.getLogicPropertyList(null);
		logicList.forEach( cl ->{
			cl.setControlDeviceaList( logicDeviceList.stream().filter(d -> d.getLogicId().equals(cl.getId())).collect(Collectors.toList()) );
			cl.setControlPropertyList( logicPropertyList.stream().filter( m -> m.get("logicId").equals(cl.getId())).collect(Collectors.toList()));
		});
		return logicList;
	}
//	@Override
//	public HashMap<String,Object> allList() {
//		HashMap<String,Object> result = new HashMap<>();
//		result.put("control_logic", controlLogicMapper.getLogicList());
//		result.put("control_logic_device", controlLogicMapper.getLogicDeviceList(null));
//		return result;
//	}
	@Override
	public List<ControlLogicVO> getLogicList() {
		return controlLogicMapper.getLogicList();
	}
	@Override
	public List<ControlLogicDeviceVO> getLogicDeviceList(Long logicId) {
		return controlLogicMapper.getLogicDeviceList(logicId);
	}
	@Override
	public List<HashMap<String, Object>> getLogicPropertyList(Long logicId) {
		return controlLogicMapper.getLogicPropertyList(logicId);
	}
	
	
}
