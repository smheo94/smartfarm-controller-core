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
import egovframework.customize.service.VDeviceEnvVO;
import egovframework.customize.service.VDeviceInfoVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/gsm/{gsm_key}")
//@RequestMapping("/env/{gsm_key}/device")
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
	@RequestMapping(value= "/device/", method = RequestMethod.POST)
	@ApiOperation(value = "구동기,센서 등록 : OLD( /gsm/{gsm_key}/")
	@ResponseBody
	public Result<List<DeviceEnvVO>> insert( @PathVariable("gsm_key") Integer gsmKey, @RequestBody List<DeviceEnvVO> device){
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
	@RequestMapping(value= "/controller/{controllerId}/devices/", method = RequestMethod.PUT)
	@ApiOperation(value = "구동기 모듈에 해당하는 구동기,센서 수정 : OLD( /gsm/{gsm_key}/{controllerId}")
	@ResponseBody
	public Result<DeviceEnvVO> update(@PathVariable("gsm_key") Integer gsmKey, @PathVariable("controllerId") String controllerId, @RequestBody List<DeviceEnvVO> device){
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
	@RequestMapping(value= "/controller/{controllerId}/device/", method = RequestMethod.GET)
	@ApiOperation(value = "저장된 구동모듈에 해당하는 deviceList 가져오기 : OLD( /gsm/{gsm_key}/list/{controllerId}")
	@ResponseBody
	public Result<List<DeviceEnvVO>> list( @PathVariable("gsm_key") Integer gsmKey, @PathVariable("controllerId") Integer controllerId){
		try {
			return new Result(deviceEnvService.list(gsmKey, controllerId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/controller/{controllerId}", method = RequestMethod.GET)
	@ApiOperation(value = "컨트롤러에 등록된 Device동작하나(?): OLD( /gsm/{gsm_key}/{controllerId})")
	//@RequestMapping(value= "/{controllerId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<DeviceEnvVO> get( @PathVariable("gsm_key") Integer gsmKey,  @PathVariable("controllerId") Integer controllerId){
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
	@RequestMapping(value= "/controller/{controllerId}/device", method = RequestMethod.DELETE)
	@ApiOperation(value = "구동모듈에 해당하는 Device 삭제: OLD( /gsm/{gsm_key}/{controllerId})")
	@ResponseBody
	public Result<DeviceEnvVO> delete(@PathVariable("gsm_key") Integer gsmKey,  @PathVariable("controllerId") Integer controllerId){
		try {
			return new Result(deviceEnvService.delete(gsmKey, controllerId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 가상장치 등록 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value= "/device/{deviceId}/realtiondevices", method = RequestMethod.POST)
	//Device Type 별로 입력 @RequestMapping(value= "/vDeviceList", method = RequestMethod.POST)
	@ResponseBody
	public Result<List<VDeviceInfoVO>> vDeviceList(@PathVariable("deviceId") Integer deviceId, @RequestBody List<VDeviceEnvVO> vo){
		try {
			return new Result(deviceEnvService.insertVDeviceEnv(vo));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
}
