package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GsmEnvService {

	List<Map<String,Object>> gsmOfDeviceList(Integer gsmKey);

	//GsmEnvVO get(Integer gsmKey);

	Map<String,Object> get(Integer gsmKey, boolean  all);

	Integer delete(Integer gsmKey);

	Integer insert(GsmEnvVO gsmInfo);

	Integer update(GsmEnvVO gsmInfo);

	List<Map<String, Object>> list(boolean all);

	
	
	
}
