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
package com.kt.smartfarm.web;

import java.util.List;

import com.kt.cmmn.util.InterceptPost;
import com.kt.cmmn.util.InterceptPre;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.*;
import javax.annotation.Resource;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value={"/device"})
//@RequestMapping("/env/{gsm_key}/device")
public class DeviceEnvController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "deviceEnvService")
	private DeviceEnvService deviceEnvService;

	@Resource(name="authCheckService")
	private AuthCheckService authCheckService;
	/**
	 * 구동기,센서 등록
	 * @param device	 *
	 * @return
	 **/
	@SuppressWarnings("unchecked")
	@RequestMapping(value= {"", "/"}, method = RequestMethod.POST)
	@ApiOperation(value = "구동기,센서 등록 : OLD( /gsm/{gsm_key}/")
	@ResponseBody
	@InterceptPost
	public Result<List<DeviceEnvVO>> insert(@RequestBody List<DeviceEnvVO> device){
		try {
			return new Result(deviceEnvService.insert(device));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, device);
		}
	}

	/**
	 * @description 저장된 구동모듈에 해당하는 deviceList 가져ㅑ오기
	 * @param gsmKey
	 * @return
	 */
	@RequestMapping(value= "/gsm/{gsm_key}", method = RequestMethod.GET)
	@ApiOperation(value = "GMS 에 저장된 전체 Device 가져오기")
	@ResponseBody
	public Result<List<DeviceEnvVO>> list(@PathVariable("gsm_key") Integer gsmKey,
										  @RequestParam("withVDeviceList") Boolean withVDeviceList,
										  @RequestParam("withEDeviceList") Boolean withEDeviceList){
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(deviceEnvService.list(gsmKey, null, withVDeviceList, withEDeviceList));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	
	/**
	 * @description 구동기 모듈에 해당하는 구동기,센서 수정
	 * @param gsmKey
	 * @param controllerId
	 * @param device
	 * @return
	 */
	@RequestMapping(value= "/gsm/{gsm_key}/controller/{controllerId}", method = RequestMethod.PUT)
	@ApiOperation(value = "구동기 모듈에 해당하는 구동기,센서 수정 : OLD( /gsm/{gsm_key}/{controllerId}")
	@ResponseBody
	@InterceptPre
	public Result<DeviceEnvVO> update(@PathVariable("gsm_key") Integer gsmKey, @PathVariable("controllerId") String controllerId, @RequestBody List<DeviceEnvVO> device){
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
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
	@RequestMapping(value= "/gsm/{gsm_key}/controller/{controllerId}", method = RequestMethod.GET)
	@ApiOperation(value = "저장된 구동모듈에 해당하는 deviceList 가져오기 : OLD( /gsm/{gsm_key}/list/{controllerId}")
	@ResponseBody
	public Result<List<DeviceEnvVO>> list( @PathVariable("gsm_key") Integer gsmKey, @PathVariable("controllerId") Integer controllerId,
										   @RequestParam(value = "withVDeviceList", required = false) Boolean withVDeviceList) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(deviceEnvService.list(gsmKey, controllerId, withVDeviceList, null));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
//	@RequestMapping(value= "/gsm/{gsm_key}/controller/{controllerId}", method = RequestMethod.GET)
//	@ApiOperation(value = "컨트롤러에 등록된 Device동작하나(?): OLD( /gsm/{gsm_key}/{controllerId})")
//	//@RequestMapping(value= "/{controllerId}", method = RequestMethod.GET)
//	@ResponseBody
//	public Result<DeviceEnvVO> getDevice( @PathVariable("gsm_key") Integer gsmKey,  @PathVariable("controllerId") Integer controllerId){
//		try {
//			return new Result(deviceEnvService.getDevice(gsmKey, controllerId));
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}
	
	/**
	 * 구동모듈에 해당하는 device 삭제
	 * @param gsmKey
	 * @param controllerId
	 * @return
	 */
	@RequestMapping(value= "/gsm/{gsm_key}/controller/{controllerId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "구동모듈에 해당하는 Device 삭제: OLD( /gsm/{gsm_key}/{controllerId})")
	@ResponseBody
	@InterceptPre
	public Result<DeviceEnvVO> deleteControllerDeivces(@PathVariable("gsm_key") Integer gsmKey,  @PathVariable("controllerId") Integer controllerId){
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(deviceEnvService.deleteControllerDeivces(gsmKey, controllerId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 가상장치 등록 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value= "/{deviceId}/relationdevices", method = RequestMethod.POST)
	//Device Type 별로 입력 @RequestMapping(value= "/vDeviceList", method = RequestMethod.POST)
	@ResponseBody
	@InterceptPost
	public Result<List<VDeviceEnvVO>> vDeviceList(@PathVariable("deviceId") Integer deviceId, @RequestBody List<VDeviceEnvVO> vo){
		try {
			vo.stream().forEach(v -> v.setParentDeviceId(deviceId));
			return new Result(deviceEnvService.insertVDeviceEnv(vo));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value= "/{deviceId}/relationdevices", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptPre
	public Result<VDeviceEnvVO> updateVDeviceList(@PathVariable("deviceId") Integer deviceId, @RequestBody VDeviceEnvVO vo){
		try {
			vo.setParentDeviceId(deviceId);
			return new Result(deviceEnvService.updateVDeviceEnv(vo));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/{id}/relationdevices", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<VDeviceEnvVO> deleteVDeviceList(@PathVariable("id") Integer id){
		try {
			return new Result(deviceEnvService.deleteVDeviceEnv(id, null));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	/**
	 * @description 가상장치 등록
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value= "/{deviceId}/relationdevices", method = RequestMethod.GET)
	//Device Type 별로 입력 @RequestMapping(value= "/vDeviceList", method = RequestMethod.POST)
	@ResponseBody
	public Result<List<VDeviceInfoVO>> vDeviceList(@PathVariable("deviceId") Integer deviceId){
		try {
			return new Result(deviceEnvService.getVDeviceEnvList(deviceId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 장치조회
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value= "/{deviceId}", method = RequestMethod.GET)
	//Device Type 별로 입력 @RequestMapping(value= "/vDeviceList", method = RequestMethod.POST)
	@ResponseBody
	public Result<DeviceEnvVO> vDeviceList(@PathVariable("deviceId") Integer deviceId,
										   @RequestParam(value = "withVDeviceList", required = false) Boolean withVDeviceList){
		try {
			return new Result(deviceEnvService.getDevice(deviceId, withVDeviceList));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}


	/**
	 * @description 장치조회
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value= "/{deviceId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result<DeviceEnvVO> deleteDevice(@PathVariable("deviceId") Integer deviceId, @Param("gsmKey") Integer gsmKey){
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(deviceEnvService.deleteDevice(deviceId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
//	@RequestMapping(value= "/deviceTypeList", method = RequestMethod.GET)
//	@ResponseBody
//	public Result<DeviceEnvVO> deviceTypeList(){
//		try {
//			return new Result(deviceEnvService.getDevice(deviceId, withVDeviceList));
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}

	/**
	 * @author yechae
	 * @description 전류감지기 등록(electric currnet)
	 * @param vo
	 * @return
	 */
	@RequestMapping(value= "/{deviceId}/electricdevices", method = RequestMethod.POST)
	@ApiOperation(value="전류 감지기 등록")
	@ResponseBody
	@InterceptPost
	public Result<List<VDeviceEnvVO>> eDeviceList(@PathVariable("deviceId") Integer deviceId, @RequestBody List<EDeviceEnvVO> vo){
		try {
			return new Result(deviceEnvService.insertEDeviceEnv(vo));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	/**
	 * @author yechae
	 * @description 전류감지기 수정(electric currnet)
	 * @param deviceId, vo
	 * @return
	 */

	@RequestMapping(value= "/{deviceId}/electricdevice", method = RequestMethod.PUT)
	@ApiOperation(value="전류 감지기 수정")
	@ResponseBody
	@InterceptPre
	public Result<VDeviceEnvVO> updateEDeviceList(@PathVariable("deviceId") Integer deviceId, @RequestBody VDeviceEnvVO vo){
		try {
			return new Result(deviceEnvService.updateVDeviceEnv(vo));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	/**
	 * @author yechae
	 * @description 전류감지기 삭제 by deviceId(electric currnet)
	 * @param id
	 * @return
	 */
	@RequestMapping(value= "/{id}/electricdevice", method = RequestMethod.DELETE)
	@ApiOperation(value = "전류 감지기 id로 전류감지기 삭제 ")
	@ResponseBody
	@InterceptPre
	public Result<VDeviceEnvVO> deleteEDevice(@PathVariable("id") Integer id){
		try {
			return new Result(deviceEnvService.deleteVDeviceEnv(id, null));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	/**
	 * @author yechae
	 * @description 전류감지기 조회(electric current)
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value= "/{deviceId}/electricdevices", method = RequestMethod.GET)
	@ApiOperation(value = "전류감지기 조회")
	@ResponseBody
	public Result<List<VDeviceInfoVO>> eDeviceList(@PathVariable("deviceId") Integer deviceId){
		try {
			return new Result(deviceEnvService.getVDeviceEnvList(deviceId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

    /**
     * @author yechae
     * @description 전류감지기 삭제 by p_id(electric currnet)
     * @param pid
     * @return
     */
    @RequestMapping(value= "/{pid}/electricdevices", method = RequestMethod.DELETE)
	@ApiOperation(value = "p_device_id로 전류 감지기 전체 삭제")
    @ResponseBody
    @InterceptPre
    public Result<VDeviceEnvVO> deleteEDevices(@PathVariable("pid") Integer pid){
        try {
            return new Result(deviceEnvService.deleteEDevicesEnv(pid));
        } catch(Exception e) {
            return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
        }
    }

}
