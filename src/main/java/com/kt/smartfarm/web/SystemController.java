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
import com.kt.smartfarm.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value="/system")
public class SystemController {
	@Resource(name = "systemService")
	private SystemService systemService;
		
	
	/**
	 * @description 앱 버전 정보 조회 ( 최신버전 )
	 * @param appName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= "/openapi/app/", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getLastVersion(@RequestParam("app_name") String appName){
		try {
			return new Result(systemService.getAppVersion(appName));
		} catch(Exception e) {
			return new Result( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}


	@RequestMapping(value= "/data/", method = RequestMethod.GET)
	@ResponseBody
	public Result<Map<String,Object>> getData(@RequestBody HashMap<String,Object> param){
		try {
			return new Result(systemService.getAnyQueryResult(param));
		} catch(Exception e) {
			return new Result( e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}
}
