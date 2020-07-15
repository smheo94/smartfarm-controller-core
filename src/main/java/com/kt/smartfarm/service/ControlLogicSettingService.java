package com.kt.smartfarm.service;

import java.util.List;
import java.util.Map;

public interface ControlLogicSettingService {
	List<ControlLogicSettingVO> getLogicSetting(Long gsmKey, Long houseId, Long controlSettingId);
	List<ControlLogicSettingVO> getLogicSetting(Long gsmKey, Long houseId, Long controlSettingId, List<Long> logicId);
	List<ControlLogicSettingHistoryVO> getControlLogicSettingHistoryList(Long gsmKey, Long houseId, Long controlSettingId, Long fromData, Long toDate);

	ControlLogicSettingVO insertLogicSetting(ControlLogicSettingVO vo);

	Integer delLogicSetting(Long controlSettingId);

	ControlLogicSettingVO updateLogicSetting(ControlLogicSettingVO vo);

	void updateLogicSettingEnv(Map<String, Object> param);

	List<ControlSettingOperatorVO> listControlSettingOperation();

	Integer delChkConditionSetting(Long chkConditionId);

	Integer deleteControlLogicSettingDevice(Long deviceId);

	void updateLogicEnv(Map<String, Object> param);

	Integer copyToNewGSM(Long fromGsmKey, Long toGsmKey);


	List<LiquidVO> getCdLiquid(String liquidId);
	List<ControlSettingLiquidVO> getControlSettingLiquid(Integer ownerUserIdx);
	int insertControlSettingLiquid(ControlSettingLiquidVO param);
	int updateControlSettingLiquid(ControlSettingLiquidVO param);
	int deleteControlSettingLiquid(Long id, Integer ownerUserIdx);
	void updateLogicSettingDeviceList(Long gsmkey, Long controlSettingId,List<ControlLogicSettingDeviceVO> voList);
}
