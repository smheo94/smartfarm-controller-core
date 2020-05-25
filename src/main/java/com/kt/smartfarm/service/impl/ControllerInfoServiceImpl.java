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

import com.kt.smartfarm.service.ControllerInfoService;
import com.kt.smartfarm.service.ControllerInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kt.smartfarm.mapper.ControllerInfoMapper;


@Service("controllerInfoService")
public class ControllerInfoServiceImpl extends EgovAbstractServiceImpl implements ControllerInfoService {

	@Resource(name="controllerInfoMapper")
	ControllerInfoMapper controllerInfoMapper;
		

	@Override
	public ControllerInfoVO get(Integer controllerInfoId) {
		Map<String, Object> map = new HashMap<>();		
		map.put("controller_info_id",  controllerInfoId);
		return controllerInfoMapper.list(map).stream().findFirst().orElse(null);
	}

	@Override
	public List<ControllerInfoVO> list() {
		Map<String, Object> map = new HashMap<>();			
		return controllerInfoMapper.list(map);
	}
  
}
