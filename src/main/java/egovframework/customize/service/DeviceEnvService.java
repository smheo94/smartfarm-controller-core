package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface DeviceEnvService {	
	List<DeviceEnvVO> insert( List<DeviceEnvVO> device);
	List<DeviceEnvVO> update( List<DeviceEnvVO> vo);
	List<DeviceEnvVO> list(Integer gsmKey, Integer controllerId);
	DeviceEnvVO get(Integer gsmKey, Integer controllerId);
	Integer delete(Integer gsmKey, Integer controllerId);
	
	List<DeviceTypeVO> getDeviceList(String houseType, String kind);
	HashMap<String,Object >gethouseTypeKindInfo();
	List<HashMap<String,Object>> getDeviceTypeList();
	
	List<VDeviceInfoVO> getVDeviceList();
	List<VDeviceEnvVO> insertVDeviceEnv(List<VDeviceEnvVO> vo);


	List<VDeviceEnvVO> getVDeviceEnvList(Integer deviceId);
}
