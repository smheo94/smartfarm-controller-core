/*
 * Copyright 2008-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.customize.service.impl;

import egovframework.customize.service.GsmEnvService;
import egovframework.customize.service.GsmEnvVO;
import egovframework.customize.service.GsmThresholdVO;
import egovframework.customize.service.HouseEnvService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kt.smartfarm.supervisor.mapper.GsmEnvMapper;

import javax.annotation.Resource;


@Service("gsmEnvService")
public class GsmEnvServiceImpl extends EgovAbstractServiceImpl implements GsmEnvService {


	@Resource(name="gsmEnvMapper")
	GsmEnvMapper gsmEnvMapper;

	@Resource(name="houseEnvService")
	HouseEnvService houseEnvService;

	@Override
	public List<Map<String, Object>> gsmOfDeviceList(Integer gsmKey) {
		return gsmEnvMapper.gsmOfDeviceList(gsmKey);
	}

//	@Override
//	public GsmEnvVO get(Integer gsmKey) {
//		return gsmEnvMapper.get(gsmKey);
//	}

	@Override
	public Map<String, Object> get(Integer gsmKey, boolean all) {
		Map<String,Object> gsmMap = gsmEnvMapper.getGsmList(gsmKey,null,null,null).stream().findFirst().orElse(null);
		List<HashMap<String, Object>> houseList = houseEnvService.list(gsmKey, all, true);
		gsmMap.put("houseList", houseList);
		return gsmMap;
	}

	@Override
	public Integer delete(Integer gsmKey) {
		return gsmEnvMapper.delete(gsmKey);
	}

	@Override
	public Integer insert(GsmEnvVO gsmInfo) {
		return gsmEnvMapper.insert(gsmInfo);
	}

	@Override
	public Integer update(GsmEnvVO gsmInfo) {
		return gsmEnvMapper.update(gsmInfo);
	}
/*
	@Override
	public List<HashMap<String, Object>> list(HouseEnvService houseEnvService) {
		List<HashMap<String,Object>> gsmList = new ArrayList<HashMap<String,Object>>();
		gsmList = gsmEnvMapper.getGsmList();
		for(HashMap<String,Object> gsm : gsmList){
			List<HashMap<String,Object>> houseList = new ArrayList<HashMap<String,Object>>();
//			Integer gsmKey = (Integer)gsm.get("gsmKey");
			Integer gsmKey = (String)gsm.get("gsmKey");
			HouseEnvService.
			houseList = gsmEnvMapper.getHouseList(gsmKey);
			gsm.put("houseList", houseList);			
		}
		return gsmList;
	}
*/
	@Override
	public List<Map<String, Object>> list(boolean all,Integer userInfoId, Integer categoryId, String farmName) {
		List<Map<String,Object>> gsmList = new ArrayList<>();
		gsmList = gsmEnvMapper.getGsmList(null,userInfoId,categoryId,farmName);
		for(Map<String,Object> gsm : gsmList){
//			Integer gsmKey = (Integer)gsm.get("gsmKey");
//			houseList = gsmEnvMapper.getHouseList(gsmKey);
			Integer gsmKey = (Integer)gsm.get("gsmKey");
			List<HashMap<String,Object>> houseList = houseEnvService.list(gsmKey, all, false);
			gsm.put("houseList", houseList);			
		}
		return gsmList;
	}

	@Override
	public List<GsmEnvVO> notMappedList() {
		return gsmEnvMapper.notMappedList();		
	}

	
	@Override
	public Integer userRegistGSM(HashMap<String,Object> param, Integer gsmKey) {
		param.put("gsmKey", gsmKey);
		return gsmEnvMapper.userRegistGSM(param);		
	}

	@Override
	public List<GsmEnvVO> getGsmInfoByUser(Integer userInfoId) {
		return gsmEnvMapper.getGsmInfoByUser(userInfoId);
	}

	@Override
	public GsmThresholdVO gsmThresholdInsert(GsmThresholdVO gsmThresholdVO) {
		return gsmEnvMapper.gsmThresholdInsert(gsmThresholdVO);
	}

	@Override
	public GsmThresholdVO gsmThresholdUpdate(GsmThresholdVO gsmThresholdVO) {
		return gsmEnvMapper.gsmThresholdUpdate(gsmThresholdVO);
	}

	@Override
	public GsmThresholdVO gsmThresholdGet(HashMap<String, Object> param) {
		return gsmEnvMapper.gsmThresholdGet(param);
	}
  
}
