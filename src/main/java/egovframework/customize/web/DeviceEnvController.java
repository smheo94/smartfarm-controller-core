/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.customize.web;

import java.util.HashMap;
import java.util.List;
import egovframework.cmmn.util.Result;
import egovframework.customize.service.DeviceEnvService;
import egovframework.customize.service.DeviceEnvVO;
import egovframework.customize.service.DeviceTypeVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/env/{gsm_key}/device")
public class DeviceEnvController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "deviceEnvService")
	private DeviceEnvService deviceEnvService;
		
	
	/**
	 * 구동기,센서 등록
	 * @param request
	 * @param gsmKey
	 * @param device
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= "/", method = RequestMethod.POST)
	@ResponseBody
	public Result<List<DeviceEnvVO>> insert( @PathVariable("gsm_key") String gsmKey, @RequestBody List<DeviceEnvVO> device){
		try {
			return new Result(deviceEnvService.insert(device));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, device);
		}
	}
	
	/**
	 * @description 구동기 모듈에 해당하는 구동기,센서 수정
	 * @param gsmKey
	 * @param controllerId
	 * @param device
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= "/{controllerId}", method = RequestMethod.PUT)
	@ResponseBody
	public Result<DeviceEnvVO> update(@PathVariable("gsm_key") String gsmKey, @PathVariable("controllerId") String controllerId, @RequestBody List<DeviceEnvVO> device){		
		try {
			return new Result(deviceEnvService.update(device)); // gsmKey, controllerId, deviceId 기준으로 업데이트
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, device);
		}
	}
	
	/**
	 * @description 저장된 구동모듈에 해당하는 deviceList 가져ㅑ오기
	 * @param gsmKey
	 * @param controllerId
	 * @return
	 */
	@RequestMapping(value= "/list/{controllerId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<DeviceEnvVO>> list( @PathVariable("gsm_key") String gsmKey, @PathVariable("controllerId") Integer controllerId){
		try {
			return new Result(deviceEnvService.list(gsmKey, controllerId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/{controllerId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<DeviceEnvVO> get( @PathVariable("gsm_key") String gsmKey,  @PathVariable("controllerId") Integer controllerId){
		try {
			return new Result(deviceEnvService.get(gsmKey, controllerId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * 구동모듈에 해당하는 device 삭제
	 * @param gsmKey
	 * @param controllerId
	 * @return
	 */
	@RequestMapping(value= "/{controllerId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result<DeviceEnvVO> delete(@PathVariable("gsm_key") String gsmKey,  @PathVariable("controllerId") Integer controllerId){
		try {
			return new Result(deviceEnvService.delete(gsmKey, controllerId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 구동기 타입에 따른 deviceList
	 * @param request
	 * @param controllerType
	 * 		1 : 단동형 Only, 
	 *		2 : 연동형 Only , 
	 *		3 : 연동/단동형 Only, 
	 *		4: 과수원 Only, 
	 *		7 : 싱글,연동,과수 사용, 
	 *		8 : 노지 Only, 
	 *		15 : 단동,연동,과수,노지 사용
	 * @param kind
	 * 		sensor_inner
	 * 		sensor_outer
	 * 		info
	 * 		actuator
	 * 		nutrient
	 * 		v_device
	 * @return
	 */
	@RequestMapping(value= "/listType/{houseType}/{kind}", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<DeviceTypeVO>> getDeviceList(@PathVariable("houseType") String houseType, @PathVariable String kind){
		try {
			
			return new Result(deviceEnvService.getDeviceList(houseType,kind));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 *  
	 * controllerType 하고 kind 가져가는 api 만들어줘
	 */
	@RequestMapping(value= "/houseTypeKindInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result<HashMap<String,Object>> houseTypeKindInfo(){
		try {
			return new Result(deviceEnvService.gethouseTypeKindInfo());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/deviceTypeList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<HashMap<String,Object>>> deviceTypeList(){
		try {
			return new Result(deviceEnvService.getDeviceTypeList());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
}
