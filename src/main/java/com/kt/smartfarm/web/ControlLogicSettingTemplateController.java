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

import com.kt.cmmn.util.InterceptLog;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.AuthCheckService;
import com.kt.smartfarm.service.ControlLogicSettingTemplateService;
import com.kt.smartfarm.service.ControlLogicSettingTemplateVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/control_logic_setting/template")
public class ControlLogicSettingTemplateController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";

	@Resource(name = "controlLogicSettingTemplateService")
	private ControlLogicSettingTemplateService service;

	@Resource(name="authCheckService")
	private AuthCheckService authCheckService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ControlLogicSettingTemplateVO>> list(
			@RequestParam(required = false, name="relativeLevel") List<ControlLogicSettingTemplateVO.RelativeLevel> relativeLevel,
			@RequestParam(required = false, name="growStage") String growStage,
			@RequestParam(required = false, name="userId") String userId,
			@RequestParam(required = false, name="houseId") Long houseId,
			@RequestParam(required = false, name="logicId") List<Long> logicId,
			@RequestParam(required = false, name="prdCode") List<String> prdCode) {
		try {
			//@RequestParam(required = false, name="publishLevel") List<ControlLogicTemplatePublishLevel> publishLevel,
			List<ControlLogicSettingTemplateVO.PublishLevel> publishLevel = new ArrayList<>();
			if( authCheckService.getAuthCheck().isAdmin() ) {
				//TODO : ADMIN 은 다 볼수 있어요
				publishLevel.add(ControlLogicSettingTemplateVO.PublishLevel.OPEN);
				publishLevel.add(ControlLogicSettingTemplateVO.PublishLevel.OWNER);
				publishLevel.add(ControlLogicSettingTemplateVO.PublishLevel.INTERNAL);
			} else {
				publishLevel.add(ControlLogicSettingTemplateVO.PublishLevel.OPEN);
				publishLevel.add(ControlLogicSettingTemplateVO.PublishLevel.OWNER);
			}
			return new Result(service.getLogicSettingTemplate(relativeLevel, publishLevel, userId, houseId, prdCode, growStage, logicId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	@InterceptLog
	public Result<ControlLogicSettingTemplateVO> insert(@RequestBody ControlLogicSettingTemplateVO vo) {
		try {
			if(vo.getControlSettingTemplateId() != null && vo.getControlSettingTemplateId() != 0 ) {
				final ControlLogicSettingTemplateVO logicSettingTemplate = service.getLogicSettingTemplate(vo.getControlSettingTemplateId() );
				if (logicSettingTemplate == null ) {
					return new Result(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND, null);
				}
			} else {
				if (!authCheckService.authCheck(null, vo.getGreenHouseId(), null, null)) {
					return new Result("Not Allowed", HttpStatus.FORBIDDEN, vo);
				}
			}
			return new Result(service.insertLogicSettingTemplate(vo));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, vo);
		}
	}

	@RequestMapping(value = "/{controlSettingTemplateId}", method = RequestMethod.GET)
	@ResponseBody
	@InterceptLog
	public Result<List<ControlLogicSettingTemplateVO>> get(@PathVariable("controlSettingTemplateId") Long controlSettingTemplateId) {
		try {
			final ControlLogicSettingTemplateVO logicSettingTemplate = service.getLogicSettingTemplate(controlSettingTemplateId);
			if (logicSettingTemplate == null ) {
				return new Result(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND, null);
			}
			//TODO : 권한에 따른 다른 사람껀 못가져가야할 듯
//			if( !authCheckService.authCheck(null, logicSettingTemplate.get(0).getGreenHouseId()) ) {
//				return new Result("Not Allowed", HttpStatus.FORBIDDEN, controlSettingId);
//			}
			return new Result(logicSettingTemplate);
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value = "/{controlSettingTemplateId}", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptLog
	public Result<ControlLogicSettingTemplateVO> update(@PathVariable("controlSettingTemplateId") Long controlSettingTemplateId, @RequestBody ControlLogicSettingTemplateVO vo) {
		try {
			final ControlLogicSettingTemplateVO logicSettingTemplate = service.getLogicSettingTemplate(controlSettingTemplateId);
			if( !authCheckService.authCheck(null, logicSettingTemplate.getGreenHouseId(), null, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, vo);
			}
			return new Result(service.updateLogicSettingTemplate(vo));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, vo);
		}
	}


	@RequestMapping(value = "/{controlSettingTemplateId}", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptLog
	public Result<ControlLogicSettingTemplateVO> delete(@PathVariable("controlSettingTemplateId") Long controlSettingTemplateId) {
		try {
			final ControlLogicSettingTemplateVO logicSettingTemplate = service.getLogicSettingTemplate(controlSettingTemplateId);
			if( !authCheckService.authCheck(null, logicSettingTemplate.getGreenHouseId(), null, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, controlSettingTemplateId);
			}
			service.delLogicSettingTemplate(controlSettingTemplateId);
			return new Result(logicSettingTemplate);
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, controlSettingTemplateId);
		}
	}


//

//
//	@RequestMapping(value = "/{controlSettingId}", method = RequestMethod.PUT)
//	@ResponseBody
//	@InterceptPost
//	public Result<ControlLogicSettingTemplateVO> update(@PathVariable("controlSettingId") Long controlSettingId, @RequestBody ControlLogicSettingTemplateVO vo) {
//		try {
//			if( !authCheckService.authCheck(null, vo.getGreenHouseId()) ) {
//				return new Result("Not Allowed", HttpStatus.FORBIDDEN, vo);
//			}
//			return new Result(service.updateLogicSetting(vo));
//		} catch (Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, vo);
//		}
//	}
//
//	@RequestMapping(value = "/{controlSettingId}/env_upudate", method = RequestMethod.PUT)
//	@ResponseBody
//	@InterceptPre
//	public Result<String> updateEnv(@PathVariable("controlSettingId") Long controlSettingId, @RequestBody Map<String, Object> param) {
//		try {
//			service.updateLogicSettingEnv(param);
//			// TODO : JSON
//			return new Result("Success");
//		} catch (Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, param);
//		}
//	}
//
//	@RequestMapping(value = "/{controlSettingId}/logic_env_update", method = RequestMethod.PUT)
//	@ResponseBody
//	@InterceptPre
//	public Result<String> updateLogicEnv(@PathVariable("controlSettingId") Long controlSettingId, @RequestBody Map<String, Object> param) {
//		try {
//			//logic_env, period_env, update_dt, auto_manual_mode
//			service.updateLogicEnv(param);
//			return new Result("Success");
//		} catch (Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, param);
//		}
//	}
//
//	@RequestMapping(value = "/{controlSettingId}", method = RequestMethod.DELETE)
//	@ResponseBody
//	@InterceptPre
//	public Result<Integer> delete(@PathVariable("controlSettingId") Long controlSettingId) {
//		try {
//			return new Result(service.delLogicSetting(controlSettingId));
//		} catch (Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, controlSettingId);
//		}
//	}



}
