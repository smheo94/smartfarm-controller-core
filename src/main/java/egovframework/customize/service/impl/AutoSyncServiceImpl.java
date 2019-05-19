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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.smartfarm.supervisor.mapper.GsmEnvMapper;
import egovframework.cmmn.util.RestClientUtil;
import egovframework.customize.intercepter.ResponseResult;
import egovframework.customize.service.*;
import egovframework.customize.web.ControllerEnvController;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

import static egovframework.customize.message.ApplicationMessage.NOT_FOUND_GSM_INFO;
import static org.springframework.boot.Banner.Mode.LOG;


@Slf4j
@Service("autoSyncService")
public class AutoSyncServiceImpl extends EgovAbstractServiceImpl implements AutoSyncService {

	@Resource(name="gsmEnvMapper")
	GsmEnvMapper gsmEnvMapper;
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
	public Integer autoSync(Integer gsmkey, HttpServletRequest request) {

		final List<ControlLogicSettingVO> logicSettingList = controlLogicSettingService.getLogicSetting(gsmkey, null, null);

		final GsmEnvVO gsmEnvVO = gsmEnvMapper.get(gsmkey);
		String server  = gsmEnvVO.getSystemHost();
		Integer port = gsmEnvVO.getSystemPort();
		try {
			URI uri = new URI("http", null, server, port, null, null, null);
			uri = UriComponentsBuilder.fromUri(uri).path("/env/control_logic_setting").build(true).toUri();
			HttpHeaders headers = new HttpHeaders();
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				headers.set(headerName, request.getHeader(headerName));
			}
			RestTemplate restTemplate = new RestTemplate();
			for(ControlLogicSettingVO  logicSettingVO : logicSettingList ) {
				try {
					HttpEntity httpEntity = new HttpEntity<String>(null, headers);
					uri = UriComponentsBuilder.fromUri(uri).path(String.format("/env/control_logic_setting/%s", logicSettingVO.controlSettingId)).build(true).toUri();
					ResponseEntity<ResponseResult> returnValue = restTemplate.exchange(uri, HttpMethod.DELETE.valueOf(request.getMethod()), httpEntity, ResponseResult.class);
					httpEntity = new HttpEntity<String>( objMapper.writeValueAsString(logicSettingVO), headers);
					uri = UriComponentsBuilder.fromUri(uri).path(String.format("/env/control_logic_setting", logicSettingVO.controlSettingId)).build(true).toUri();
					returnValue = restTemplate.exchange(uri, HttpMethod.POST.valueOf(request.getMethod()), httpEntity, ResponseResult.class);
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
