package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface DeviceEnvService {	
	List<DeviceEnvVO> insert( List<DeviceEnvVO> device);
	List<DeviceEnvVO> update( List<DeviceEnvVO> vo);
	List<DeviceEnvVO> list(String gsmKey, Integer controllerId);
	DeviceEnvVO get(String gsmKey, Integer controllerId);
	Integer delete(String gsmKey, Integer controllerId);
	
	List<DeviceTypeVO> getDeviceList(String houseType, String kind);
	HashMap<String,Object >gethouseTypeKindInfo();
	List<HashMap<String,Object>> getDeviceTypeList();
	
	
}
