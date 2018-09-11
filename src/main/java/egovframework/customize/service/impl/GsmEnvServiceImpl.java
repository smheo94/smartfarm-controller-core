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

import egovframework.customize.service.GsmEnvService;
import egovframework.customize.service.GsmEnvVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kt.smartfarm.supervisor.mapper.GsmEnvMapper;


@Service("gsmEnvService")
public class GsmEnvServiceImpl extends EgovAbstractServiceImpl implements GsmEnvService {

	@Autowired
	GsmEnvMapper gsmEnvMapper;

	@Override
	public List<GsmEnvVO> list() {
		Map<String,Object> map = new HashMap<>();
		map.put("gsm_key", null);
		return gsmEnvMapper.list(map);		
	}

	@Override
	public GsmEnvVO get(String gsmKey) {
		Map<String,Object> map = new HashMap<>();
		map.put("gsm_key", gsmKey);
		return gsmEnvMapper.list(map).stream().findFirst().orElse(null);
	}

	@Override
	public Integer delete(String gsmKey) {
		Map<String,Object> map = new HashMap<>();
		map.put("gsm_key", gsmKey);
		return gsmEnvMapper.delete(map);
	}

	@Override
	public Integer insert(GsmEnvVO gsmInfo) {
		return gsmEnvMapper.insert(gsmInfo);
	}

	@Override
	public Integer update(GsmEnvVO gsmInfo) {
		return gsmEnvMapper.update(gsmInfo);
	}
  
}
