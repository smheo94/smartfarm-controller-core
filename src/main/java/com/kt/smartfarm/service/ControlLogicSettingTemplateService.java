package com.kt.smartfarm.service;

import java.util.List;

public interface ControlLogicSettingTemplateService {
	ControlLogicSettingTemplateVO getLogicSettingTemplate(Long controlLogicSettingTemplateId);
	Integer delLogicSettingTemplate(Long controlSettingTemplateId);

	List<ControlLogicSettingTemplateVO> getLogicSettingTemplate(List<ControlLogicSettingTemplateVO.RelativeLevel> relativeLevel,
																List<ControlLogicSettingTemplateVO.PublishLevel> publishLevel,
																String userId, Long houseId,
																List<String> prdCode, String growStage, List<Long> logicId);
	ControlLogicSettingTemplateVO insertLogicSettingTemplate(ControlLogicSettingTemplateVO vo);
	ControlLogicSettingTemplateVO updateLogicSettingTemplate(ControlLogicSettingTemplateVO vo);


//	void updateLogicSettingTemplateEnv(Map<String, Object> param);
}
