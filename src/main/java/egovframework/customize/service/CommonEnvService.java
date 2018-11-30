package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface CommonEnvService {
	CommonEnvVO getOutWeather(int gsmKey);
	CommonEnvVO getOutWeatherDefault(int gsmKey);
	void updateOutWeather(CommonEnvVO vo);	
	void updateOutWeatherDefault(CommonEnvVO vo);
	
	CommonEnvVO getThreshold(int gsmKey);
	CommonEnvVO getThresholdDefault(int gsmKey);
	void updateThreshold(CommonEnvVO vo);	
	void updateThresholdDefault(CommonEnvVO vo);
	
	void updateEnvInfo(CommonEnvVO vo);
	CommonEnvVO getOutweatherInfo(HashMap<String, Object> param);
	
//	HashMap<String, Object> getSensorInfoList();
//	HashMap<String, Object> getDeviceInfoList();
	HashMap<String, Object> insertDeviceList(HashMap<String, Object> actuatorModuleInfo, String contextPath);	
	HashMap<String, Object> updateDeviceList(HashMap<String, Object> actuatorModuleInfo, String contextPath);
	HashMap<String, Object> deleteControllerInfo(String controllerId, String contextPath);
	
	HashMap<String, Object> upsertSensorList(HashMap<String, Object> sensorModuleInfo, String contextPath, boolean isNew);
	HashMap<String, Object> deleteSensorList(String sensorControllerId, String contextPath);
	List<HashMap<String, Object>> selectedControllerInfo(String selectedControllerId, Integer controllerType);
	HashMap<String, Object> selectSensorList(Integer sensorType);
	
	List<HashMap<String, Object>> getControllerListOfControlModule(int[] controllerInfoId);
	List<HashMap<String, Object>> selectDeviceOfControlModule(Integer controllerId);
	
	
}
