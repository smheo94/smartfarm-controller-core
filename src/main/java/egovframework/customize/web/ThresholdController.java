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
import egovframework.customize.service.ThresholdVO;
import egovframework.customize.service.ProductVO;
import egovframework.customize.service.ThresholdService;
import egovframework.customize.service.ThresholdVO;
import egovframework.customize.service.DeviceTypeVO;
import egovframework.customize.service.HouseEnvService;

import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/threshold")
public class ThresholdController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "thresholdService")
	private ThresholdService thresholdService;
		
	/**
	 * @description 제어기 임계치 설정
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= "/", method = RequestMethod.PUT)
	@ResponseBody
	public Result<ThresholdVO> update(@RequestBody ThresholdVO thresholdVO){		
		try {
			return new Result(thresholdService.update(thresholdVO)); // gsmKey, id기준으로 업데이트
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, thresholdVO);
		}
	}
	  
	
	
	@RequestMapping(value= "/{gsm_key}", method = RequestMethod.GET)
	@ResponseBody
	public Result<ThresholdVO> list( @PathVariable("gsm_key") Integer gsmKey){
		try {
			HashMap<String,Object> param = new HashMap<String,Object>();
			param.put("gsmKey", gsmKey);
			return new Result(thresholdService.getThreshold(param));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	
}
