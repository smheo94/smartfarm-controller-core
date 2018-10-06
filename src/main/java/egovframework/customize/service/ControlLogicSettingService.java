package egovframework.customize.service;

import java.util.List;


public interface ControlLogicSettingService {
	List<ControlLogicSettingVO> getLogicSetting(Integer gsmKey, Integer houseId, Integer controlSettingId);
	ControlLogicSettingVO insertLogicSetting(ControlLogicSettingVO vo);
	Integer delLogicSetting(Integer controlSettingId);

	ControlLogicSettingVO updateLogicSetting(ControlLogicSettingVO vo);
}
