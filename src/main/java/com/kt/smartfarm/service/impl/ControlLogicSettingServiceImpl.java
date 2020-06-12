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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.kt.cmmn.util.ClassUtil;
import com.kt.cmmn.util.GSMUtil;
import com.kt.cmmn.util.MapUtils;
import com.kt.cmmn.util.RestClientUtil;
import com.kt.smartfarm.config.SmartfarmInterceptorConfig;
import com.kt.smartfarm.service.*;
import com.kt.smartfarm.mapper.ControlLogicSettingMapper;
import com.kt.smartfarm.mapper.HouseEnvMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.*;

@Slf4j
@Service("controlLogicSettingService")
public class ControlLogicSettingServiceImpl extends EgovAbstractServiceImpl implements ControlLogicSettingService {

	@Autowired
	ApplicationEventPublisher publisher;
	@Autowired
	SmartfarmInterceptorConfig config;

	@Resource(name = "controlLogicSettingMapper")
	ControlLogicSettingMapper mapper;

	@Resource(name = "houseEnvMapper")
	HouseEnvMapper houseEnvMapper;

	private static final ObjectMapper objMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@Override
	public List<ControlLogicSettingVO> getLogicSetting(Long gsmKey, Long houseId, Long controlSettingId) {
		return mapper.getControlLogicSetting(gsmKey, houseId, controlSettingId, null);
	}
	@Override
	public List<ControlLogicSettingVO> getLogicSetting(Long gsmKey, Long houseId, Long controlSettingId, List<Long> logicId) {
		return mapper.getControlLogicSetting(gsmKey, houseId, controlSettingId, logicId);
	}

	@Override
	public List<ControlLogicSettingHistoryVO> getControlLogicSettingHistoryList(Long gsmKey, Long houseId, Long controlSettingId, Long fromData, Long toDate)
	{
		return  mapper.getControlLogicSettingHistoryList(gsmKey, houseId, controlSettingId, fromData, toDate);
	}


	@Override
	public ControlLogicSettingVO insertLogicSetting(ControlLogicSettingVO vo) {
	    if( vo != null ) {
            vo.logicPeriodEnv = periodSort(vo.logicPeriodEnv);
        }
		Long tempControlSettingId = null;
		if(vo.getControlSettingId() !=null){
			tempControlSettingId = vo.getControlSettingId();
		}
		final HouseEnvVO houseEnvVO = houseEnvMapper.get(null, vo.greenHouseId);
		vo.setTmpGsmKey(houseEnvVO.getGsmKey());
		mapper.insertControlSetting(vo);
		if(vo.getControlSettingId() == 0){
			vo.setControlSettingId(tempControlSettingId);	
		}
		
		if (vo.getControlSettingId() == null) {
			return vo;
		}
		if (vo.getPreOrderSettingId() != null) {
			mapper.insertControlSettingPreOrder(vo);
		}
		if (vo.getCheckConditionList() != null) {
			vo.getCheckConditionList().forEach(checkList -> {
				checkList.setControlSettingId(vo.controlSettingId);
				checkList.setTmpGsmKey(vo.tmpGsmKey);
				mapper.insertControlSettingChkCondition(checkList);
				if (checkList.getId() != null) {
					mapper.insertControlSettingChkConditionDevice(checkList);
				}
			});
		}
		if (vo.getDeviceList() != null) {
			for(int i=0; i<vo.getDeviceList().size();i++){
				ControlLogicSettingDeviceVO device = vo.getDeviceList().get(i);
				device.setControlSettingId(vo.getControlSettingId());
				device.setTmpGsmKey(vo.tmpGsmKey);
				mapper.insertControlSettingDevice(device);
			}
		}
		return vo;
	}

    private String periodSort(String logicPeriodEnv) {
        try {
            if( logicPeriodEnv == null ) {
                return logicPeriodEnv;
            }
            JsonNode periodEnv = objMapper.readTree(logicPeriodEnv);
            if( !periodEnv.isArray() ) {
                return logicPeriodEnv;
            }
            SortedMap<Calendar, JsonNode> sortedNodes = new TreeMap<>();
            List<JsonNode> unsortedNodes = new ArrayList<>();
            for(JsonNode env : periodEnv) {
                if( env.isObject() ) {
                    JsonNode conditionNode = env.get("condition");
                    JsonNode timeNode = env.get("on_start_time");

                    if( timeNode == null || timeNode.isNull()) {
                        timeNode=  env.get("relative_start_time");
                    }
                    if( timeNode == null || timeNode.isNull() ) {
                        timeNode=  env.get("relative_time");
                    }
                    if( timeNode == null || conditionNode == null || timeNode.isNull()) {
                        unsortedNodes.add(env);
						continue;
                    }
                    Time relativeTime = ClassUtil.castToSomething(timeNode.asText(), Time.class);
                    Integer condition = ClassUtil.castToSomething(conditionNode.asText(), Integer.class);
                    Calendar convertCal = GSMUtil.getRealTime(relativeTime, condition);
                    if( convertCal == null ) {
						unsortedNodes.add(env);
						continue;
					}
                    sortedNodes.put(convertCal, env);
                }
            }
            List<JsonNode> sortedNodeList = new ArrayList<>(sortedNodes.values());
            ((ArrayNode)periodEnv).removeAll();
            ((ArrayNode)periodEnv).addAll(sortedNodeList);
            ((ArrayNode)periodEnv).addAll(unsortedNodes);
            return objMapper.writeValueAsString(periodEnv);
        } catch (Exception e) {
           log.warn("Sorted Fail : {}", logicPeriodEnv);
           return logicPeriodEnv;
        }
    }

    @Override
	public Integer delLogicSetting(Long controlSettingId) {
		List<ControlLogicSettingVO> logicSettingVOList = mapper.getControlLogicSetting(null, null, controlSettingId, null);
		if (logicSettingVOList == null || logicSettingVOList.size() == 0 || logicSettingVOList.get(0) == null) {
			return 0;
		}
		final ControlLogicSettingVO logicSettingVO = logicSettingVOList.get(0);
		if (logicSettingVO.getDeviceList() != null) {
			mapper.deleteControlSettingDevice(null, logicSettingVO.controlSettingId);
		}
		if (logicSettingVO.getCheckConditionList() != null) {
			mapper.deleteControlSettingChkCondition(null, logicSettingVO.controlSettingId);
		}
		return mapper.deleteControlSetting(logicSettingVO.controlSettingId);

		// if( logicSettingVO.getPreOrderSettingId() != null ) {
		//
		// }
		// return null;
	}

	@Override
	public ControlLogicSettingVO updateLogicSetting(ControlLogicSettingVO vo) {
        if( vo != null ) {
            vo.logicPeriodEnv = periodSort(vo.logicPeriodEnv);
        }
		if (vo.getControlSettingId() == null) {
			log.info("setting ID is null {}", vo);
			return vo;
		}
		final HouseEnvVO houseEnvVO = houseEnvMapper.get(null, vo.greenHouseId);
		vo.setTmpGsmKey(houseEnvVO.getGsmKey());
		List<ControlLogicSettingVO> dbSettingList = mapper.getControlLogicSetting(null, null, vo.controlSettingId, null);
		mapper.updateControlSetting(vo);
		if (vo.getPreOrderSettingId() != null) {
			log.info("exists getPreOrderSettingId {}", vo.controlSettingId);
			mapper.deleteControlSettingPreOrder(null, vo.controlSettingId);
			mapper.insertControlSettingPreOrder(vo);
		}
		if (vo.getCheckConditionList() != null) {
			log.info("exists getCheckConditionList {}", vo.getCheckConditionList().size());
			vo.getCheckConditionList().forEach(checkList -> {
				checkList.setControlSettingId(vo.controlSettingId);
				checkList.setTmpGsmKey(vo.tmpGsmKey);
				int isExist = mapper.updateControlSettingChkCondition(checkList);
				if (isExist == 0) {
					mapper.insertControlSettingChkCondition(checkList);
				} else {
					log.info("update updateControlSettingChkCondition {}", checkList);
				}

				if (checkList.getId() != null) {
					log.info("insertControlSettingChkConditionDevice {}", checkList.getId());
					mapper.deleteControlSettingChkConditionDevice(checkList.getId());
					mapper.insertControlSettingChkConditionDevice(checkList);
				}
			});
		}
		if (vo.getDeviceList() != null) {
			vo.getDeviceList().size();
			for (int i = 0, size = vo.getDeviceList().size(); i < size; i++) {
				ControlLogicSettingDeviceVO device = vo.getDeviceList().get(i);
				device.setControlSettingId(vo.getControlSettingId());
				device.setTmpGsmKey(vo.tmpGsmKey);
				Long id = device.getId();
				int isExist = mapper.updateControlSettingDevice(device);
				if (isExist == 0) {
					mapper.insertControlSettingDevice(device);
				}
			}
		}

		if( config.isSmartfarmSystem() ) {
			if (dbSettingList != null && dbSettingList.size() > 0 && dbSettingList.get(0) != null) {
				ControlLogicSettingVO dbSetting = dbSettingList.get(0);
				if (!Objects.equals(dbSetting.autoManualMode, vo.autoManualMode)) {
					publisher.publishEvent(vo);
				}
			}
		}
		return vo;
	}

	@Async
	@EventListener
	public void sendControlModeChange(ControlLogicSettingVO vo) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("controlSettingId", vo.controlSettingId);
		param.put("logicId", vo.logicId);
		param.put("command", "manual_mode_change");
		try{
			log.info("manual command");
			RestTemplate client = new RestTemplate();
			RestClientUtil.setIgnoreCertificateSSL(client);
			String url = "http://127.0.0.1:9876/api/v1/device/operation_command";
			HashMap<String, Object> responseData = (HashMap<String, Object>) client.postForObject(url, param, HashMap.class);
			String result = MapUtils.toJson(responseData);
			log.info("command ========== " + result);
		}catch(Exception e){
			log.error("command : {}", param,  e);
		}
	}

	@Override
	public void updateLogicSettingEnv(Map<String, Object> param) {
		mapper.updateControlSettingEnv(param);
	}

	@Override
	public void updateLogicEnv(Map<String, Object> param) {
		if( param.containsKey("logic_period_env")) {
			String logicPeriodEnv = (String) param.get("logic_period_env");
			param.put("logic_period_env", periodSort(logicPeriodEnv));
		}
		mapper.updateLogicEnv(param);
	}
	
	@Override
	public List<ControlSettingOperatorVO> listControlSettingOperation() {
		return mapper.listControlSettingOperator();
	}

	@Override
	public Integer delChkConditionSetting(Long chkConditionId) {
		return mapper.delChkConditionSetting(chkConditionId);
	}

	@Override
	public Integer deleteControlLogicSettingDevice(Long id) {
		return mapper.deleteControlSettingDevice(id, null);
	}


	@Override
	public List<LiquidVO> getCdLiquid(String liquidId) {
		return mapper.getCdLiquid(liquidId);
	}
	@Override
	public List<ControlSettingLiquidVO> getControlSettingLiquid(Integer ownerUserIdx) {
		return mapper.getControlSettingLiquid(ownerUserIdx);
	}
	@Override
	public int insertControlSettingLiquid(ControlSettingLiquidVO param) {
		return mapper.insertControlSettingLiquid(param);
	}
	@Override
	public int updateControlSettingLiquid(ControlSettingLiquidVO param) {
		return mapper.updateControlSettingLiquid(param);
	}
	@Override
	public int deleteControlSettingLiquid(Long id, Integer ownerUserIdx) {
		return mapper.deleteControlSettingLiquid(id, ownerUserIdx);
	}

	@Override
	public Integer copyToNewGSM(Long fromGsmKey, Long toGsmKey) {
		Integer result = mapper.copyToNewGSMContolSetting(fromGsmKey,toGsmKey);
		mapper.copyToNewGSMContolSettingCheckCondition(fromGsmKey,toGsmKey);
		mapper.copyToNewGSMContolSettingCheckConditionDevice(fromGsmKey,toGsmKey);
		mapper.copyToNewGSMControlSettingDevice(fromGsmKey,toGsmKey);
		mapper.copyToNewGSMControlPreOrder(fromGsmKey,toGsmKey);
		return result;
	}
}
