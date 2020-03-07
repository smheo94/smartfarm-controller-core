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

import com.kt.smartfarm.service.ControlLogicSettingTemplateService;
import com.kt.smartfarm.service.ControlLogicSettingTemplateVO;
import com.kt.smartfarm.service.HouseEnvVO;
import com.kt.smartfarm.supervisor.mapper.ControlLogicSettingTemplateMapper;
import com.kt.smartfarm.supervisor.mapper.HouseEnvMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("controlLogicSettingTemplateService")
public class ControlLogicSettingTemplateServiceImpl extends EgovAbstractServiceImpl implements ControlLogicSettingTemplateService {

	@Resource(name = "controlLogicSettingTemplateMapper")
	ControlLogicSettingTemplateMapper mapper;

	@Resource(name = "houseEnvMapper")
	HouseEnvMapper houseEnvMapper;

	@Override
	public List<ControlLogicSettingTemplateVO> getLogicSettingTemplate(List<ControlLogicSettingTemplateVO.RelativeLevel> relativeLevel,
																	   List<ControlLogicSettingTemplateVO.PublishLevel> publishLevel,
																	   String userId, Long houseId,  List<String> prdCode, String growStage, List<Long> logicId) {
		return mapper.listControlLogicSettingTemplate(relativeLevel, publishLevel, userId, houseId, prdCode, growStage, logicId);
	}

	@Override
	public ControlLogicSettingTemplateVO getLogicSettingTemplate(Long controlLogicSettingTemplateId) {
		return mapper.getControlLogicSettingTemplate(controlLogicSettingTemplateId);
	}

	@Override
	public ControlLogicSettingTemplateVO insertLogicSettingTemplate(ControlLogicSettingTemplateVO vo) {
		Long tempControlSettingId = null;
		if(vo.getControlSettingTemplateId() !=null){
			tempControlSettingId = vo.getControlSettingTemplateId();
		}
		mapper.insertControlSettingTemplate(vo);
		if(vo.getControlSettingTemplateId() == 0){
			vo.setControlSettingTemplateId(tempControlSettingId);
		}
		return vo;
	}

	@Override
	public Integer delLogicSettingTemplate(Long controlSettingTemplateId) {
		ControlLogicSettingTemplateVO logicSettingTemplateVO = mapper.getControlLogicSettingTemplate(controlSettingTemplateId);
		if (logicSettingTemplateVO == null) {
			return 0;
		}
		mapper.deleteControlSettingTemplate(controlSettingTemplateId);
		return 1;
	}

	@Override
	public ControlLogicSettingTemplateVO updateLogicSettingTemplate(ControlLogicSettingTemplateVO vo) {
		if (vo.getControlSettingTemplateId() == null) {
			return null;
		}
		mapper.updateControlSettingTemplate(vo);
		return vo;
	}

//	@Override
//	public void updateLogicSettingTemplateEnv(Map<String, Object> param) {
//		mapper.updateControlSettingTemplateEnv(param);
//	}
//

}
