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

import java.util.HashMap;
import java.util.List;

import com.kt.cmmn.util.InterceptPost;
import com.kt.cmmn.util.InterceptPre;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.config.SmartfarmInterceptorConfig;
import com.kt.smartfarm.service.*;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Controller
@RequestMapping(value =  "/{gsm_key}/house" )
public class HouseEnvController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";

	@Resource(name = "houseEnvService")
	private HouseEnvService houseEnvService;
	@Resource(name="authCheckService")
	private AuthCheckService authCheckService;
	@Autowired
	SmartfarmInterceptorConfig config;
	/**
	 * @description 온실 등록 ( 제어기 선택은 어디서? 밑에서~ )
	 * @param request
	 * @param gsmKey
	 * @param device
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	@InterceptPost
	public Result<HouseEnvVO> insert(@PathVariable("gsm_key") Integer gsmKey, @RequestBody HouseEnvVO house) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(houseEnvService.insert(house));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, house);
		}
	}

	/**
	 * @description 온실 등록후 device 와 온실 link
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/linkDevice", method = RequestMethod.POST)
	@ResponseBody
	@InterceptPost
	public Result<HashMap<String, Object>> houesMapDeviceInsert(@PathVariable("gsm_key") Integer gsmKey, @RequestBody HashMap<String, Object> map) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(houseEnvService.insertHouseDeviceMap(map));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, map);
		}
	}

	/**
	 * @description 온실 등록후 device 와 온실 link
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/linkDevice", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<HashMap<String, Object>> houesMapDeviceUpdate(@PathVariable("gsm_key") Integer gsmKey, @RequestBody HashMap<String, Object> map) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(houseEnvService.deleteHouseDeviceMap(map));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, map);
		}
	}

	/**
	 * @description 온실 update
	 * @param gsmKey
	 * @param house
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptPre
	public Result<HouseEnvVO> update(@PathVariable("gsm_key") Integer gsmKey, @RequestBody HouseEnvVO house) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(houseEnvService.update(house)); // gsmKey, id기준으로 업데이트
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, house);
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<HashMap<String, Object>>> list(@PathVariable("gsm_key") Integer gsmKey) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(houseEnvService.list(gsmKey, true, true, config.isSmartfarmSystem()));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value = "/{greenHouseId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<HashMap<String, Object>> get(@PathVariable("gsm_key") Integer gsmKey, @PathVariable("greenHouseId") Integer greenHouseId) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result<HashMap<String, Object>>(houseEnvService.get(gsmKey, greenHouseId, config.isSmartfarmSystem()));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value = "/{greenHouseId}", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<HouseEnvVO> delete(@PathVariable("gsm_key") Integer gsmKey, @PathVariable("greenHouseId") Integer greenHouseId) {
		try {
			if( !authCheckService.authCheck(gsmKey, null) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
			}
			return new Result(houseEnvService.delete(gsmKey, greenHouseId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description houseType 리스트
	 * @return "type_name": "노지농가", "root_type": 1, "description": "노지농가", "id": 95,
	 *         // green_house_type_id "type_name_i18n":
	 *         "__common.green_house.house_type.openfield", "house_type": 4
	 */
	@RequestMapping(value = "/houseTypeList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<HashMap<String, Object>>> selectHouseTypeList() {
		try {
			return new Result(houseEnvService.selectHouseTypeList());
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 작물 정보
	 * @return
	 */
	@RequestMapping(value = "/productList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ProductVO>> selectProductList() {
		try {
			return new Result(houseEnvService.selectProductList());
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value = "/{houseId}/deviceList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<DeviceEnvVO>> houseDeviceList(@PathVariable("houseId") Integer houseId) {
		try {
			if( !authCheckService.authCheck(null, houseId) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
			}
			return new Result(houseEnvService.houseDeviceList(houseId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * 에버커스, 노루시스템즈를 위한 별도의 연동 api
	 * @description 온실에 추가되어 있는 장치 리스트와, 장치 타입
	 */
	@RequestMapping(value = "/{houseId}/deviceInfoList", method = RequestMethod.GET)
	@ResponseBody
	public Result<HashMap<String,Object>> houseDeviceInfoList(@PathVariable("houseId") Integer houseId) {
		try {
			if( !authCheckService.authCheck(null, houseId) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
			}
			return new Result(houseEnvService.houseDeviceInfoList(houseId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * cctv 등록, 수정, 조회, 삭제, 온실 삭제되면 cascade
	 * 
	 * @return
	 */

	@RequestMapping(value = "/weather_cast", method = RequestMethod.GET)
	@ResponseBody
	public Result weatherCast(@RequestParam(value = "house_id", required = false) Integer houseId, @RequestParam(value = "from_date", required = false) String fromDate,
			@RequestParam(value = "to_date", required = false) String toDate) {
		try {
			if( !authCheckService.authCheck(null, houseId) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
			}
			return new Result(houseEnvService.getWeatherCast(houseId, fromDate, toDate, config.isSmartfarmSystem()));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value = "/weather_category", method = RequestMethod.GET)
	@ResponseBody
	public Result weatherCategory() {
		try {
			return new Result(houseEnvService.getWeatherCategory());
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	
	@RequestMapping(value= "/{houseId}/groundDeviceList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<DeviceEnvVO>> groundDeviceList(@PathVariable("houseId") Integer houseId){
		try {
			if( !authCheckService.authCheck(null, houseId) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
			}
			return new Result(houseEnvService.groundDeviceList(houseId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	


	// CCTV ===========================================

	// CCTV INSERT
	@RequestMapping(value = "/cctv", method = RequestMethod.POST)
	@ResponseBody
	@InterceptPost
	public Result<CCTVSettingVO> insertCctv(@RequestBody CCTVSettingVO cctv) {
		try {
			return new Result(houseEnvService.insertCctv(cctv));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	// CCTV READ
	@RequestMapping(value = "/{houseId}/cctv", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<CCTVSettingVO>> insertCctv(@PathVariable Integer houseId) {
		try {
			if( !authCheckService.authCheck(null, houseId) ) {
				return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
			}
			return new Result(houseEnvService.getCctvsByHouseId(houseId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	// CCTV UPDATE
	@RequestMapping(value = "/cctv", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptPre
	public Result<CCTVSettingVO> updateCctv(@RequestBody CCTVSettingVO cctv) {
		try {
			return new Result(houseEnvService.updateCctv(cctv));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	// CCTV DELETE
	@RequestMapping(value = "/cctv/{cctvId}", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<HashMap<String, Object>> deleteCctv(@PathVariable("cctvId") Integer cctvId) {
		try {
			return new Result(houseEnvService.deleteCctv(cctvId));
		} catch (Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

}
