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

import com.kt.cmmn.util.AuthorityChecker;
import com.kt.smartfarm.service.AuthCheckService;
import com.kt.smartfarm.mapper.AuthCheckMapper;
import com.kt.cmmn.util.ClassUtil;
import com.kt.smartfarm.config.SmartfarmInterceptorConfig;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service("authCheckService")
public class AuthCheckServiceImpl extends EgovAbstractServiceImpl implements AuthCheckService {
	//@Autowired
	@Resource(name = "authCheckMapper")
	AuthCheckMapper authCheckMapper;

	@Autowired
	SmartfarmInterceptorConfig config;

	public String getClientUserIdx(String userIdx) {
		if( !StringUtils.isNumeric(userIdx) ) {
			Integer newClientUserId = authCheckMapper.selectUserIdxForOauthClientId(userIdx);
			if( newClientUserId != null ) {
				return String.valueOf(newClientUserId);
			}
		}
		return userIdx;
	}

	public AuthorityChecker getAuthCheck() {
		return new AuthorityChecker();
	}
	@Override
	public Boolean authCheck(Long gsmKey, Long houseId) {
		if (config.isSmartfarmSystem()) {
			return true;
		}
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) return false;
		if (authentication.getPrincipal() == null) return false;
		String userIdx = authentication.getPrincipal().toString();
		userIdx = getClientUserIdx(userIdx);
		HashMap<String, Object> param = new HashMap<>();
		param.put("user_idx", userIdx);
		param.put("gsm_key", gsmKey);
		param.put("house_id", houseId);
		List<HashMap<String, Object>> result = authCheckMapper.selectCheckAllowAuth(param);
		if (result != null) {
			Integer allowGsm = ClassUtil.castToSomething(result.get(0).get("allow_gsm"), Integer.class);
			Integer allowHouse = ClassUtil.castToSomething( result.get(0).get("allow_house"), Integer.class);
			if (gsmKey != null && allowGsm != 1) return false;
            return houseId == null || allowHouse == 1;
        }
		return false;
	}

	@Override
	public Boolean authCheck(Long gsmKey, Long houseId, List<Long>gsmKeyList, List<Long>houseIdList) {
		if (config.isSmartfarmSystem()) {
			return true;
		}
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) return false;
		if (authentication.getPrincipal() == null) return false;
		String userIdx = authentication.getPrincipal().toString();
		userIdx = getClientUserIdx(userIdx);
		HashMap<String, Object> param = new HashMap<>();
		param.put("user_idx", userIdx);
		param.put("gsm_key", gsmKey);
		param.put("house_id", houseId);
		param.put("gsm_key_list", gsmKeyList);
		param.put("house_id_list", houseIdList);
		Integer isAdmin = authCheckMapper.isAdminUser(param);
		if (isAdmin != null && isAdmin > 0) {
			return true;
		}
		Integer isAllowUser = authCheckMapper.selectCheckAllowAuth2(param);
        return isAllowUser != null && isAllowUser > 0;
    }

	public String getAuthUserIdx() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication ==null)
			return null;
		if(authentication.getPrincipal()==null)
			return null;
		String userIdx = authentication.getPrincipal().toString();
		userIdx = getClientUserIdx(userIdx);
		return userIdx;
	}
}
