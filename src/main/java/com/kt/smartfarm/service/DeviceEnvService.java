package com.kt.smartfarm.service;

import java.util.HashMap;
import java.util.List;

public interface DeviceEnvService {	
	List<DeviceEnvVO> insert( List<DeviceEnvVO> device);
	List<DeviceEnvVO> update( List<DeviceEnvVO> vo);
	List<DeviceEnvVO> list(Integer gsmKey, Integer controllerId, Boolean withVDeviceList, Boolean withEDeviceList);
	DeviceEnvVO getDevice(Integer deviceId, Boolean withVDeviceList);

	//DeviceEnvVO getDevice(Integer gsmKey, Integer controllerId);


	Integer deleteControllerDeivces(Integer gsmKey, Integer controllerId);
	
	List<DeviceTypeVO> getDeviceTypeByHouseType(String houseType, String kind);
	HashMap<String,Object > getHouseTypeKindInfo();
	List<HashMap<String,Object>> getDeviceTypeList();
	
	List<VDeviceInfoVO> getVDeviceList();
	List<VDeviceEnvVO> insertVDeviceEnv(List<VDeviceEnvVO> vo);


	List<VDeviceEnvVO> getVDeviceEnvList(Integer deviceId);
	
	VDeviceEnvVO updateVDeviceEnv(VDeviceEnvVO vo);
	Integer deleteVDeviceEnv(Integer id, Integer pDeviceId,  Integer deviceNum , Integer deviceInsertOrder);

	Integer copyToNewGSM(Integer fromGsmKey, Integer toGsmKey);

	Integer deleteDevice(Integer deviceId);



	List<EDeviceEnvVO> insertEDeviceEnv(List<EDeviceEnvVO> vo);

	List<EDeviceEnvVO> getEDeviceEnvList(Integer deviceId);

	EDeviceEnvVO updateEDeviceEnv(EDeviceEnvVO vo);
	Integer deleteEDeviceEnv(Integer deviceId);
	Integer deleteEDevicesEnv(Integer pDeviceId);


}
