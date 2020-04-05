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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kt.cmmn.util.*;
import com.kt.smartfarm.config.SmartfarmInterceptorConfig;
import com.kt.smartfarm.lamplog.LampLog;
import com.kt.smartfarm.service.LampLogService;
import com.kt.smartfarm.lamplog.models.LOG_SECURITY_EVENT;
import com.kt.smartfarm.lamplog.models.LOG_SECURITY_TYPE;
import com.kt.smartfarm.lamplog.models.LOG_TYPE;
import com.kt.smartfarm.service.*;
import com.kt.smartfarm.intercepter.SmartFarmDataInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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


	@Resource(name = "autoSyncService")
	private AutoSyncService autoSyncService;
		
	@Resource(name ="houseEnvService")
	private HouseEnvService houseEnvService;

	@Resource(name="authCheckService")
	private AuthCheckService authCheckService;

	@Autowired
	HttpServletRequest request;
	@Autowired
	SmartfarmInterceptorConfig config;

	@Autowired
	private LampLogService lampLogService;
	/**
	 * 제어모듈 수정
	 * @param gsmKey
	 * @param gsmInfo	 *
	 * @return
	 */
	@RequestMapping(value= "/{gsmKey}", method = RequestMethod.PUT)
	//@ApiOperation("제어기 정보 수정, OLD /")
	@ResponseBody
	@InterceptPre
	@InterceptLog
	public Result<GsmEnvVO> update(@RequestBody GsmEnvVO gsmInfo, @PathVariable("gsmKey") Long gsmKey){
		LampLog lampLog = lampLogService.createTransactionLog("gsm.update", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
		try {
			lampLog.addSecurity(LOG_SECURITY_TYPE.PRCS, LOG_SECURITY_EVENT.UPDATE, String.valueOf(gsmInfo.getGsmKey()));
			if( !authCheckService.authCheck(gsmKey, null, null, null) ) {
				lampLog.error(HttpStatus.FORBIDDEN.toString(), "Not Allowed");
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmInfo);
			}
			if(!gsmKey.equals(gsmInfo.getGsmKey())) {
				lampLog.error(HttpStatus.CONFLICT.toString(), "Unmatched GSM key");
				return new Result("Unmatched GSM key", HttpStatus.CONFLICT, gsmInfo);
			}
			gsmEnvService.update(gsmInfo);
			lampLog.success();
			return new Result(gsmInfo);
		} catch(Exception e) {
			lampLog.exception(e.getMessage());
			return new Result(e.getMessage(), HttpStatus.CONFLICT, gsmInfo);
		}
		finally {
			lampLogService.sendLog(lampLog);
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
	@InterceptLog
	@InterceptIgnoreGSMKey
	public Result<GsmEnvVO> insert(HttpServletRequest request,HttpServletResponse response, @RequestBody GsmEnvVO gsmInfo){
		LampLog lampLog = lampLogService.createTransactionLog("gsm.insert", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
		try {
			lampLog.addSecurity(LOG_SECURITY_TYPE.PRCS, LOG_SECURITY_EVENT.CREATE, String.valueOf(gsmInfo.getGsmKey()));
			if( !authCheckService.authCheck(gsmInfo.getGsmKey(), null, null, null) ) {
				lampLog.error(HttpStatus.FORBIDDEN.toString(), "Not Allowed");
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmInfo);
			}
			Map gi = gsmEnvService.get(gsmInfo.getGsmKey(), false, false);
			if ( gi != null && gi.get("gsmKey") != null ) {
				lampLog.error(HttpStatus.CONFLICT.toString(), "Duplicate entry");
				return new Result("Duplicate entry", HttpStatus.CONFLICT, gi);
			}
			Long result = gsmEnvService.insert(gsmInfo);
			response.setHeader(SmartFarmDataInterceptor.X_HEADER_GSM_KEY, gsmInfo.getGsmKey().toString());
			lampLog.success();
			return new Result(gsmInfo);
		} catch(Exception e) {
			lampLog.exception(e.getMessage());
			return new Result(e.getMessage(), HttpStatus.CONFLICT, gsmInfo);
		} finally {
			lampLogService.sendLog(lampLog);
		}
	}


	/**
	 * @description 제어모듈 등록
	 * @param fromGsmKey
	 * @param toGsmKey	 *
	 * @return
	 */
	@RequestMapping(value= "/{from_gsm_key}/copyTo/{to_gsm_key}", method = RequestMethod.POST)
	@ResponseBody
	@InterceptLog
	@InterceptPost
	@InterceptIgnoreGSMKey
	public Result<GsmEnvVO> copyTo(HttpServletRequest request,HttpServletResponse response, @PathVariable("from_gsm_key") Long fromGsmKey, @PathVariable("to_gsm_key") Long toGsmKey){
		LampLog lampLog = lampLogService.createTransactionLog("gsm.copy", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
		try {
			lampLog.addSecurity(LOG_SECURITY_TYPE.PRCS, LOG_SECURITY_EVENT.CREATE, String.valueOf(toGsmKey));
			if( !authCheckService.authCheck(null, null, Arrays.asList(fromGsmKey, toGsmKey), null) ) {
				lampLog.error(HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN.getReasonPhrase());
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, fromGsmKey);
			}
			lampLog.success();
			return new Result(gsmEnvService.copyToNewGsm(request, fromGsmKey, toGsmKey));
		} catch(Exception e) {
			lampLog.exception(e.getMessage());
			return new Result(e.getMessage(), HttpStatus.CONFLICT, Arrays.asList(fromGsmKey, toGsmKey));
		} finally{
			lampLogService.sendLog(lampLog);
		}
	}




	@RequestMapping(value= "/{gsm_key}/sync", method = RequestMethod.POST)
	@ResponseBody
	@InterceptLog
	public Result<GsmEnvVO> sync(HttpServletRequest request,HttpServletResponse response, @PathVariable("gsm_key") Long gsmKey){
		try {
			if( !authCheckService.authCheck(gsmKey, null, null, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(gsmEnvService.syncToSmartfarm(request, gsmKey));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, gsmKey);
		}
	}
	
	/**
	 * @description 제어기, 제어기에 해당하는 device 의 갯수 
	 * @param gsmKey
	 * @return
	 */
	@RequestMapping(value= "/{gsmKey}/device", method = RequestMethod.GET)
	//@ApiOperation("제어기 정보 수정, OLD /deviceInfo")
	@ResponseBody
	public Result<List<HashMap<String,Object>>> gsmOfDeviceList(@PathVariable("gsmKey") Long gsmKey){
		try {
			if( !authCheckService.authCheck(gsmKey, null, null, null) ) {
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
	public Result<GsmEnvVO> getAll( @PathVariable("gsmKey") Long gsmKey, @RequestParam(value = "all", required = false) Boolean all, Authentication authentication){
		try {
			if( !authCheckService.authCheck(gsmKey, null, null, null) ) {
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
	@InterceptLog
//	@InterceptPre
	public Result<String> delete(@PathVariable("gsmKey") Long gsmKey){
		LampLog lampLog = lampLogService.createTransactionLog("gsm.delete", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
		try {
			lampLog.addSecurity(LOG_SECURITY_TYPE.PRCS, LOG_SECURITY_EVENT.DELETE, String.valueOf(gsmKey));
			if( !authCheckService.authCheck(gsmKey, null, null, null) ) {
				lampLog.error(HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN.getReasonPhrase());
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			gsmEnvService.delete(gsmKey);
			lampLog.success();
			return new Result("OK",HttpStatus.OK,null);
		} catch(Exception e) {
			lampLog.exception(e.getMessage());
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		} finally {
			lampLogService.sendLog(lampLog);
		}
	}
	
	/**
	 * @description gsm_info, green_house mapping dataList
	 * @return
	 */
	@RequestMapping(value= "", method = RequestMethod.GET)
	//@ApiOperation("GSM List OLD (none) ")
	@ResponseBody
	@SuppressWarnings("PMD.AvoidReassigningParameters")
	public Result<List<HashMap<String,Object>>> list(@RequestParam(value = "all", required = false) Boolean all
			,@RequestParam(value = "userInfoId", required = false) Integer userInfoId
			,@RequestParam(value = "categoryId", required = false) Integer categoryId
			,@RequestParam(value = "farmName", required = false) String farmName
			,@RequestParam(value = "hasCCTV", required = false) Boolean hasCCTVOnly
			){
		try {
		    if( all == null ) {
                all = true;
            }
		    if( hasCCTVOnly == null ) {
		    	hasCCTVOnly = false;
			}
			String authUserIdx = authCheckService.getAuthUserIdx();
		    Boolean isSmartfarm = config.isSmartfarmSystem();
			return new Result(gsmEnvService.list(all,userInfoId,categoryId,farmName, authUserIdx, isSmartfarm, hasCCTVOnly));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * @description 농장주와 연결되지 않은 제어기 리스트.
	 * @return
	 */
	@RequestMapping(value= "/notMappedList", method = RequestMethod.GET)
	//@ApiOperation("농장주와 연결되지 않은 제어기 리스트.")
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
	@RequestMapping(value= "/{gsmKey}/autosync", method = RequestMethod.POST)
	//@ApiOperation("userInfoId로 제어기 등록.")
	@ResponseBody
	public Result autoSync(@RequestBody HashMap<String,Object> param, @PathVariable Long gsmKey){
		try {
			if( !authCheckService.authCheck(gsmKey, null, null, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(autoSyncService.autoSync(gsmKey, request));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description userInfoId로 제어기 등록
	 * @return
	 */
	@RequestMapping(value= "/{gsmKey}", method = RequestMethod.POST)
	//@ApiOperation("userInfoId로 제어기 등록.")
	@ResponseBody
	public Result userRegisterGSM(@RequestBody HashMap<String,Object> param, @PathVariable Long gsmKey){
		try {
			return new Result(gsmEnvService.userRegisterGSM(param,gsmKey));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value= "/threshold", method = RequestMethod.POST)	
	@ResponseBody
	@InterceptLog
	@InterceptPost
	public Result gsmThresholdInsert(@RequestBody GsmThresholdVO gsmThresholdVO){
		try {
			if( !authCheckService.authCheck(gsmThresholdVO.getGsmKey(), null, null, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmThresholdVO);
			}
			return new Result(gsmEnvService.gsmThresholdInsert(gsmThresholdVO));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}




	@RequestMapping(value= "/threshold", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptLog
	@InterceptPost
	public Result gsmThresholdUpdate(@RequestBody GsmThresholdVO gsmThresholdVO){
		try {
			if( !authCheckService.authCheck(gsmThresholdVO.getGsmKey(), null, null, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmThresholdVO);
			}
			return new Result(gsmEnvService.gsmThresholdUpdate(gsmThresholdVO));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/{gsmKey}/threshold", method = RequestMethod.GET)	
	@ResponseBody
	public Result gsmThresholdGet(@PathVariable Long gsmKey){
		try {
			if( !authCheckService.authCheck(gsmKey, null, null, null) ) {
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
