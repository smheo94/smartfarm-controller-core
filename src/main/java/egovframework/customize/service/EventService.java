package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface EventService {	
	ControllerEnvVO insert( ControllerEnvVO vo);
	Integer delete(String gsmKey, Integer controllerId);
	ControllerEnvVO update(ControllerEnvVO vo);
	ControllerEnvVO get(String gsmKey, Integer controllerId);
	List<ControllerEnvVO> list(String gsmKey);
	List<HashMap<String,Object>> listType();
	List<ControllerEnvVO> controllerDeviceList(String gsmKey);
}
