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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.cmmn.util.RestClientUtil;
import com.kt.smartfarm.service.*;
import com.kt.smartfarm.intercepter.ResponseResult;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@Slf4j
@Service("autoSyncService")
public class AutoSyncServiceImpl extends EgovAbstractServiceImpl implements AutoSyncService {

	@Resource(name="gsmEnvService")
    GsmEnvService gsmEnvService;

	@Resource(name="houseEnvService")
    HouseEnvService houseEnvService;

	@Resource(name = "controlLogicSettingService")
	private ControlLogicSettingService controlLogicSettingService;

	@Resource(name="controllerEnvService")
    ControllerEnvService controllerEnvService;

	private ObjectMapper objMapper =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@Override
	public Integer autoSync(Long gsmkey, HttpServletRequest request) {

		final List<ControlLogicSettingVO> logicSettingList = controlLogicSettingService.getLogicSetting(gsmkey, null, null);

		final GsmEnvVO gsmEnvVO = gsmEnvService.get(gsmkey, true);
		String httpSchema = gsmEnvVO.getHttpSchema();
		String server  = gsmEnvVO.getSystemHost();
		Integer port = gsmEnvVO.getSystemPort();
		try {
			URI uri = new URI(httpSchema, null, server, port, null, null, null);
			uri = UriComponentsBuilder.fromUri(uri).path("/env/control_logic_setting").build(true).toUri();
			HttpHeaders headers = new HttpHeaders();
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				headers.set(headerName, request.getHeader(headerName));
			}
			RestTemplate restTemplate = new RestTemplate();
			RestClientUtil.setIgnoreCertificateSSL(restTemplate);
			for(ControlLogicSettingVO  logicSettingVO : logicSettingList ) {
				try {
					HttpEntity httpEntity = new HttpEntity<String>(null, headers);
					uri = UriComponentsBuilder.fromUri(uri).path(String.format("/env/control_logic_setting/%s", logicSettingVO.controlSettingId)).build(true).toUri();
					ResponseEntity<ResponseResult> returnValue = restTemplate.exchange(uri, HttpMethod.valueOf(request.getMethod()), httpEntity, ResponseResult.class);
					httpEntity = new HttpEntity<String>( objMapper.writeValueAsString(logicSettingVO), headers);
					uri = UriComponentsBuilder.fromUri(uri).path(String.format("/env/control_logic_setting", logicSettingVO.controlSettingId)).build(true).toUri();
					returnValue = restTemplate.exchange(uri, HttpMethod.valueOf(request.getMethod()), httpEntity, ResponseResult.class);
				} catch (JsonProcessingException e) {
					log.error("autoSync Error : {}", e);
					return 0;
				}
			}
		} catch (URISyntaxException e) {
			log.error("autoSync Error : {}", e);
			return 0;
		}
		return 1;
	}
}
