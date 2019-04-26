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

import egovframework.cmmn.util.Result;
import egovframework.customize.config.SmartfarmInterceptorConfig;
import egovframework.customize.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value="")
public class CodeDataController {

	@Resource(name = "deviceEnvService")
	private DeviceEnvService deviceEnvService;


	@Resource(name = "controlLogicSettingService")
	private ControlLogicSettingService controlLogicSettingService;

	@Autowired
    SmartfarmInterceptorConfig smartfarmInterceptorConfig;

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
	@RequestMapping(value= "/housetype/{houseType}/kind/{kind}/devicetype", method = RequestMethod.GET)
	@ApiOperation(value = "하우스 타입/구동기종류별 구동기 Type 리스트: OLD( /gsm/{gsm_key}/listType/{houseType}/{kind}")
	//@RequestMapping(value= "/listType/{houseType}/{kind}", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<DeviceTypeVO>> getDeviceList(@PathVariable("houseType") String houseType, @PathVariable String kind){
		try {

			return new Result(deviceEnvService.getDeviceTypeByHouseType(houseType,kind));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 *
	 * controllerType 하고 kind 가져가는 api 만들어줘
	 */
	@RequestMapping(value= "/housetype", method = RequestMethod.GET)
	@ApiOperation(value = "HouseType 리스트: OLD( /gsm/{gsm_key}/houseTypeKindInfo")
	@ResponseBody
	public Result<HashMap<String,Object>> houseTypeKindInfo(){
		try {
			return new Result(deviceEnvService.gethouseTypeKindInfo());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value= "/devicetype", method = RequestMethod.GET)
	@ApiOperation(value = "Device Type 리스트: OLD( /gsm/{gsm_key}/deviceTypeList")
	@ResponseBody
	public Result<List<HashMap<String,Object>>> deviceTypeList(){
		try {
			return new Result(deviceEnvService.getDeviceTypeList());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}


	/**
	 * @description 가상 장치 리스트
	 * @return
	 */
	@RequestMapping(value= "/vdeviceinfo", method = RequestMethod.GET)
	@ApiOperation(value = "가상 장치 종류 리스트: OLD( /gsm/{gsm_key}/vDeviceList")
	@ResponseBody
	public Result<List<VDeviceInfoVO>> vDeviceList(){
		try {
			return new Result(deviceEnvService.getVDeviceList());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}


	/**
	 * @description 컨트롤 셋팅 오퍼레이터 리스트
	 * @return
	 */
	@RequestMapping(value= "/control_setting_operator", method = RequestMethod.GET)
	@ApiOperation(value = "컨트롤 셋팅 오퍼레이터 리스트")
	@ResponseBody
	public Result<List<ControlSettingOperatorVO>> listControlSettingOperator(){
		try {
			return new Result(controlLogicSettingService.listControlSettingOperation());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}


    /**
     * @description 컨트롤 셋팅 오퍼레이터 리스트
     * @return
     */
    @RequestMapping(value= "/server_type", method = RequestMethod.GET)
    @ApiOperation(value = "컨트롤 셋팅 오퍼레이터 리스트")
    @ResponseBody
    public Result<String> serverType(){
        try {
            return new Result(smartfarmInterceptorConfig.SYSTEM_TYPE);
        } catch(Exception e) {
            return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
        }
    }



}
