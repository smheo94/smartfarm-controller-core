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

import egovframework.cmmn.util.InterceptPost;
import egovframework.cmmn.util.InterceptPre;
import egovframework.cmmn.util.Result;
import egovframework.customize.service.ControllerEnvService;
import egovframework.customize.service.ControllerEnvVO;
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

/*
 * mgrEnv
 * 온실환경설정 및 제어환경설정
 * 센서구성,제어기구성,온실구성,제어로직구성,외부기상대구성,임계치구성
 */
@Controller
@RequestMapping(value="/{gsmKey}/controller")
public class ControllerEnvController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "controllerEnvService")
	private ControllerEnvService controllerEnvService;
		
	
	/**
	 * 제어모듈 수정
	 * @param gsmKey
	 * @param controllerId
	 * @param controller
	 * @return
	 */
	@RequestMapping(value= "/{controllerId}", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptPre
	public Result<ControllerEnvVO> update(@PathVariable("gsmKey") Integer gsmKey, @PathVariable("controllerId") String controllerId, @RequestBody ControllerEnvVO controller){
		try {
			
			return new Result(controllerEnvService.update(controller));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, controller);
		}
	}
	
	/**
	 * @description 제어모듈 등록
	 * @param gsmKey
	 * @param controller
	 * @return
	 */
	@RequestMapping(value= "/", method = RequestMethod.POST)
	@ResponseBody
	@InterceptPost
	public Result<ControllerEnvVO> insert( @PathVariable("gsmKey") Integer gsmKey, @RequestBody ControllerEnvVO controller){
		try {			
			return new Result(controllerEnvService.insert(controller));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, controller);
		}
	}
	
	/**
	 * @description 제어모듈 전체 리스트 
	 * @param gsmKey
	 * @return
	 */
	@RequestMapping(value= "/", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ControllerEnvVO>> list( @PathVariable("gsmKey") Integer gsmKey){
		try {
			return new Result(controllerEnvService.list(gsmKey));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * @description 제어모듈 전체 리스트 + 구동기 장치 join 
	 * @param gsmKey
	 * @return
	 */
	@RequestMapping(value= "/deviceList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ControllerEnvVO>> controllerDeviceList( @PathVariable("gsmKey") Integer gsmKey){
		try {
			return new Result(controllerEnvService.controllerDeviceList(gsmKey));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	
	
	/**
	 * @descriptiion 제어모듈의 정보
	 * @param gsmKey
	 * @param controllerId
	 * @return
	 */
	@RequestMapping(value= "/{controllerId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<ControllerEnvVO> get( @PathVariable("gsmKey") Integer gsmKey,  @PathVariable("controllerId") Integer controllerId){
		try {
			return new Result(controllerEnvService.get(gsmKey, controllerId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * 제어모듈 삭제
	 * @param gsmKey
	 * @param controllerId
	 * @return
	 */
	@RequestMapping(value= "/{controllerId}", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<ControllerEnvVO> delete(@PathVariable("gsmKey") Integer gsmKey,  @PathVariable("controllerId") Integer controllerId){
		try {
			return new Result(controllerEnvService.delete(gsmKey, controllerId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	
	/**
	 * 제어모듈 타입 목록
	 * @return
	 */
	@RequestMapping(value= "/listType", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<HashMap<String,Object>>> listType(){
		try {
			return new Result(controllerEnvService.listType());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
}
