package egovframework.customize.service;

import java.util.HashMap;

public interface DeviceService {
	
	HashMap<String, Object> getSensorInfoList();
	HashMap<String, Object> getDeviceInfoList();
	HashMap<String, Object> insertDeviceList();
	HashMap<String, Object> insertSensorList();
}
