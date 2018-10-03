package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface ControlLogicEnvService {
	List<ControlLogicVO> getAllLogicList();
//	HashMap<String,Object> allList();
	List<ControlLogicVO> getLogicList();
	List<ControlLogicDeviceVO> getLogicDeviceList(Long logicId);
	List<HashMap<String, Object>> getLogicPropertyList(Long logicId);

}
