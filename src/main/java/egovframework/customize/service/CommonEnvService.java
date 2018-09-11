package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface CommonEnvService {
	CommonEnvVO getOutWeather(int gsmKey) throws Exception;
	CommonEnvVO getOutWeatherDefault(int gsmKey) throws Exception;
	void updateOutWeather(CommonEnvVO vo) throws Exception;	
	void updateOutWeatherDefault(CommonEnvVO vo) throws Exception;
	
	CommonEnvVO getThreshold(int gsmKey) throws Exception;
	CommonEnvVO getThresholdDefault(int gsmKey) throws Exception;
	void updateThreshold(CommonEnvVO vo) throws Exception;	
	void updateThresholdDefault(CommonEnvVO vo) throws Exception;
	
	void updateEnvInfo(CommonEnvVO vo) throws Exception;
	CommonEnvVO getOutweatherInfo(HashMap<String, Object> param);
	
//	HashMap<String, Object> getSensorInfoList() throws Exception;
//	HashMap<String, Object> getDeviceInfoList() throws Exception;
	HashMap<String, Object> insertDeviceList(HashMap<String, Object> actuatorModuleInfo, String contextPath) throws Exception;	
	HashMap<String, Object> updateDeviceList(HashMap<String, Object> actuatorModuleInfo, String contextPath) throws Exception;
	HashMap<String, Object> deleteControllerInfo(String controllerId, String contextPath);
	
	HashMap<String, Object> upsertSensorList(HashMap<String, Object> sensorModuleInfo, String contextPath, boolean isNew) throws Exception;
	HashMap<String, Object> deleteSensorList(String sensorControllerId, String contextPath);
	List<HashMap<String, Object>> selectedControllerInfo(String selectedControllerId, Integer controllerType);
	HashMap<String, Object> selectSensorList(Integer sensorType);
	
	List<HashMap<String, Object>> getControllerListOfControlModule(int[] controllerInfoId);
	List<HashMap<String, Object>> selectDeviceOfControlModule(Integer controllerId);
	
	
}
