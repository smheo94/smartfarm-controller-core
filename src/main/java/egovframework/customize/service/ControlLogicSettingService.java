package egovframework.customize.service;

import java.util.List;
import java.util.Map;

public interface ControlLogicSettingService {
	List<ControlLogicSettingVO> getLogicSetting(Integer gsmKey, Integer houseId, Integer controlSettingId);

	ControlLogicSettingVO insertLogicSetting(ControlLogicSettingVO vo);

	Integer delLogicSetting(Integer controlSettingId);

	ControlLogicSettingVO updateLogicSetting(ControlLogicSettingVO vo);

	void updateLogicSettingEnv(Map<String, Object> param);

	List<ControlSettingOperatorVO> listControlSettingOperation();

	Integer delChkConditionSetting(Integer chkConditionId);

	Integer deleteControlLogicSettingDevice(Integer id);
}
