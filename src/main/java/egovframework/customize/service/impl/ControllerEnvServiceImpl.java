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

import egovframework.cmmn.util.Result;
import egovframework.customize.service.ArrayDeviceVO;
import egovframework.customize.service.ControllerEnvService;
import egovframework.customize.service.ControllerEnvVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.client.RestTemplate;

import com.kt.smartfarm.supervisor.mapper.ControllerEnvMapper;
import com.kt.smartfarm.supervisor.mapper.DeviceEnvMapper;


@Service("controllerEnvService")
public class ControllerEnvServiceImpl extends EgovAbstractServiceImpl implements ControllerEnvService {

	@Autowired
	ControllerEnvMapper controllerEnvMapper;
	
	@Autowired
	DeviceEnvMapper deviceEnvMapper;
	
	@Override
	public ControllerEnvVO insert(ControllerEnvVO vo) {				
		controllerEnvMapper.insert(vo);

		return vo;
	}

	@Override
	public Integer delete(String gsmKey, Integer controllerId) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		map.put("id",  controllerId);
		return controllerEnvMapper.delete(map);
	}

	@Override
	public ControllerEnvVO update(ControllerEnvVO vo) {			
		controllerEnvMapper.update(vo);
		return vo;
	}

	@Override
	public ControllerEnvVO get(String gsmKey, Integer controllerId) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		map.put("id",  controllerId);
		return controllerEnvMapper.list(map).stream().findFirst().orElse(null);
	}

	@Override
	public List<ControllerEnvVO> list(String gsmKey) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);		
		return controllerEnvMapper.list(map);
	}

	@Override
	public List<HashMap<String, Object>> listType() {	
		return controllerEnvMapper.listType();
	}

	@Override
	public List<ControllerEnvVO> controllerDeviceList(String gsmKey) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		List<ControllerEnvVO> controllerList = controllerEnvMapper.list(map);
		for(ControllerEnvVO controller : controllerList){
			Integer controllerId = controller.getId();
			map.put("controller_id", controllerId);
			controller.setDeviceList(deviceEnvMapper.list(map));
			
		}
		return controllerList;

	}
  
}
