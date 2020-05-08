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

import com.kt.cmmn.util.JasyptUtil;
import com.kt.smartfarm.service.SystemService;
import com.kt.smartfarm.supervisor.mapper.DBSchemaMapper;
import com.kt.smartfarm.supervisor.mapper.SystemMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("systemService")
public class SystemServiceImpl extends EgovAbstractServiceImpl implements SystemService {
    @Resource(name="systemMapper")
    private SystemMapper systemMapper;

    @Resource(name="dbSchemaMapper")
	private DBSchemaMapper dbSchemaMapper;

	@Override
	public Map<String, Object> getAppVersion(String appName) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appName", appName);
		return systemMapper.getAppVersion(param);
	}

}
