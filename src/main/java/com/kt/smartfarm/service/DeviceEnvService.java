package com.kt.smartfarm.service;

import org.springframework.web.client.HttpStatusCodeException;

import java.util.HashMap;
import java.util.List;

public interface DeviceEnvService {	
	List<DeviceEnvVO> insert( List<DeviceEnvVO> device);
	List<DeviceEnvVO> update( List<DeviceEnvVO> vo);
	List<DeviceEnvVO> list(Long gsmKey, Long controllerId, Boolean withVDeviceList, Boolean withEDeviceList);
	DeviceEnvVO getDevice(Long deviceId, Boolean withVDeviceList);

	//DeviceEnvVO getDevice(Long gsmKey, Long controllerId);


	Integer deleteControllerDeivces(Long gsmKey, Long controllerId);
	
	List<DeviceTypeVO> getDeviceTypeByHouseType(String houseType, String kind);
	HashMap<String,Object > getHouseTypeKindInfo();
	List<HashMap<String,Object>> getDeviceTypeList();
	
	List<VDeviceInfoVO> getVDeviceList();
	List<VDeviceEnvVO> insertVDeviceEnv(List<VDeviceEnvVO> vo)  throws HttpStatusCodeException;;


	List<VDeviceEnvVO> getVDeviceEnvList(Long deviceId);
	
	VDeviceEnvVO updateVDeviceEnv(VDeviceEnvVO vo) throws HttpStatusCodeException;
	Integer deleteVDeviceEnv(Integer id, Long pDeviceId,  Integer deviceNum , Integer deviceInsertOrder);

	Integer copyToNewGSM(Long fromGsmKey, Long toGsmKey);

	Integer deleteDevice(Long deviceId);


	List<EDeviceEnvVO> insertEDeviceEnv(List<EDeviceEnvVO> vo);

	List<EDeviceEnvVO> getEDeviceEnvList(Long deviceId);

	EDeviceEnvVO updateEDeviceEnv(EDeviceEnvVO vo);
	Integer deleteEDeviceEnv(Integer id);
	Integer deleteEDevicesEnv(Long pDeviceId);


}
