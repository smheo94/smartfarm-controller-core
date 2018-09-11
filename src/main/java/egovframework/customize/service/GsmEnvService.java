package egovframework.customize.service;

import java.util.List;

public interface GsmEnvService {

	List<GsmEnvVO> list();

	GsmEnvVO get(String gsmKey);

	Integer delete(String gsmKey);

	Integer insert(GsmEnvVO gsmInfo);

	Integer update(GsmEnvVO gsmInfo);

	
	
	
}
