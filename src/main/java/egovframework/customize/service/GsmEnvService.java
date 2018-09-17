package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface GsmEnvService {

	List<HashMap<String,Object>> list();

	GsmEnvVO get(String gsmKey);

	Integer delete(String gsmKey);

	Integer insert(GsmEnvVO gsmInfo);

	Integer update(GsmEnvVO gsmInfo);

	
	
	
}
