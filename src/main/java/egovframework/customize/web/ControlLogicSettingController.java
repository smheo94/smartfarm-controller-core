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
import egovframework.customize.service.ControlLogicSettingService;
import egovframework.customize.service.ControlLogicSettingVO;
import egovframework.customize.service.DeviceEnvVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/control_logic_setting")
public class ControlLogicSettingController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "controlLogicSettingService")
	private ControlLogicSettingService service;

	@RequestMapping(value= "/gsm/{gsmKey}/house/{houseId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ControlLogicSettingVO>> list(@PathVariable("gsmKey") Integer gsmKey, @PathVariable("houseId") Integer houseId){
		try {
			return new Result(service.getLogicSetting(gsmKey, houseId, null));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}


	@RequestMapping(value= "/{controlSettingId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ControlLogicSettingVO>> get(@PathVariable("controlSettingId") Integer controlSettingId){
		try {
			final List<ControlLogicSettingVO> logicSettingList = service.getLogicSetting(null, null, controlSettingId);
			if( logicSettingList == null || logicSettingList.size() ==0 ){
				return new Result(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND, null);
			}
			return new Result(logicSettingList.get(0));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}


	@RequestMapping(value= "", method = RequestMethod.POST)
	@ResponseBody
	public Result<List<DeviceEnvVO>> insert(@RequestBody ControlLogicSettingVO vo){
		try {
			return new Result(service.insertLogicSetting(vo));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, vo);
		}
	}

	@RequestMapping(value= "/{controlSettingId}", method = RequestMethod.PUT)
	@ResponseBody
	public Result<List<DeviceEnvVO>> update(@PathVariable("controlSettingId") Integer controlSettingId, @RequestBody ControlLogicSettingVO vo){
		try {
			return new Result(service.updateLogicSetting(vo));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, vo);
		}
	}

	@RequestMapping(value= "/{controlSettingId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result<Integer> delete(@PathVariable("controlSettingId") Integer controlSettingId){
		try {
			return new Result(service.delLogicSetting(controlSettingId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, controlSettingId);
		}
	}

//
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value= "/{controllerId}", method = RequestMethod.PUT)
//	@ResponseBody
//	public Result<DeviceEnvVO> update(@PathVariable("gsm_key") Integer gsmKey, @PathVariable("controllerId") String controllerId, @RequestBody List<DeviceEnvVO> device){
//		try {
//			return new Result(controlLogicEnvService.update(device)); // gsmKey, controllerId, deviceId 기준으로 업데이트
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, device);
//		}
//	}
//
//	/**
//	 * 제어로직 전체 list
//	 * @param gsmKey
//	 * @param controllerId
//	 * @return
//	 */
//	@RequestMapping(value= "/list", method = RequestMethod.GET)
//	@ResponseBody
//	public Result<HashMap<String,Object>> list(){
//		try {
//			return new Result(controlLogicEnvService.list());
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}
//
//	/**
//	 * @description 제어로직 프로퍼티 조회
//	 * @return
//	 */
//	@RequestMapping(value= "/properties", method = RequestMethod.GET)
//	@ResponseBody
//	public Result<HashMap<String,Object>> getLogicProperties(){
//		try {
//			return new Result(controlLogicEnvService.getLogicProperties());
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}
//
//
//
//
//	@RequestMapping(value= "/{house_id}", method = RequestMethod.GET)
//	@ResponseBody
//	public Result<DeviceEnvVO> get(@PathVariable("gsm_key") Integer gsmKey, @PathVariable("house_id") String houseId){
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
