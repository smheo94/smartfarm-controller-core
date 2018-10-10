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
import egovframework.customize.service.ControllerInfoService;
import egovframework.customize.service.ControllerInfoVO;
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

import com.kt.smartfarm.supervisor.mapper.ControllerInfoMapper;


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
