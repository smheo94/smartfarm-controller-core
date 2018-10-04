package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;


public interface ControlLogicSettingService {
	List<ControlLogicSettingVO> getLogicSetting(Integer gsmKey, Integer houseId);
	ControlLogicSettingVO setLogicSetting(ControlLogicSettingVO vo);
	ControlLogicSettingVO delLogicSetting(Integer logicSettingId);
}
