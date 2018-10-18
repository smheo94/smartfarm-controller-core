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
import egovframework.cmmn.util.InterceptPre;
import egovframework.cmmn.util.InterceptPost;
import egovframework.customize.intercepter.SmartFarmDataInterceptor;
import egovframework.customize.service.GsmEnvVO;
import egovframework.customize.service.GsmEnvService;
import egovframework.customize.service.HouseEnvService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/*
 * mgrEnv
 * 온실환경설정 및 제어환경설정
 * 센서구성,제어기구성,온실구성,제어로직구성,외부기상대구성,임계치구성
 */
@Controller
@RequestMapping("/gsm")
public class GsmEnvController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";	
	
	@Resource(name = "gsmEnvService")
	private GsmEnvService gsmEnvService;
		
	@Resource(name ="houseEnvService")
	private HouseEnvService houseEnvService;
	/**
	 * 제어모듈 수정
	 * @param gsmKey
	 * @param gsmInfo	 *
	 * @return
	 */
	@RequestMapping(value= "/{gsmKey}", method = RequestMethod.PUT)
	@ApiOperation("제어기 정보 수정, OLD /")
	@ResponseBody
	@InterceptPre
	public Result<GsmEnvVO> update(@RequestBody GsmEnvVO gsmInfo, @PathVariable("gsmKey") Integer gsmKey){
		try {
			if( gsmKey != gsmInfo.getGsmKey()) {
				return new Result("Unmatched GSM key", HttpStatus.CONFLICT, gsmInfo);
			}
			gsmEnvService.update(gsmInfo);
			return new Result(gsmInfo);
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, gsmInfo);
		}
	}
	
	/**
	 * @description 제어모듈 등록
	 * @param gsmInfo
	 * @return
	 */
	@RequestMapping(value= "", method = RequestMethod.POST)
	@ResponseBody
	@InterceptPost
	public Result<GsmEnvVO> insert(HttpServletResponse response, @RequestBody GsmEnvVO gsmInfo){
		try {
			Integer result = gsmEnvService.insert(gsmInfo);
			response.setHeader(SmartFarmDataInterceptor.X_HEADER_GSM_KEY, gsmInfo.getGsmKey().toString());
			return new Result(gsmInfo);
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, gsmInfo);
		}
	}
	
	/**
	 * @description 제어기, 제어기에 해당하는 device 의 갯수 
	 * @param gsmKey
	 * @return
	 */
	@RequestMapping(value= "/{gsmKey}/device", method = RequestMethod.GET)
	@ApiOperation("제어기 정보 수정, OLD /deviceInfo")
	@ResponseBody
	public Result<List<HashMap<String,Object>>> gsmOfDeviceList(@PathVariable("gsmKey") Integer gsmKey){
		try {
			return new Result(gsmEnvService.gsmOfDeviceList(gsmKey));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @descriptiion 제어기 상세정보
	 * @param gsmKey
	 * @return
	 */
	@RequestMapping(value= "/{gsmKey}", method = RequestMethod.GET)
	@ResponseBody
	public Result<GsmEnvVO> getAll( @PathVariable("gsmKey") Integer gsmKey, @RequestParam(value = "all", required = false) Boolean all){
		try {
			if(all == null )
				all = true;
			return new Result(gsmEnvService.get(gsmKey, all));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * 제어모듈 삭제
	 * @param gsmKey
	 * @return
	 */
	@RequestMapping(value= "/{gsmKey}", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<String> delete(@PathVariable("gsmKey") Integer gsmKey){
		try {
			gsmEnvService.delete(gsmKey);
			return new Result("");
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * @description gsm_info, green_house mapping dataList
	 * @return
	 */
	@RequestMapping(value= "", method = RequestMethod.GET)
	@ApiOperation("GSM List OLD (none) ")
	@ResponseBody
	public Result<List<HashMap<String,Object>>> list(@RequestParam(value = "all", required = false) Boolean all){
		try {
		    if( all == null ) {
                all = true;
            }
			return new Result(gsmEnvService.list(all));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
}
