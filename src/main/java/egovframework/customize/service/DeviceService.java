package egovframework.customize.service;

import java.util.HashMap;

public interface DeviceService {
	
	HashMap<String, Object> getSensorInfoList() throws Exception;
	HashMap<String, Object> getDeviceInfoList() throws Exception;
	HashMap<String, Object> insertDeviceList() throws Exception;
	HashMap<String, Object> insertSensorList() throws Exception;
}
