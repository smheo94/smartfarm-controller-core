package egovframework.customize.service;

import java.util.List;
import java.util.Map;

public interface ControlLogicSettingService {
	List<ControlLogicSettingVO> getLogicSetting(Integer gsmKey, Integer houseId, Integer controlSettingId);

//	ControlLogicSettingHistoryVO getControlLogicSettingHIstoryDetail(Integer gsmKey, Integer houseId, Integer controlSettingId, Long logDt);

	List<ControlLogicSettingHistoryVO> getControlLogicSettingHIstoryList(Integer gsmKey, Integer houseId, Integer controlSettingId, Long fromData, Long toDate);

	ControlLogicSettingVO insertLogicSetting(ControlLogicSettingVO vo);

	Integer delLogicSetting(Integer controlSettingId);

	ControlLogicSettingVO updateLogicSetting(ControlLogicSettingVO vo);

	void updateLogicSettingEnv(Map<String, Object> param);

	List<ControlSettingOperatorVO> listControlSettingOperation();

	Integer delChkConditionSetting(Integer chkConditionId);

	Integer deleteControlLogicSettingDevice(Integer id);

	void updateLogicEnv(Map<String, Object> param);

	Integer copyToNewGSM(Integer fromGsmKey, Integer toGsmKey);
}
