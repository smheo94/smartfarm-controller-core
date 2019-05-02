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

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import com.kt.smartfarm.supervisor.mapper.AuthCheckMapper;
import egovframework.cmmn.SystemType;
import egovframework.cmmn.util.Result;
import egovframework.cmmn.util.InterceptPre;
import egovframework.cmmn.util.InterceptIgnoreGSMKey;
import egovframework.cmmn.util.InterceptPost;
import egovframework.customize.config.SmartfarmInterceptorConfig;
import egovframework.customize.intercepter.SmartFarmDataInterceptor;
import egovframework.customize.service.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/*
 * mgrEnv
 * 온실환경설정 및 제어환경설정
 * 센서구성,제어기구성,온실구성,제어로직구성,외부기상대구성,임계치구성
 */
@Controller
@RequestMapping(value="/gsm")
public class GsmEnvController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";	
	
	@Resource(name = "gsmEnvService")
	private GsmEnvService gsmEnvService;
		
	@Resource(name ="houseEnvService")
	private HouseEnvService houseEnvService;

	@Resource(name="authCheckService")
	private AuthCheckService authCheckService;

	@Autowired
	SmartfarmInterceptorConfig config;
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
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmInfo);
			}
			if(!gsmKey.equals(gsmInfo.getGsmKey())) {
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
	@InterceptIgnoreGSMKey
	public Result<GsmEnvVO> insert(HttpServletRequest request,HttpServletResponse response, @RequestBody GsmEnvVO gsmInfo){
		try {
			if( !authCheckService.authCheck(gsmInfo.getGsmKey(), null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmInfo);
			}
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
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
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
	@SuppressWarnings("PMD.AvoidReassigningParameters")
	@RequestMapping(value= "/{gsmKey}", method = RequestMethod.GET)
	@ResponseBody
	public Result<GsmEnvVO> getAll( @PathVariable("gsmKey") Integer gsmKey, @RequestParam(value = "all", required = false) Boolean all, Authentication authentication){
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			// 일출, 일몰 api 같이
			if(all == null )
				all = true;
			return new Result(gsmEnvService.get(gsmKey, all, config.isSmartfarmSystem()));
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
//	@InterceptPre
	public Result<String> delete(@PathVariable("gsmKey") Integer gsmKey){
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			gsmEnvService.delete(gsmKey);
			return new Result("OK",HttpStatus.OK,null);
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
	@SuppressWarnings("PMD.AvoidReassigningParameters")
	public Result<List<HashMap<String,Object>>> list(@RequestParam(value = "all", required = false) Boolean all
			,@RequestParam(value = "userInfoId", required = false) Integer userInfoId
			,@RequestParam(value = "categoryId", required = false) Integer categoryId
			,@RequestParam(value = "farmName", required = false) String farmName
			){
		try {
		    if( all == null ) {
                all = true;
            }
			String authUserIdx = authCheckService.getAuthUserIdx();
		    Boolean isSmartfarm = config.isSmartfarmSystem();
			return new Result(gsmEnvService.list(all,userInfoId,categoryId,farmName, authUserIdx, isSmartfarm));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * @description 농장주와 연결되지 않은 제어기 리스트.
	 * @return
	 */
	@RequestMapping(value= "/notMappedList", method = RequestMethod.GET)
	@ApiOperation("농장주와 연결되지 않은 제어기 리스트.")
	@ResponseBody
	public Result<List<GsmEnvVO>> notMappedList(){
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			return new Result<List<GsmEnvVO>>(gsmEnvService.notMappedList());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	// 
	// userInfoId로 제어기 조회
	// userInfoId랑 매핑 끊기
	
	/**
	 * @description userInfoId로 제어기 등록
	 * @return
	 */
	@RequestMapping(value= "/{gsmKey}", method = RequestMethod.POST)
	@ApiOperation("userInfoId로 제어기 등록.")
	@ResponseBody
	public Result userRegistGSM(@RequestBody HashMap<String,Object> param, @PathVariable Integer gsmKey){
		try {
			return new Result(gsmEnvService.userRegistGSM(param,gsmKey));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
//	
//	@RequestMapping(value= "/user/{userInfoId}", method = RequestMethod.GET)
//	@ApiOperation("userInfoId로 제어기 조회.")
//	@ResponseBody
//	public Result getGsmInfoByUser(@PathVariable("userInfoId") Integer userInfoId){
//		try {		    
//			return new Result<List<GsmEnvVO>>(gsmEnvService.getGsmInfoByUser(userInfoId));
//		} catch(Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}
//	
	
	@RequestMapping(value= "/threshold", method = RequestMethod.POST)	
	@ResponseBody
	public Result gsmThresholdInsert(@RequestBody GsmThresholdVO gsmThresholdVO){
		try {
			if( !authCheckService.authCheck(gsmThresholdVO.getGsmKey(), null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmThresholdVO);
			}
			return new Result(gsmEnvService.gsmThresholdInsert(gsmThresholdVO));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value= "/threshold", method = RequestMethod.PUT)	
	@ResponseBody
	public Result gsmThresholdUpdate(@RequestBody GsmThresholdVO gsmThresholdVO){
		try {
			if( !authCheckService.authCheck(gsmThresholdVO.getGsmKey(), null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmThresholdVO);
			}
			return new Result(gsmEnvService.gsmThresholdUpdate(gsmThresholdVO));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/{gsmKey}/threshold", method = RequestMethod.GET)	
	@ResponseBody
	public Result gsmThresholdGet(@PathVariable Integer gsmKey){
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			HashMap<String,Object> param = new HashMap<String, Object>();
			param.put("gsmKey", gsmKey);
			return new Result(gsmEnvService.gsmThresholdGet(param));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
}
