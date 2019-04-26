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

import egovframework.customize.service.CommonEnvService;
import egovframework.customize.service.CommonEnvVO;
import egovframework.customize.service.DeviceService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import java.util.HashMap;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kt.smartfarm.supervisor.mapper.DeviceMapper;


@Service("deviceService")
public class DeviceServiceImpl extends EgovAbstractServiceImpl implements DeviceService {
    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Resource(name = "deviceMapper")
    private DeviceMapper	deviceMapper;
    /** ID Generation */
    @Resource(name = "egovIdGnrService")
    private EgovIdGnrService    egovIdGnrService;

   
    
	@Override
	public HashMap<String, Object> getSensorInfoList() {
		try{
			HashMap<String,Object> result = deviceMapper.getSensorInfoList();
			return result;
		}catch(Exception e){
			log.debug(e.getMessage());
			return null;
		}		
	}
	@Override
	public HashMap<String, Object> getDeviceInfoList() {
		try{
			HashMap<String,Object> result = deviceMapper.getDeviceInfoList();
			return result;
		}catch(Exception e){
			log.debug(e.getMessage());
			return null;
		}		
		
	}


	@Override
	public HashMap<String, Object> insertDeviceList() {
		try{
			HashMap<String,Object> result = deviceMapper.insertDeviceList();
			return result;	
		}catch(Exception e){
			log.debug(e.getMessage());
			return null;
		}
		
		
	}	
	@Override
	public HashMap<String, Object> insertSensorList() {		
		
		try {
			HashMap<String, Object> result = deviceMapper.insertSensorList();
			return result;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
		
	}
}
