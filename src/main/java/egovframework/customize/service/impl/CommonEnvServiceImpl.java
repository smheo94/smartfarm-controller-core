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
package egovframework.customize.service.impl;

import egovframework.cmmn.util.Result;
import egovframework.customize.service.CommonEnvService;
import egovframework.customize.service.CommonEnvVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.client.RestTemplate;

import com.kt.smartfarm.supervisor.mapper.CommonEnvMapper;


@Service("commonEnvService")
public class CommonEnvServiceImpl extends EgovAbstractServiceImpl implements CommonEnvService {
    private static final Logger log = LoggerFactory.getLogger(CommonEnvServiceImpl.class);

    @Resource(name = "commonEnvMapper")
    private CommonEnvMapper	commonEnvMapper;
    /** ID Generation */
    @Resource(name = "egovIdGnrService")
    private EgovIdGnrService    egovIdGnrService;
    
   
    
	@Override
	public CommonEnvVO getThreshold(int gsmKey) throws Exception {
		CommonEnvVO commonEnvVO = commonEnvMapper.getThreshold(gsmKey);

		return commonEnvVO;
	}


	@Override
	public CommonEnvVO getThresholdDefault(int gsmKey) {
		CommonEnvVO commonEnvVO = new CommonEnvVO();
		try{
			commonEnvVO = commonEnvMapper.getThresholdDefault(gsmKey);				
		}catch(Exception e){
			e.getMessage();
		}
		return commonEnvVO;		
	}

	 @Override
	public void updateThreshold(CommonEnvVO vo) {
		try{
			commonEnvMapper.updateThreshold(vo);	
		}catch(Exception e){
			log.debug(e.getMessage());
		}				
	}
	 
	@Override
	public void updateThresholdDefault(CommonEnvVO vo) {
		
	}

	
	

	@Override
	public CommonEnvVO getOutWeather(int gsmKey) throws Exception {
		CommonEnvVO commonEnvVO = commonEnvMapper.getThreshold(gsmKey);
		return commonEnvVO;		
	}
	
	@Override
	public CommonEnvVO getOutWeatherDefault(int gsmKey) {
		CommonEnvVO commonEnvVO = new CommonEnvVO();
		try{
			commonEnvVO = commonEnvMapper.getOutWeatherDefault(gsmKey);	
		}catch(Exception e){
			e.getMessage();
		}
		return commonEnvVO;
	}

	@Override
	public void updateOutWeather(CommonEnvVO vo) throws Exception {
		try{
			commonEnvMapper.updateThreshold(vo);	
		}catch(Exception e){
			log.debug(e.getMessage());
		}	
	}

	@Override
	public void updateOutWeatherDefault(CommonEnvVO vo) {
		
	}


	@Override
	public void updateEnvInfo(CommonEnvVO vo) throws Exception {
		try{
			commonEnvMapper.updateEnvInfo(vo);
		}catch(Exception e){
			log.debug(e.getMessage());
		}		
	}

	@Override
	public CommonEnvVO getOutweatherInfo(HashMap<String, Object> param) {
		CommonEnvVO commonEnvVO = commonEnvMapper.getOutweatherInfo(param);
		return commonEnvVO;
	}


//	@Override
//	public HashMap<String, Object> getSensorInfoList() {
//		try{
//			HashMap<String,Object> result = commonEnvMapper.getSensorInfoList();
//			return result;
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}		
//	}
	
//	@Override
//	public HashMap<String, Object> getDeviceInfoList() {
//		try{
//			HashMap<String,Object> result = commonEnvMapper.getDeviceInfoList();
//			return result;
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}				
//	}


	@SuppressWarnings("unchecked")
	@Transactional()
	@Override
	public HashMap<String, Object> insertDeviceList(HashMap<String,Object> actuatorModuleInfo, String contextPath) {		
		try{
			
			HashMap<String,Object> result = new HashMap<String,Object>();
			HashMap<String, Object> controllerInfo = (HashMap<String,Object>)actuatorModuleInfo.get("controller_list");
			String description = (String)controllerInfo.get("description");
			controllerInfo.put("description", description);
			// 구동모듈 등록 , Controller insert
			commonEnvMapper.insertController(controllerInfo);
			int insertedControllerId = (int)controllerInfo.get("controller_id");
			
			//구동모듈의 device 등록
			List<HashMap<String,Object>> arrDeviceData = (List<HashMap<String,Object>>)controllerInfo.get("array_device_data");
			if(arrDeviceData != null && arrDeviceData.size() > 0){
				for(HashMap<String,Object> row: arrDeviceData){
					row.put("controller_id", insertedControllerId);
					row.put("mode", 1);
					//TODO Query 작성하면끝
					commonEnvMapper.insertDeviceList(row);
					commonEnvMapper.insertDeviceStatusUpdate(row);
					row.put("isNew", true);
				}
			}			
			result.put("controller_id",insertedControllerId);
			
			if(result != null){
				if(contextPath.equals("http://127.0.0.1:8080")){
					RestTemplate client = new RestTemplate();
					HashMap<String,Object> actuatorModuleInsertedInfo = new HashMap<>();
					actuatorModuleInsertedInfo.put("controller_list", controllerInfo);
					actuatorModuleInsertedInfo.put("array_device_data", arrDeviceData);					
					
					Result controllerResult = client.postForObject(
							"http://127.0.0.1:9876/management/controller/add/", actuatorModuleInsertedInfo, Result.class);

					if(controllerResult.getStatus() != 0 || controllerResult == null) {
						// ERROR
						return null;
					}
				}
			}
			return result;
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
			//TODO rollback 되는지 확인			
		}		
	}	
	
	@SuppressWarnings("unchecked")
	@Transactional()
	@Override
	public HashMap<String, Object> updateDeviceList(HashMap<String,Object> actuatorModuleInfo, String contextPath) {
		try{			
			HashMap<String,Object> result = new HashMap<String,Object>();
			HashMap<String, Object> controllerInfo = (HashMap<String,Object>)actuatorModuleInfo.get("controller_list");//
			String controllerId = (String)controllerInfo.get("id");
			// 구동모듈 등록 , Controller id로 UPDATE
			commonEnvMapper.updateController(controllerInfo);			
			
			//device update ( deviceType 체크, 새로 등록된 device등록, 상태 업데이트, 
			List<HashMap<String,Object>> arrDeviceData = (List<HashMap<String,Object>>)controllerInfo.get("array_device_data");
			List<String> deviceIdList = new ArrayList<>();
			
			if(arrDeviceData != null && arrDeviceData.size() > 0){						
				for(HashMap<String,Object> row: arrDeviceData){
					String deviceId = (String)row.get("device_id");
					row.put("controller_id", controllerId);
					
					if( deviceId != null && !deviceId.isEmpty() ) {								
						deviceIdList.add(deviceId);
					}
					
					if( commonEnvMapper.deleteDeviceOfChangedDeviceType(row)  > 0 ) {
						//DeviceType 이 바뀐 장비는 지우고 Device_id를 ""로 넣어서 새로 입력되도록 한다.
						row.put("device_id", "");
					}
				}
				
				
				log.debug(" ");
				for(HashMap<String,Object> row: arrDeviceData){
					String deviceId = (String)row.get("device_id");

					row.put("controller_id", controllerId);
					row.put("mode", 1);
					
					if(deviceId.isEmpty() ){ //새로운 device 등록	
						row.put("isNew", true);
						if( row.get("device_type_id") == null || row.get("device_type_id").equals("0") ) {
							continue;
						}						
						commonEnvMapper.insertDeviceList(row);
						commonEnvMapper.insertDeviceStatusUpdate(row);			
						
						int insertedDeviceId = (int)row.get("device_id");
						deviceIdList.add(String.valueOf(insertedDeviceId));
					} else {
						row.put("isNew", false);
						commonEnvMapper.updateDeviceList(row);
					}							
				}				
			}
			commonEnvMapper.deleteDeviceOfRemovedDevice(controllerId,  deviceIdList);
			
			if(result != null){
				if(contextPath.equals("http://127.0.0.1:8080")){
					RestTemplate client = new RestTemplate();
					HashMap<String,Object> actuatorModuleInsertedInfo = new HashMap<>();
					actuatorModuleInsertedInfo.put("controller_list", controllerInfo);
					actuatorModuleInsertedInfo.put("array_device_data", arrDeviceData);					
					
					Result controllerResult = client.postForObject(
							"http://127.0.0.1:9876/management/controller/modify/", actuatorModuleInsertedInfo, Result.class);

					if(controllerResult.getStatus() != 0 || controllerResult == null) {
						// ERROR
						return null;
					}
				}
			}
			return result;
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
			//TODO rollback 되는지 확인			
		}		
	}	
	
	@Override
	public HashMap<String, Object> deleteControllerInfo(String controllerId, String contextPath) {
		try{
			if(!deleteDeviceInfoUsingControllerId(controllerId, null)){
				return null;
			}
			
			if(!deleteControllerInfo(Integer.parseInt(controllerId))){
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
			//ROLLBACK
		}
		return null;
	}
	
	private boolean deleteControllerInfo(int controllerId) {
		try{
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("id", controllerId);
			commonEnvMapper.deleteControllerInfo(controllerId);
			return true;
		}catch(Exception e){
			return false;
		}
	}


	private boolean deleteDeviceInfoUsingControllerId(String controllerId, List<String> sensorDeviceIdList) {
		try{
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("controller_id", controllerId);
			if( sensorDeviceIdList != null && sensorDeviceIdList.size() > 0  ) {
				param.put("sensor_device_ids", sensorDeviceIdList);
			}		
			commonEnvMapper.deleteDeviceInfoUsingControllerId(param);
			return true;
		}catch(Exception e){
			return false;
		}		
	}

	/**************
	 * sensorModule
	 * insert or update
	 * isNew 로 판단
	 **************/
	@Transactional
	@Override
	public HashMap<String, Object> upsertSensorList(HashMap<String, Object> sensorModuleInfo, String contextPath, boolean isNew){		
		try {			
			HashMap<String, Object> result = new HashMap<String, Object>();
			boolean isFirst = true;
			int sensorControllerId = 0;
			List<HashMap<String, Object>> sensorListInfoList = (List<HashMap<String, Object>>)sensorModuleInfo.get("sensor_list");
			if(isNew){
				// [ 새로 추가 ]
				int controllerId = 0;
				String newSensorName = (String)sensorModuleInfo.get("name");
				for (HashMap<String, Object> sensorListInfo : sensorListInfoList) {
					if(isFirst){
						sensorListInfo.put("controller_description", newSensorName);
						sensorListInfo.put("sensorId", "21"); // 21 = 내부센서
						try {
							commonEnvMapper.insertSensorControllerInfo(sensorListInfo);
							controllerId = (int) sensorListInfo.get("controller_id");
						} catch (Exception e) {
							//
							log.error("[Exception]", e);
							return null;
						}						
						isFirst = false;
					}
					
					String setupDateString = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(DateTime.now());
					sensorListInfo.put("now", setupDateString);
					if (!isFirst) {
						sensorListInfo.put("controller_id", controllerId);
					}
					try {
						if (0 == (Integer) sensorListInfo.get("device_type_id")) {
							continue;
						}
						commonEnvMapper.insertSensorDevice(sensorListInfo);
						result.put("controller_id", controllerId);
					} catch (Exception e) {
						log.error("[Exception]", e);
						return null;
					}					
				}
			}else{
				// [ 수정 ]
				try {
					List<String> sensorDeviceIdList = new ArrayList<>();
					for (HashMap<String, Object> sensorListInfo : sensorListInfoList) {
						Object deviceIdObj = sensorListInfo.get("device_id");
						if(deviceIdObj != null ) {
							sensorDeviceIdList.add(deviceIdObj.toString());
						}
					}
					// 구동기모듈과 동일한 method 사용
					deleteDeviceInfoUsingControllerId(String.valueOf(sensorControllerId), sensorDeviceIdList);
					result.put("controller_id", sensorControllerId);
				} catch (Exception e) {
					log.error("[Exception]", e);
					return null;
				}
				for (HashMap<String, Object> sensorListInfo : sensorListInfoList) {
					sensorListInfo.put("controller_id", sensorControllerId);
					sensorListInfo.put("sensorId", "21");
					if (isFirst && sensorListInfo.get("ip") != null) {
						try {
							commonEnvMapper.updateSensorControllerInfo(sensorListInfo);
						} catch (Exception e) {
							//
							log.error("[Exception]", e);
							return null;
						}
						isFirst = false;
					}
					try {
						if (0 == (Integer) sensorListInfo.get("device_type_id")) {
							// UI에 사용안함이면 입력할 필요가 없음
							continue;
						}
						commonEnvMapper.insertSensorDevice(sensorListInfo);						
					} catch (Exception e) {
						//
						log.error("[Exception]", e);
						return null;
					}
				}
			}
			
			if(contextPath.equals("http://127.0.0.1:8080")){
				RestTemplate client = new RestTemplate();
				Result sensorModuleResult = client.postForObject(
						"http://127.0.0.1:9876/management/module", sensorModuleInfo, Result.class);
				
				// 21은 내부센서 controller_info_id 박아둔것
//				url = requestUrl+"/management/module/add/" + inputSensorName+"/21";
//				url = requestUrl+"/management/module/modify/" + $("#sensorModuleSelector").val()+"/21"; 

				// ERROR
				if(sensorModuleResult.getStatus() != 0 || sensorModuleResult == null) {
					return null;
				}
			}			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();			
		}
	}


	@Transactional
	@Override
	public HashMap<String, Object> deleteSensorList(String sensorControllerId, String contextPath) {
		try{
			HashMap<String,Object> result = new HashMap<String, Object>();
			if(!deleteDeviceInfoUsingControllerId(sensorControllerId, null) ){
				return null;  
			}
			int controllerId = Integer.parseInt(sensorControllerId);
			if(!deleteControllerInfo(controllerId)){
				return null;
			}
			if(contextPath.equals("http://127.0.0.1:8080")){
				RestTemplate client = new RestTemplate();			
				client.delete("http://127.0.0.1:9876", sensorControllerId);
			}	
			result.put("isSuccess", "isSuccess");
			return result;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
				
	}


	@Override
	public List<HashMap<String, Object>> selectedControllerInfo(String selectedControllerId, Integer controllerType) {
		HashMap<String, Object> param = new HashMap<String, Object>();
	    List<HashMap<String, Object>> list = null;	    	    
	    param.put("controller_id", selectedControllerId);
		param.put("sensorId", controllerType);
	    list = commonEnvMapper.selectedControllerInfo(param);
	    
	    return list;
	}


	@Override
	public HashMap<String, Object> selectSensorList(Integer sensorType) {
		HashMap<String, Object> result = new HashMap<String, Object>();
	    List<HashMap<String, Object>> list = null;
	    list = commonEnvMapper.selectSensorList(sensorType);
	    result.put("data", list);
	    if(sensorType == 21){
		    result.put("channel_data",  commonEnvMapper.getSensorChannel(false));
		    result.put("sensor_type_list", commonEnvMapper.getSensorGroupList(null));
		}else if(sensorType == 23){
		    result.put("channel_data",  commonEnvMapper.getSensorChannel(false));
		}else if(sensorType == 24){
		    result.put("channel_data",  commonEnvMapper.getSensorChannel(true));
		}	    
	    return result;
	}


	@Override
	public List<HashMap<String, Object>> getControllerListOfControlModule(int[] controllerInfoId) {
		try{		
		
			HashMap<String, Object> param = new HashMap<>();
	    	param.put("controller_info_id", controllerInfoId);
	    	List<HashMap<String, Object>> controllerInfoList = commonEnvMapper.selectControllerListOfControlModule(param);
			
	    	for(HashMap<String, Object> controllerInfo : controllerInfoList ) {
				List<Integer> houseIds = new ArrayList<>();
				List<HashMap<String, Object>> houseIdList = selectControllerHouseMapList(null,  (Integer)controllerInfo.get("id") );
				if( houseIdList.size() > 0 ) {
					for(HashMap<String, Object> controllerMapHouseInfo :  houseIdList) {
						houseIds.add( (Integer)controllerMapHouseInfo.get("green_house_id") );
					}
				}
				controllerInfo.put("green_house_ids", houseIds);
			}
			return controllerInfoList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	public List<HashMap<String, Object>> selectControllerHouseMapList(Integer greenHouseId, Integer controllerId) {		
		try {	
			HashMap<String, Object> param = new HashMap<>();			
			param.put("controller_id", controllerId);			
			param.put("green_house_id", greenHouseId);
			return commonEnvMapper.selectControllerHouseMapList(param);			
		} catch (Exception e) {
			log.error("selectControllerHouseMapList fail.",e);
			log.error("[Exception]", e);
			return null;
		}
	}


	@Override
	public List<HashMap<String, Object>> selectDeviceOfControlModule(Integer controllerId) {
		try{
			HashMap<String,Object> param = new HashMap<String,Object>();
			param.put("controller_id", controllerId);
			return commonEnvMapper.selectDeviceOfControlModule(param);	
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
				
	}
}
