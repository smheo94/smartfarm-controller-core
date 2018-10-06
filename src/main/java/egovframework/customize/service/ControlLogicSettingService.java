package egovframework.customize.service;

import java.util.List;


public interface ControlLogicSettingService {
	List<ControlLogicSettingVO> getLogicSetting(Integer gsmKey, Integer houseId, Integer logicSettingId);
	ControlLogicSettingVO insertLogicSetting(ControlLogicSettingVO vo);
	Integer delLogicSetting(Integer logicSettingId);

	ControlLogicSettingVO updateLogicSetting(ControlLogicSettingVO vo);
}
