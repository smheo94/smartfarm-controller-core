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

import java.util.HashMap;
import java.util.List;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.ControlLogicDeviceVO;
import com.kt.smartfarm.service.ControlLogicEnvService;
import com.kt.smartfarm.service.ControlLogicEnvVO;

import javax.annotation.Resource;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/controllogic"})
public class ControlLogicEnvController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "controlLogicEnvService")
	private ControlLogicEnvService controlLogicEnvService;

	/**
	 * 제어로직 전체 list
	 * @return
	 */
	@RequestMapping(value= "", method = RequestMethod.GET)
	@ApiOperation("정의된 제어로직 목록을 조회합니다.")
	@ResponseBody
	public Result<List<ControlLogicEnvVO>> list(){
		try {
			return new Result(controlLogicEnvService.getAllLogicList());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 제어로직 프로퍼티 조회
	 * @return
	 */
	@RequestMapping(value= "/{logicId}/controlproperty", method = RequestMethod.GET)
	@ApiOperation("특정 제어로직에 사용될 프로퍼티 목록을 조회합니다.")
	@ResponseBody
	public Result<List<HashMap<String,Object>>> getLogicPropertyList(@PathVariable("logicId") Long logicId){
		try {
			return new Result(controlLogicEnvService.getLogicPropertyList(logicId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 제어로직 프로퍼티 조회
	 * @return
	 */
	@RequestMapping(value= "/{logicId}/controldevice", method = RequestMethod.GET)
	@ApiOperation("특정 제어로직에 사용될  제어 디바이스 종류를 조회합니다.")
	@ResponseBody
	public Result<List<ControlLogicDeviceVO>> getLogicDeviceList(@PathVariable("logicId") Long logicId){
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
//	public Result<DeviceEnvVO> get(@PathVariable("gsm_key") Integer gsmKey, @PathVariable("house_id") Integer houseId){
//		try {
//			return new Result(controlLogicEnvService.getRegList(gsmKey,houseId));
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}
//
//	@RequestMapping(value= "/{controllerId}", method = RequestMethod.DELETE)
//	@ResponseBody
//	public Result<DeviceEnvVO> delete(@PathVariable("gsm_key") Integer gsmKey,  @PathVariable("controllerId") Integer controllerId){
//		try {
//			return new Result(controlLogicEnvService.delete(gsmKey, controllerId));
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}


}
