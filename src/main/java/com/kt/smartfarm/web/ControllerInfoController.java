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

import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.ControllerInfoService;
import com.kt.smartfarm.service.ControllerInfoVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * mgrEnv
 * 온실환경설정 및 제어환경설정
 * 센서구성,제어기구성,온실구성,제어로직구성,외부기상대구성,임계치구성
 */
@Controller
@RequestMapping(value="/controllerinfo")
public class ControllerInfoController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "controllerInfoService")
	private ControllerInfoService controllerInfoService;
		
	
	@RequestMapping(value= "/", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ControllerInfoVO>> list(HttpServletRequest request){
		try {
			return new Result(controllerInfoService.list());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/{controllerTypeId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<ControllerInfoVO> get(HttpServletRequest request,  @PathVariable("controllerTypeId") Integer controllerTypeId){
		try {
			return new Result(controllerInfoService.get(controllerTypeId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

}
