package com.kt.smartfarm.service;

import java.util.HashMap;
import java.util.List;

public interface EventService {

	ControllerEnvVO insert( ControllerEnvVO vo);
	Integer delete(Long gsmKey, Long controllerId);
	ControllerEnvVO update(ControllerEnvVO vo);
	ControllerEnvVO get(Long gsmKey, Long controllerId);
	List<ControllerEnvVO> list(Long gsmKey);
	List<HashMap<String,Object>> listType();
	List<ControllerEnvVO> controllerDeviceList(Long gsmKey);
}
