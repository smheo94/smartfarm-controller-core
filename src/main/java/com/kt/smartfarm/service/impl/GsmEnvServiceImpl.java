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
import com.kt.cmmn.util.MapUtils;
import com.kt.smartfarm.config.SmartfarmInterceptorConfig;
import com.kt.smartfarm.service.*;
import com.kt.smartfarm.supervisor.mapper.*;
import com.kt.cmmn.util.ClassUtil;
import com.kt.smartfarm.config.SecurityConfig;
import com.kt.smartfarm.intercepter.ResponseResult;
import com.kt.smartfarm.intercepter.SmartFarmDataInterceptor;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.kt.smartfarm.message.ApplicationMessage.NOT_FOUND_GSM_INFO;


@Slf4j
@Service("gsmEnvService")
public class GsmEnvServiceImpl extends EgovAbstractServiceImpl implements GsmEnvService {


	@Resource(name="gsmEnvMapper")
    GsmEnvMapper gsmEnvMapper;

    @Resource(name="thresholdService")
	ThresholdService thresholdService;

    @Resource(name="controllerEnvService")
    ControllerEnvService controllerEnvService;

    @Resource(name="deviceEnvService")
    DeviceEnvService deviceEnvService;

    @Resource(name="controlLogicSettingService")
	ControlLogicSettingService controlLogicSettingService;

	@Resource(name="houseEnvService")
	HouseEnvService houseEnvService;

	@Autowired
	SecurityConfig securityConfig;

	@Autowired
	SmartfarmInterceptorConfig smartfarmConfig;

	@Override
	public List<GsmEnvVO> gsmOfDeviceList(Integer gsmKey) {
		return gsmEnvMapper.gsmOfDeviceList(gsmKey);
	}

	@PostConstruct
	public void initGsmKeyOnSmartfarm() {
		if( smartfarmConfig.isSmartfarmSystem() ) {
			Integer gsmKey = 0;
			try {
				String gsmKeyValue = smartfarmConfig.GSM_KEY;
				gsmKey = Integer.parseInt(gsmKeyValue);
			} catch (Exception e) {
				log.warn("Load GSM Key Error !!!", e);
			}
			if( gsmKey == 0 ) {
				//gsmKey가 0로면 할일이 없음
				return;
			}
			GsmEnvVO gsmInfo = gsmEnvMapper.get(gsmKey);
			if( gsmInfo == null || gsmInfo.getGsmKey() == 0 ) {
				gsmInfo = gsmEnvMapper.get(-1);
				if( gsmInfo == null ) {
					gsmInfo = new GsmEnvVO();
					gsmInfo.setGsmKey(gsmKey);
					insert(gsmInfo);
					log.info("Create new GsmInfo : {}", gsmKey);
				} else {
					log.info("Already exists GSM : {}", gsmInfo);
				}
			}
		}
	}
//	@Override
//	public GsmEnvVO get(Integer gsmKey) {
//		return gsmEnvMapper.get(gsmKey);
//	}

	@Override
	public Map<String, Object> get(Integer gsmKey, boolean all, Boolean isSmartfarmSystem) {
		GsmEnvVO gsmEnv = gsmEnvMapper.getGsmList(gsmKey,null,null,null, null, null).stream().findFirst().orElse(null);
		Map<String,Object> gsmMap = MapUtils.convertToMap(gsmEnv);
		List<HashMap<String, Object>> houseList = houseEnvService.list(gsmKey, all, true, isSmartfarmSystem);
		gsmMap.put("houseList", houseList);
		return gsmMap;
	}

	@Override
	public Integer delete(Integer gsmKey) {
		return gsmEnvMapper.delete(gsmKey);
	}

	@Override
	public Integer insert(GsmEnvVO gsmInfo) {
		Integer gsmKey = gsmEnvMapper.insert(gsmInfo);
		gsmEnvMapper.createGSMSeq(gsmInfo.getGsmKey());
		return gsmKey;
	}

	@Override
	public Integer update(GsmEnvVO gsmInfo) {
		return gsmEnvMapper.update(gsmInfo);
	}

	@Override
	public List<Map<String, Object>> list(boolean all,Integer userInfoId, Integer categoryId, String farmName, String authUserIdx, Boolean isSmartfarmSystem, Boolean isCCTVOnly) {
        List<GsmEnvVO> gsmEnvList = gsmEnvMapper.getGsmList(null, userInfoId, categoryId, farmName, authUserIdx, isSmartfarmSystem);
        if( gsmEnvList == null && gsmEnvList.size() == 0 ) {
        	return new ArrayList<>();
		}
		List<Map<String, Object>> gsmList = gsmEnvList.stream().map( g -> MapUtils.convertToMap(g) ).collect(Collectors.toList());
		for(Map<String,Object> gsm : gsmList){
//			Integer gsmKey = (Integer)gsm.get("gsmKey");
//			houseList = gsmEnvMapper.getHouseList(gsmKey);
			Integer gsmKey = (Integer)gsm.get("gsmKey");
			List<HashMap<String,Object>> houseList = houseEnvService.list(gsmKey, all, false, isSmartfarmSystem, isCCTVOnly);
			if( isCCTVOnly && (houseList == null  || houseList.size() == 0 ))  {
				//CCTV가 없으면 리스트를 줄 필요가 없음
				continue;
			}
			gsm.put("houseList", houseList);			
		}
		if( isCCTVOnly )  {
			return gsmList.stream().filter( g -> g.get("houseList") != null ).collect(Collectors.toList());
		}
		return gsmList;
	}

	@Override
	public List<GsmEnvVO> notMappedList() {
		return gsmEnvMapper.notMappedList();		
	}



	@Override
	public Integer userRegistGSM(HashMap<String,Object> param, Integer gsmKey) {
		param.put("gsmKey", gsmKey);
		return gsmEnvMapper.userRegistGSM(param);		
	}

//	@Override
//	public List<GsmEnvVO> getGsmInfoByUser(Integer userInfoId) {
//		return gsmEnvMapper.getGsmInfoByUser(userInfoId);
//	}

	@Override
	public GsmThresholdVO gsmThresholdInsert(GsmThresholdVO gsmThresholdVO) {
		gsmEnvMapper.gsmThresholdInsert(gsmThresholdVO);
		return gsmThresholdVO;
	}

	@Override
	public GsmThresholdVO gsmThresholdUpdate(GsmThresholdVO gsmThresholdVO) {
		gsmEnvMapper.gsmThresholdUpdate(gsmThresholdVO);
		return gsmThresholdVO;
	}

	@Override
	public GsmThresholdVO gsmThresholdGet(HashMap<String, Object> param) {
		return gsmEnvMapper.gsmThresholdGet(param);
	}

	@Override
	public Integer copyToNewGsm(HttpServletRequest request, Integer fromGsmKey, Integer toGsmKey) {
	    Integer setValue = gsmEnvMapper.getNewGSMSeqValue(fromGsmKey, toGsmKey);
		gsmEnvMapper.setNewGSMSeqKey(toGsmKey, setValue);
        thresholdService.copyToNewGSM(fromGsmKey, toGsmKey);

        controllerEnvService.copyToNewGSM(fromGsmKey, toGsmKey);
		deviceEnvService.copyToNewGSM(fromGsmKey, toGsmKey);
        houseEnvService.copyToNewGSM(fromGsmKey, toGsmKey);
        //houseEnvService.copyToNewGSMMapGreenHouseDevice(fromGsmKey, toGsmKey);

        //deviceEnvService.copyToNewGSMVDeviceEnv(fromGsmKey, toGsmKey);
        controlLogicSettingService.copyToNewGSM(fromGsmKey,toGsmKey);
		return HttpStatus.OK.value();
		//20190613 권용식 부장 요청으로 싱크는 별개로 진행
		//return syncToSmartfarm(request, toGsmKey);
    }

    private final String CONTROLLER_INSERT_URL = "/env/%s/controller/";
	private final String CONTROLLER_UPDATE_URL = "/env/%s/controller/%s";
	private final String DEVICE_INSERT_URL = "/env/device";
	private final String DEVICE_RELATION_DELETE_URL = "/env/device/%s/relationdevices";
	private final String DEVICE_RELATION_INSERT_URL = "/env/device/%s/relationdevices";
	private final String HOUSE_INSERT_URL = "/env/%s/house";
	private final String HOUSE_UPDATE_URL = "/env/%s/house";
	private final String HOUSE_DEVICE_LINK_DELETE_URL = "/env/%s/house/linkDevice";
	private final String HOUSE_DEVICE_LINK_INSERT_URL = "/env/%s/house/linkDevice";
	private final String HOUSE_CONTROL_LOGIC_SETTING_INSERT_URL = "/env/control_logic_setting";
	private final String HOUSE_CONTROL_LOGIC_SETTING_UPDATE_URL = "/env/control_logic_setting/%s";

	@Override
    public Integer syncToSmartfarm(HttpServletRequest request, Integer gsmKey) {
		final Map<String, Object> gsmInfo = this.get(gsmKey, true, true);
		List<Map<String,Object>> houseList = (List<Map<String,Object>>)gsmInfo.get("houseList");
        //List<Map<String,Object>> controllerList = (List<Map<String,Object>>)gsmInfo.get("controllerList");
		final List<ControllerEnvVO> contorollerEnvList = controllerEnvService.controllerDeviceList(gsmKey);
		if( contorollerEnvList != null ) {
			contorollerEnvList.forEach(c -> {
				sendRestBodyData(request, String.format(CONTROLLER_INSERT_URL, gsmKey), HttpMethod.POST, gsmKey, c);
				sendRestBodyData(request, String.format(CONTROLLER_UPDATE_URL, gsmKey, c.getId()), HttpMethod.PUT, gsmKey, c);
				if (c.getDeviceList() != null && c.getDeviceList().size() > 0 ) {
					sendRestBodyData(request, String.format(DEVICE_INSERT_URL), HttpMethod.POST, gsmKey, c.getDeviceList());
					c.getDeviceList().forEach(d -> {
						sendRestBodyData(request, String.format(DEVICE_RELATION_DELETE_URL, d.getId()), HttpMethod.DELETE, gsmKey, null);
						final List<VDeviceEnvVO> vDeviceEnvList = deviceEnvService.getVDeviceEnvList(d.getId());
						if( vDeviceEnvList.size() > 0 ) {
							sendRestBodyData(request, String.format(DEVICE_RELATION_INSERT_URL, d.getId()), HttpMethod.POST, gsmKey, vDeviceEnvList);
						}
					});
				}
			});
		}
		if( houseList != null ) {
			for (int i = 0; i < houseList.size(); i++) {
				Map<String, Object> houseInfo = houseList.get(i);
				log.info("House Info : {}", houseInfo);
				//하우스 목록 내리기
				Integer houseId = ClassUtil.castToSomething(houseInfo.get("id"), Integer.class);
				sendRestBodyData(request, String.format(HOUSE_INSERT_URL, gsmKey), HttpMethod.POST, gsmKey, houseInfo);
				sendRestBodyData(request, String.format(HOUSE_UPDATE_URL, gsmKey), HttpMethod.PUT, gsmKey, houseInfo);
				//하우스-Device 링크 내리기
				Map<String,Object> param = new HashMap<>();
				param.put("houseId", houseInfo.get("id"));
				param.put("deviceId" , houseInfo.get("selectedDeviceList"));
				sendRestBodyData(request, String.format(HOUSE_DEVICE_LINK_DELETE_URL, gsmKey, houseId), HttpMethod.DELETE, gsmKey, null);
				sendRestBodyData(request, String.format(HOUSE_DEVICE_LINK_INSERT_URL, gsmKey, houseId), HttpMethod.POST, gsmKey, param);

				//제어로직 리스트 내리기
				final List<ControlLogicSettingVO> logicSetting = controlLogicSettingService.getLogicSetting(gsmKey, houseId, null);
				logicSetting.forEach( cs -> {
					sendRestBodyData(request, String.format(HOUSE_CONTROL_LOGIC_SETTING_INSERT_URL), HttpMethod.POST, gsmKey, cs);
					sendRestBodyData(request, String.format(HOUSE_CONTROL_LOGIC_SETTING_UPDATE_URL, cs.getControlSettingId()), HttpMethod.PUT, gsmKey, cs);
				});
			}
		}
		return HttpStatus.OK.value();
    }

	private ObjectMapper objMapper =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	public HttpEntity getHttpEntity(HttpServletRequest request, Integer gsmKey, Object data) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.set(headerName, request.getHeader(headerName));
		}
		if( headers.get(SmartFarmDataInterceptor.X_HEADER_GSM_KEY) == null || headers.get(SmartFarmDataInterceptor.X_HEADER_GSM_KEY).equals("") ) {
			headers.set(SmartFarmDataInterceptor.X_HEADER_GSM_KEY, String.valueOf(gsmKey));
		}
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		String strData = "";
		if( data != null ) {
			strData = objMapper.writeValueAsString(data);
		}
		return new HttpEntity<String>(strData, headers);
	}
	public HttpEntity sendRestBodyData(HttpServletRequest request, String server, Integer port, String url, HttpMethod method, Integer gsmKey, Object data) {
		try {
		    URI uri = new URI("http", null, server, port, null, null, null);
			uri = UriComponentsBuilder.fromUri(uri).path(url).build(true).toUri();
			HttpEntity httpEntity = getHttpEntity(request, gsmKey, data);
			if (httpEntity == null) {
				return null;
			}
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

			log.debug("sendPost : {} , {}, {}", gsmKey, uri, request.getMethod());
			final ResponseEntity<ResponseResult> returnValue = restTemplate.exchange(uri, method, httpEntity, ResponseResult.class);
			return returnValue;
		} catch(Exception e ) {
			log.warn("{} - {} , {}, {} :{}",url, method, gsmKey, data,  e);
		}
		return null;
	}
    public HttpEntity sendRestBodyData(HttpServletRequest request, String url, HttpMethod method, Integer gsmKey, Object data)  {
			final GsmEnvVO gsmEnvVO = this.gsmEnvMapper.get(gsmKey);
			if (gsmEnvVO == null) {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, NOT_FOUND_GSM_INFO);
			}
			String server = gsmEnvVO.getSystemHost();
			Integer port = gsmEnvVO.getSystemPort();
			return sendRestBodyData(request, server, port, url, method, gsmKey, data);

	}
}
