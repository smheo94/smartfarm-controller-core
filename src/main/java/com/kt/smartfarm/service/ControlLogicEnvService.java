package com.kt.smartfarm.service;

import java.util.HashMap;
import java.util.List;


public interface ControlLogicEnvService {
	List<ControlLogicVO> getAllLogicList();

	List<ControlLogicV2VO> getAllLogicListV2();

	//	HashMap<String,Object> allList();
	List<ControlLogicVO> getLogicList();
	List<ControlLogicDeviceVO> getLogicDeviceList(Integer logicId);
	List<HashMap<String, Object>> getLogicPropertyList(Integer logicId);
	List<ControlLogicPropertiesVO> getLogicPropertyListV2(Integer logicId);

}
