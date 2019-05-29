package com.kt.smartfarm.service;

import java.util.HashMap;
import java.util.List;

public interface ControllerEnvService {	
	ControllerEnvVO insert( ControllerEnvVO vo);
	Integer delete(Integer gsmKey, Integer controllerId);
	ControllerEnvVO update(ControllerEnvVO vo);
	ControllerEnvVO get(Integer gsmKey, Integer controllerId);
	List<ControllerEnvVO> list(Integer gsmKey);
	List<HashMap<String,Object>> listType();
	List<ControllerEnvVO> controllerDeviceList(Integer gsmKey);

	Integer copyToNewGSM(Integer fromGsmKey, Integer toGsmKey);
}
