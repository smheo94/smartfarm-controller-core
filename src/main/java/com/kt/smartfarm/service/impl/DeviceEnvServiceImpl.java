/*
 * Copyright 2008-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kt.smartfarm.service.impl;

import com.kt.smartfarm.mapper.DeviceEnvMapper;
import com.kt.smartfarm.service.*;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("deviceEnvService")
public class DeviceEnvServiceImpl extends EgovAbstractServiceImpl implements DeviceEnvService {

	private static final Logger log = LoggerFactory.getLogger(DeviceEnvServiceImpl.class);
	@Resource(name = "deviceEnvMapper")
	DeviceEnvMapper deviceEnvMapper;

	@Override
	public List<DeviceEnvVO> insert(List<DeviceEnvVO> device) {
		try {
			for (DeviceEnvVO vo : device) {
				log.info("Insert Device : {}", vo);
				try {
					deviceEnvMapper.insert(vo);
				} catch (Exception se) {
					if (se instanceof SQLException) {
						if (((SQLException) se).getErrorCode() == 1062) {
							log.warn("중복 오류 발생 : {}", se.getMessage());
							continue;
						}
					}
				}
				if (vo.getRelationDeviceList() != null) {
					for (VDeviceEnvVO vDevice : vo.getRelationDeviceList()) {
						vDevice.setParentDeviceId(vo.getId());
						try {
							deviceEnvMapper.insertVDeviceEnv(vDevice);
						} catch (Exception se) {
							if (se instanceof SQLException) {
								if (((SQLException) se).getErrorCode() == 1062) {
									log.warn("중복 오류 발생 : {}", se.getMessage());
									continue;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return device;
	}

	@Override
	public Integer deleteControllerDeivces(Long gsmKey, Long controller_id) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key", gsmKey);
		map.put("controller_id", controller_id);
		return deviceEnvMapper.deleteControllerDeivces(map);
	}

	@Override
	public List<DeviceEnvVO> update(List<DeviceEnvVO> device) {
		for (DeviceEnvVO vo : device) {
			deviceEnvMapper.update(vo);
		}
		return device;
	}

//	@Override
//	public DeviceEnvVO getDevice(Long gsmKey, Long controllerId) {
//		Map<String, Object> map = new HashMap<>();
//		map.put("gsm_key",  gsmKey);
//		map.put("id",  controllerId);
//		return deviceEnvMapper.list(map).stream().findFirst().orElse(null);
//	}

	@Override
	public List<DeviceEnvVO> list(Long gsmKey, Long controllerId, Boolean withVDeviceList, Boolean withEDeviceList) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key", gsmKey);
		map.put("controller_id", controllerId);

		if (!withVDeviceList && withEDeviceList) {
			int eDeviceId = 11401;
			map.put("device_type_id", eDeviceId);
		}

		final List<DeviceEnvVO> deviceList = deviceEnvMapper.list(map);

		if (withVDeviceList) {
			for (DeviceEnvVO vo : deviceList) {
				vo.setRelationDeviceList(getVDeviceEnvList(vo.getId()));
			}
		}

		return deviceList;
	}

	@Override
	public DeviceEnvVO getDevice(Long deviceId, Boolean withVDeviceList) {
		Map<String, Object> map = new HashMap<>();
		map.put("device_id", deviceId);
		DeviceEnvVO device = deviceEnvMapper.list(map).stream().findFirst().orElse(null);
		if (withVDeviceList) {
			device.setRelationDeviceList(getVDeviceEnvList(deviceId));
		}
		return device;
	}

	@Override
	public List<DeviceTypeVO> getDeviceTypeByHouseType(String houseType, String kind) {
		String[] controllerTypeArr = houseType.split(",");
		Integer[] controllerTypeList = new Integer[controllerTypeArr.length];
		List<Integer> idsList = new ArrayList<>();
		for (int i = 0; i < controllerTypeList.length; i++) {
			idsList.add(Integer.parseInt(controllerTypeArr[i]));
		}
		HashMap<String, Object> param = new HashMap<>();
		param.put("kind", kind);
		param.put("idsList", idsList);
		return deviceEnvMapper.getDeviceTypeByHouseType(param);
	}

	@Override
	public HashMap<String, Object> getHouseTypeKindInfo() {
		HashMap<String, Object> result = new HashMap<>();

		result.put("houseType", deviceEnvMapper.getHouseType());
		result.put("kind", deviceEnvMapper.getKind());
		return result;
	}

	@Override
	public List<HashMap<String, Object>> getDeviceTypeList() {
//		return deviceEnvMapper.getDeviceTypeByHouseType();
		return deviceEnvMapper.getDeviceTypeList();
	}

	@Override
	public List<VDeviceInfoVO> getVDeviceList() {
		return deviceEnvMapper.getVDeviceList();
	}

	@Override
	public List<VDeviceEnvVO> insertVDeviceEnv(List<VDeviceEnvVO> voList) throws HttpStatusCodeException {

		try{
//			for(VDeviceEnvVO vo :voList){
//				if( vo.getParentDeviceId()  != null && vo.getDeviceNum()  != null &&  vo.getDeviceInsertOrder()  != null ) {
//					deviceEnvMapper.deleteVDeviceEnv(null, vo.getParentDeviceId(), vo.getDeviceNum(), vo.getDeviceInsertOrder());
//				} else {
//					throw new HttpClientErrorException(HttpStatus.CONFLICT, "not found device");
//				}
//			}
			for(VDeviceEnvVO vo :voList){
				deviceEnvMapper.insertVDeviceEnv(vo);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return voList;
	}

	@Override
	public List<VDeviceEnvVO> getVDeviceEnvList(Long deviceId) {
		try {
			return deviceEnvMapper.getVDeviceEnvList(deviceId);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}


	@Override
	public VDeviceEnvVO updateVDeviceEnv(VDeviceEnvVO vo) throws HttpStatusCodeException {
		try {
			if( vo.getParentDeviceId()  != null && vo.getDeviceNum()  != null &&  vo.getDeviceInsertOrder()  != null ) {
				deviceEnvMapper.deleteVDeviceEnv(null, vo.getParentDeviceId(), vo.getDeviceNum(), vo.getDeviceInsertOrder());
				deviceEnvMapper.insertVDeviceEnv(vo);
			} else {
				throw new HttpClientErrorException(HttpStatus.CONFLICT, "not found device");
			}
			return vo;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public Integer deleteVDeviceEnv(Long id, Long pDeviceId, Integer deviceNum, Integer deviceInsertOrder) {
		try{
			return deviceEnvMapper.deleteVDeviceEnv(id, pDeviceId, deviceNum, deviceInsertOrder);
		}catch(Exception e){
			log.debug(e.getMessage());
			return null;
		}
	}


	@Override
	public Integer deleteDevice(Long deviceId) {
		try {
			return deviceEnvMapper.deleteDevice(deviceId);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public Integer copyToNewGSM(Long fromGsmKey, Long toGsmKey) {
		log.info("copyToNewGSM :{} --> {}", fromGsmKey, toGsmKey);
		deviceEnvMapper.copyToNewGSM(fromGsmKey, toGsmKey);
		deviceEnvMapper.copyToNewGSMVDeviceEnv(fromGsmKey, toGsmKey);
		deviceEnvMapper.copyToNewGSMEDeviceEnv(fromGsmKey, toGsmKey);
		return 1;
	}

	//yechae
	@Override
	public List<EDeviceEnvVO> insertEDeviceEnv(List<EDeviceEnvVO> voList) {
		try {
			for (EDeviceEnvVO vo : voList) {
				deviceEnvMapper.insertEDeviceEnv(vo);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return voList;
	}

	@Override
	public List<EDeviceEnvVO> getEDeviceEnvList(Long deviceId) {
		try {
			return deviceEnvMapper.getEDeviceEnvList(deviceId);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return null;
	}

	@Override
	public EDeviceEnvVO updateEDeviceEnv(EDeviceEnvVO vo) {
		try {
			return deviceEnvMapper.updateEDeviceEnv(vo);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public Integer deleteEDeviceEnv(Long id) {
		try {
			return deviceEnvMapper.deleteEDeviceEnv(id);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}

	@Override
	public Integer deleteEDevicesEnv(Long parentDeviceId) {
		try {
			//수정
			return deviceEnvMapper.deleteVDevicesEnv(parentDeviceId);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}



}
