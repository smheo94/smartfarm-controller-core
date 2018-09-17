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

import egovframework.customize.service.HouseEnvVO;
import egovframework.customize.service.ProductVO;
import egovframework.customize.service.HouseEnvService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kt.smartfarm.supervisor.mapper.HouseEnvMapper;


@Service("houseEnvService")
public class HouseEnvServiceImpl extends EgovAbstractServiceImpl implements HouseEnvService {

	@Autowired
	HouseEnvMapper houseEnvMapper;
	
	@Override
	public HouseEnvVO insert(HouseEnvVO vo) {				
		houseEnvMapper.insert(vo);
		return vo;
	}

	@Override
	public Integer delete(String gsmKey, Integer greenHouseId) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsm_key",  gsmKey);
		map.put("id",  greenHouseId);
		return houseEnvMapper.delete(map);
	}

	@Override
	public HouseEnvVO update(HouseEnvVO vo) {			
		houseEnvMapper.update(vo);
		return vo;
	}
/*
	@Override
	public HashMap<String,Object> get(String gsmKey, Integer greenHouseId) {
		
		HashMap<String,Object> result = new HashMap<String, Object>();
		List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> deviceList = new ArrayList<HashMap<String,Object>>();
		List<Integer> deviceIds = new ArrayList<Integer>();
		List<HashMap<String,Object>> controllerList = new ArrayList<HashMap<String,Object>>();
		Map<String, Object> map = new HashMap<>();
		
		map.put("gsm_key",  gsmKey);
		map.put("green_house_id",  greenHouseId);
		
		result = houseEnvMapper.getHouseDetail(map);		
		deviceList = houseEnvMapper.getMappedDevice(map);
		for(int i=0; i<deviceList.size();i++){
			deviceIds.add((Integer)deviceList.get(i).get("device_id"));
		}
		map.put("deviceIds", deviceIds);
		controllerList = houseEnvMapper.getMappedController(map);
		result.put("deviceList", deviceList);
		result.put("controllerList", controllerList);
		
		return result;
	}
*/
	@Override
	public List<HashMap<String,Object>> list(String gsmKey) {
		List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
		List<Integer> deviceIds = new ArrayList<Integer>();
		List<HashMap<String,Object>> controllerList = new ArrayList<HashMap<String,Object>>();
		Map<String, Object> map = new HashMap<>();
		
		map.put("gsm_key",  gsmKey);		
		result = houseEnvMapper.getHouseDetail(map);
		
		for(int j=0; j<result.size(); j++){
			map.put("green_house_id",result.get(j).get("id"));
			deviceIds = houseEnvMapper.getMappedDevice(map);
						
			map.put("deviceIds", deviceIds);
			controllerList = houseEnvMapper.getMappedController(map);
			result.get(j).put("deviceList", deviceIds);
			result.get(j).put("controllerList", controllerList);			
		}		
		
		return result;
	}

	@Override
	public List<HashMap<String, Object>> selectHouseTypeList() {		
		return houseEnvMapper.selectHouseTypeList();
	}

 
	@Override
	public List<ProductVO> selectProductList() {		
		List<ProductVO> productList = null;
		try{
			productList = houseEnvMapper.selectProductList();
			for(ProductVO product : productList){
				String species = product.getProductSpecies();
				HashMap<String,Object> param = new HashMap<>();
				param.put("species", species);
				List<HashMap<String,Object>> productMethodList = houseEnvMapper.selectProductMethod(param);
				product.setProductMethod(productMethodList);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public HashMap<String, Object> insertHouseDeviceMap(HashMap<String, Object> map) {
		HashMap<String,Object> param = new HashMap<>();
		Integer houseId = (Integer) map.get("houseId");
		List<Integer> deviceIds = (List<Integer>) map.get("deviceId");
		for(Integer deviceId : deviceIds){
			param.put("house_id", houseId);
			param.put("device_id", deviceId);
			houseEnvMapper.insertHouseDeviceMap(param);
		}		
		
		return map;
	}
}
