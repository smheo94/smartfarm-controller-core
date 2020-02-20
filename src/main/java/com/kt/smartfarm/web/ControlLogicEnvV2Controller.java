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

import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

//import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping(value={"/v2/controllogic"})
public class ControlLogicEnvV2Controller {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";

	@Resource(name = "controlLogicEnvService")
	private ControlLogicEnvService controlLogicEnvService;

	/**
	 * 제어로직 전체 list
	 * @return
	 */
	@RequestMapping(value= "", method = RequestMethod.GET)
	//@ApiOperation("정의된 제어로직 목록을 조회합니다.")
	@ResponseBody
	public Result<List<ControlLogicV2VO>> list(){
		try {
			return new Result(controlLogicEnvService.getAllLogicListV2());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 제어로직 프로퍼티 조회
	 * @return
	 */
	@RequestMapping(value= "/{logicId}/ml ", method = RequestMethod.GET)
	//@ApiOperation("특정 제어로직에 사용될 프로퍼티 목록을 조회합니다.")
	@ResponseBody
	public Result<List<ControlLogicPropertiesVO>> getLogicPropertyList(@PathVariable("logicId") Integer logicId){
		try {
			return new Result(controlLogicEnvService.getLogicPropertyListV2(logicId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 제어로직 프로퍼티 조회
	 * @return
	 */
	@RequestMapping(value= "/{logicId}/controldevice", method = RequestMethod.GET)
	//@ApiOperation("특정 제어로직에 사용될  제어 디바이스 종류를 조회합니다.")
	@ResponseBody
	public Result<List<ControlLogicDeviceVO>> getLogicDeviceList(@PathVariable("logicId") Integer logicId){
		try {
			return new Result(controlLogicEnvService.getLogicDeviceList(logicId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	
//
//
//	@RequestMapping(value= "/{house_id}", method = RequestMethod.GET)
//	@ResponseBody
//	public Result<DeviceEnvVO> get(@PathVariable("gsm_key") Long gsmKey, @PathVariable("house_id") Long houseId){
//		try {
//			return new Result(controlLogicEnvService.getRegList(gsmKey,houseId));
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}
//
//	@RequestMapping(value= "/{controllerId}", method = RequestMethod.DELETE)
//	@ResponseBody
//	public Result<DeviceEnvVO> delete(@PathVariable("gsm_key") Long gsmKey,  @PathVariable("controllerId") Long controllerId){
//		try {
//			return new Result(controlLogicEnvService.delete(gsmKey, controllerId));
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}


}
