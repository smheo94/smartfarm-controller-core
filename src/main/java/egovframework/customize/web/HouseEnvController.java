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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import egovframework.cmmn.util.DateUtil;
import egovframework.cmmn.util.InterceptPost;
import egovframework.cmmn.util.InterceptPre;
import egovframework.cmmn.util.Result;
import egovframework.customize.service.DeviceEnvVO;
import egovframework.customize.service.HouseEnvVO;
import egovframework.customize.service.ProductVO;
import egovframework.customize.service.HouseEnvService;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/env/{gsm_key}/house","/{gsm_key}"})
public class HouseEnvController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "houseEnvService")
	private HouseEnvService houseEnvService;
		
	
	/**
	 * @description 온실 등록 ( 제어기 선택은 어디서? 밑에서~ )
	 * @param request
	 * @param gsmKey
	 * @param device
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= "", method = RequestMethod.POST)
	@ResponseBody
	@InterceptPost
	public Result<HouseEnvVO> insert( @PathVariable("gsm_key") Integer gsmKey, @RequestBody HouseEnvVO house){
		try {
			return new Result(houseEnvService.insert(house));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, house);
		}
	}
	
	/**
	 * @description 온실 등록후 device 와 온실 link
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= "/linkDevice", method = RequestMethod.POST)
	@ResponseBody
	@InterceptPost
	public Result<HashMap<String,Object>> houesMapDeviceInsert( @PathVariable("gsm_key") Integer gsmKey, @RequestBody HashMap<String,Object> map){
		try {
			return new Result(houseEnvService.insertHouseDeviceMap(map));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, map);
		}
	}
	
	/**
	 * @description 온실 등록후 device 와 온실 link
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= "/linkDevice", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<HashMap<String,Object>> houesMapDeviceUpdate( @PathVariable("gsm_key") Integer gsmKey, @RequestBody HashMap<String,Object> map){
		try {
			return new Result(houseEnvService.deleteHouseDeviceMap(map));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, map);
		}
	}
	
	
	/**
	 * @description 온실 update
	 * @param gsmKey
	 * @param house
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= "", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptPre
	public Result<HouseEnvVO> update(@PathVariable("gsm_key") Integer gsmKey, @RequestBody HouseEnvVO house){
		try {
			return new Result(houseEnvService.update(house)); // gsmKey, id기준으로 업데이트
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, house);
		}
	}
	  
	
	
	@RequestMapping(value= "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<HashMap<String,Object>>> list( @PathVariable("gsm_key") Integer gsmKey){
		try {
			return new Result(houseEnvService.list(gsmKey, true, true));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	@RequestMapping(value= "/{greenHouseId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<HashMap<String,Object>> get( @PathVariable("gsm_key") Integer gsmKey,  @PathVariable("greenHouseId") Integer greenHouseId){
		try {
			return new Result<HashMap<String,Object>>(houseEnvService.get(gsmKey, greenHouseId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/{greenHouseId}", method = RequestMethod.DELETE)
	@ResponseBody
	@InterceptPre
	public Result<HouseEnvVO> delete(@PathVariable("gsm_key") Integer gsmKey,  @PathVariable("greenHouseId") Integer greenHouseId){
		try {
			return new Result(houseEnvService.delete(gsmKey, greenHouseId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	
	/**
	 * @description houseType 리스트
	 * @return
	 	"type_name": "노지농가",
        "root_type": 1,
        "description": "노지농가",
        "id": 95,  // green_house_type_id
        "type_name_i18n": "__common.green_house.house_type.openfield",
        "house_type": 4
	 */
	@RequestMapping(value= "/houseTypeList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<HashMap<String,Object>>> selectHouseTypeList(){
		try {
			return new Result(houseEnvService.selectHouseTypeList());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 작물 정보
	 * @return
	 */
	@RequestMapping(value= "/productList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ProductVO>> selectProductList(){
		try {			
			return new Result(houseEnvService.selectProductList());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	@RequestMapping(value= "/{houseId}/deviceList", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<DeviceEnvVO>> houseDeviceList(@PathVariable("houseId") Integer houseId){
		try {			
			return new Result(houseEnvService.houseDeviceList(houseId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	/**
	 * cctv 등록, 수정, 조회, 삭제, 온실 삭제되면 cascade
	 * @return 
	 */
	
	
	@RequestMapping(value = "/weather_cast", method = RequestMethod.GET)
	@ResponseBody
	public Result weatherCast(@RequestParam(value = "house_id", required = false) Integer houseId,
			@RequestParam(value = "from_date", required = false) String fromDate,
			@RequestParam(value = "to_date", required = false) String toDate) {
		try {
			return new Result(houseEnvService.getWeatherCast(houseId,fromDate,toDate));
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
	
}
