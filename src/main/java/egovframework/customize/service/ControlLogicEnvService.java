package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface ControlLogicEnvService {

	Object insert(List<DeviceEnvVO> device);

	Object update(List<DeviceEnvVO> device);

	HashMap<String,Object> list();

	List<ControlLogicVO> getRegList(String gsmKey, String houseId);

	Object delete(String gsmKey, Integer controllerId);

	
}
