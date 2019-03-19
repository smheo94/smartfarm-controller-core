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

import com.kt.smartfarm.supervisor.mapper.AuthCheckMapper;
import com.kt.smartfarm.supervisor.mapper.CategoryEnvMapper;
import egovframework.cmmn.SystemType;
import egovframework.customize.config.SmartfarmInterceptorConfig;
import egovframework.customize.service.AuthCheckService;
import egovframework.customize.service.CategoryEnvService;
import egovframework.customize.service.CategoryEnvVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service("authCheckService")
public class AuthCheckServiceImpl extends EgovAbstractServiceImpl implements AuthCheckService {
	//@Autowired
	@Resource(name = "authCheckMapper")
	AuthCheckMapper authCheckMapper;

	@Autowired
	SmartfarmInterceptorConfig config;

	@Override
	public Boolean authCheck(Integer gsmKey, Integer houseId) {
		if( Objects.equals(config.SYSTEM_TYPE, SystemType.SYSTEM_TYPE_SMARTFARM) ) {
			//20190318 - 제어기에서는 검사 안해요
			return true;
		}
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if( authentication == null )
			return false;
		if( authentication.getPrincipal() == null )
			return false;
		String userIdx = authentication.getPrincipal().toString();
		HashMap<String,Object> param = new HashMap<>();
		param.put("user_idx", userIdx);
		param.put("gsm_key", gsmKey);
		param.put("house_id", houseId);
		List<HashMap<String,Object>> result = authCheckMapper.selectCheckAllowAuth(param);
		if( result != null ) {
			Integer allowGsm = (Integer) result.get(0).get("allow_gsm");
			Integer allowHouse = (Integer) result.get(0).get("allow_house");
			if (gsmKey != null && allowGsm == null) return false;
			if (houseId != null && allowHouse == null) return false;
			return true;
		}
		return false;
	}
}
