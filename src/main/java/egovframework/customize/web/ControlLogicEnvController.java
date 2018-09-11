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
import egovframework.customize.service.ControlLogicEnvService;
import egovframework.customize.service.ControlLogicVO;
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
@RequestMapping("/env/{gsm_key}/controlLogic")
public class ControlLogicEnvController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "controlLogicEnvService")
	private ControlLogicEnvService controlLogicEnvService;

	@RequestMapping(value= "/", method = RequestMethod.POST)
	@ResponseBody
	public Result<List<DeviceEnvVO>> insert( @PathVariable("gsm_key") String gsmKey, @RequestBody List<DeviceEnvVO> device){
		try {
			return new Result(controlLogicEnvService.insert(device));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, device);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value= "/{controllerId}", method = RequestMethod.PUT)
	@ResponseBody
	public Result<DeviceEnvVO> update(@PathVariable("gsm_key") String gsmKey, @PathVariable("controllerId") String controllerId, @RequestBody List<DeviceEnvVO> device){		
		try {
			return new Result(controlLogicEnvService.update(device)); // gsmKey, controllerId, deviceId 기준으로 업데이트
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, device);
		}
	}
	
	/**
	 * 제어로직 전체 list
	 * @param gsmKey
	 * @param controllerId
	 * @return
	 */
	@RequestMapping(value= "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result<HashMap<String,Object>> list(){
		try {
			return new Result(controlLogicEnvService.list());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/{house_id}", method = RequestMethod.GET)
	@ResponseBody
	public Result<DeviceEnvVO> get(@PathVariable("gsm_key") String gsmKey, @PathVariable("house_id") String houseId){
		try {
			return new Result(controlLogicEnvService.getRegList(gsmKey,houseId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value= "/{controllerId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result<DeviceEnvVO> delete(@PathVariable("gsm_key") String gsmKey,  @PathVariable("controllerId") Integer controllerId){
		try {
			return new Result(controlLogicEnvService.delete(gsmKey, controllerId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}


}
