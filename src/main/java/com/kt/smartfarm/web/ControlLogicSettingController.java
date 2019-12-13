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

import com.kt.cmmn.util.InterceptPost;
import com.kt.cmmn.util.InterceptPre;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.AuthCheckService;
import com.kt.smartfarm.service.ControlLogicSettingService;
import com.kt.smartfarm.service.ControlLogicSettingVO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/control_logic_setting")
public class ControlLogicSettingController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";

	@Resource(name = "controlLogicSettingService")
	private ControlLogicSettingService service;

	@Resource(name="authCheckService")
	private AuthCheckService authCheckService;

	@RequestMapping(value = "/gsm/{gsmKey}/house/{houseId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ControlLogicSettingVO>> list(@PathVariable("gsmKey") Integer gsmKey, @PathVariable("houseId") Integer houseId, @RequestParam(required = false, name="logicId") List<Long> logicId) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(service.getLogicSetting(gsmKey, houseId, null, logicId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}





	@RequestMapping(value = "/gsm/{gsmKey}/house/{houseId}/settingId/{controlSettingId}/history", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ControlLogicSettingVO>> historyList(@PathVariable("gsmKey") Integer gsmKey, @PathVariable("houseId") Integer houseId,
														   @PathVariable("controlSettingId") Integer controlSettingId,
														   @RequestParam(required = false, name = "from_date") Long fromDate,
														   @RequestParam(required = false, name = "to_date") Long toDate) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(service.getControlLogicSettingHIstoryList(gsmKey, houseId, controlSettingId, fromDate, toDate));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

//	@RequestMapping(value = "/gsm/{gsmKey}/house/{houseId}/settingId/{controlSettingId}/history/logdt/{logdt}", method = RequestMethod.GET)
//	@ResponseBody
//	public Result<ControlLogicSettingVO> historyDetail(@PathVariable("gsmKey") Integer gsmKey, @PathVariable("houseId") Integer houseId,
//															 @PathVariable("controlSettingId") Integer controlSettingId, @PathVariable("logdt") Long logDt) {
//		try {
//			return new Result(service.getControlLogicSettingHIstoryDetail(gsmKey, houseId, controlSettingId, logDt));
//		} catch (Exception e) {
//			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
//		}
//	}

	@RequestMapping(value = "/{controlSettingId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ControlLogicSettingVO>> get(@PathVariable("controlSettingId") Integer controlSettingId) {
		try {
			final List<ControlLogicSettingVO> logicSettingList = service.getLogicSetting(null, null, controlSettingId);
			if (logicSettingList == null || logicSettingList.size() == 0) {
				return new Result(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND, null);
			}
			if( !authCheckService.authCheck(null, logicSettingList.get(0).getGreenHouseId()) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, controlSettingId);
			}
			return new Result(logicSettingList.get(0));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	@InterceptPost
	public Result<ControlLogicSettingVO> insert(@RequestBody ControlLogicSettingVO vo) {
		try {
			if( !authCheckService.authCheck(null, vo.getGreenHouseId()) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, vo);
			}
			return new Result(service.insertLogicSetting(vo));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, vo);
		}
	}

    @RequestMapping(value = "/new_list", method = RequestMethod.POST)
    @ResponseBody
    @InterceptPost
    public Result<List<ControlLogicSettingVO>> insert(@RequestBody List<ControlLogicSettingVO> voList) {
        try {
            if( voList.get(0) == null ) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, null);
            }
            List<ControlLogicSettingVO> resultList = new ArrayList<>();
            voList.stream().forEach( vo -> resultList.add(service.insertLogicSetting(vo)));
            return new Result(resultList);
        } catch (Exception e) {
            return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
        }

    }

    @RequestMapping(value = "/update_list", method = RequestMethod.PUT)
    @ResponseBody
    @InterceptPost
    public Result<List<ControlLogicSettingVO>> update(@RequestBody List<ControlLogicSettingVO> voList) {
        try {
            if( voList.get(0) == null ) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, null);
            }
            List<ControlLogicSettingVO> resultList = new ArrayList<>();
            voList.stream().forEach( vo -> resultList.add(service.updateLogicSetting(vo)));
            return new Result(resultList);
        } catch (Exception e) {
            return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
        }
    }

	@RequestMapping(value = "/{controlSettingId}", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptPost
	public Result<ControlLogicSettingVO> update(@PathVariable("controlSettingId") Integer controlSettingId, @RequestBody ControlLogicSettingVO vo) {
		try {
			if( !authCheckService.authCheck(null, vo.getGreenHouseId()) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, vo);
			}
			return new Result(service.updateLogicSetting(vo));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, vo);
		}
	}

	@RequestMapping(value = "/{controlSettingId}/env_upudate", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptPre
	public Result<String> updateEnv(@PathVariable("controlSettingId") Integer controlSettingId, @RequestBody Map<String, Object> param) {
		try {
			service.updateLogicSettingEnv(param);
			// TODO : JSON
			return new Result("Success");
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, param);
		}
	}

	@RequestMapping(value = "/{controlSettingId}/logic_env_update", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptPre
	public Result<String> updateLogicEnv(@PathVariable("controlSettingId") Integer controlSettingId, @RequestBody Map<String, Object> param) {
		try {
			//logic_env, period_env, update_dt, auto_manual_mode
			service.updateLogicEnv(param);
			return new Result("Success");
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, param);
		}
	}
	
	//
	// "UPDATE LicenseActivation"
	// + " SET \n"
	// + "license_prop_origin_text = JSON_REPLACE(license_prop_origin_text,
	// '$.numRobot', :numRobot\n" + ",'$.numScenario', :numScenario \n"
	// + ", '$.numScenarioStudio', :numScenarioStudio\n"
	// + ", '$.numTask', :numTask\n"
	// + ", '$.startDate', :startDate\n"
	// + ", '$.expireDate', :expireDate)\n"
	// + " WHERE license_id = :licenseId"

	@RequestMapping(value = "/{controlSettingId}", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<Integer> delete(@PathVariable("controlSettingId") Integer controlSettingId) {
		try {
			return new Result(service.delLogicSetting(controlSettingId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, controlSettingId);
		}
	}

	@RequestMapping(value = "/chkCondition/{chkConditionId}", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<Integer> deleteChkCondition(@PathVariable("chkConditionId") Integer chkConditionId) {
		try {
			return new Result(service.delChkConditionSetting(chkConditionId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, chkConditionId);
		}
	}

	@RequestMapping(value = "/device/{deviceId}", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPost
	public Result<Integer> deleteControlSettingDevice(@PathVariable("deviceId") Integer deviceId) {
		try {
			return new Result(service.deleteControlLogicSettingDevice(deviceId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, deviceId);
		}
	}

}
