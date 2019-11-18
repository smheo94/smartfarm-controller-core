/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */                                                               
package com.kt.smartfarm.supervisor.mapper;

import com.kt.smartfarm.service.*;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper("controlLogicSettingTemplateMapper")
public interface ControlLogicSettingTemplateMapper {
	ControlLogicSettingTemplateVO getControlLogicSettingTemplate(@Param("id") Long controlLogicSettingTemplateId);
	Integer deleteControlSettingTemplate(@Param("id") Long controlLogicSettingTemplateId);

	List<ControlLogicSettingTemplateVO> listControlLogicSettingTemplate(
			@Param("relative_level") List<ControlLogicSettingTemplateVO.RelativeLevel> relativeLevel,
			@Param("publish_level") List<ControlLogicSettingTemplateVO.PublishLevel> publishLevel,
			@Param("user_id") String userId,
			@Param("green_house_id") Integer houseId,
			@Param("prd_code") List<String> prdCode,
			@Param("grow_stage") String growStage,
			@Param("logic_id") List<Long> logicId);

	Integer insertControlSettingTemplate(ControlLogicSettingTemplateVO vo);
	Integer updateControlSettingTemplate(ControlLogicSettingTemplateVO vo);
	//Integer updateControlSettingTemplateEnv(Map<String, Object> param);



//	Integer copyToNewGSMContolSetting(@Param("from_gsm_key") Integer fromGsmKey, @Param("to_gsm_key") Integer toGsmKey);
//	Integer copyToNewGSMContolSettingCheckCondition(@Param("from_gsm_key") Integer fromGsmKey, @Param("to_gsm_key") Integer toGsmKey);
//	Integer copyToNewGSMContolSettingCheckConditionDevice(@Param("from_gsm_key") Integer fromGsmKey, @Param("to_gsm_key") Integer toGsmKey);
//	Integer copyToNewGSMControlSettingDevice(@Param("from_gsm_key") Integer fromGsmKey, @Param("to_gsm_key") Integer toGsmKey);
//	Integer copyToNewGSMControlPreOrder(@Param("from_gsm_key") Integer fromGsmKey, @Param("to_gsm_key") Integer toGsmKey);
	void updateLogicTemplateEnv(Map<String, Object> param);


}
