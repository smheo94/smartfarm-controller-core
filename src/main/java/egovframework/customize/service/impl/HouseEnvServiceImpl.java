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
import egovframework.customize.service.ControlLogicSettingVO;
import egovframework.customize.service.DeviceEnvVO;
import egovframework.customize.service.HouseEnvService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.smartfarm.supervisor.mapper.ControlLogicSettingMapper;
import com.kt.smartfarm.supervisor.mapper.DeviceEnvMapper;
import com.kt.smartfarm.supervisor.mapper.HouseEnvMapper;

import javax.annotation.Resource;


@Service("houseEnvService")
public class HouseEnvServiceImpl extends EgovAbstractServiceImpl implements HouseEnvService {

	@Resource(name="houseEnvMapper")
	HouseEnvMapper houseEnvMapper;
	
	//controllerId 로 device list 가져오는 mapper
	@Resource(name="deviceEnvMapper")
	DeviceEnvMapper deviceEnvMapper;
	
	@Resource(name="controlLogicSettingMapper")
	ControlLogicSettingMapper controlLogicMapper;
	
	@Override
	public HouseEnvVO insert(HouseEnvVO vo) {				
		houseEnvMapper.insert(vo);
		if(vo.getCctv() == null){
			return vo;	
		}else if(vo.getCctv() != null){
			for(int i=0; i<vo.getCctv().size();i++){
				vo.getCctv().get(i).setHouseId(vo.getId());
				houseEnvMapper.insertCCTVSetting(vo.getCctv().get(i));
			}
		}
		return vo;
	}

	@Override
	public Integer delete(Integer gsmKey, Integer greenHouseId) {
		Map<String, Object> map = new HashMap<>();
		map.put("gsmKey",  gsmKey);
		map.put("id",  greenHouseId);
		return houseEnvMapper.delete(map);
	}

	@Override
	public HouseEnvVO update(HouseEnvVO vo) {			
		houseEnvMapper.update(vo);
		return vo;
	}

	@Override
	public HashMap<String,Object> get(Integer gsmKey, Integer greenHouseId) {
		
		HashMap<String,Object> result = new HashMap<String, Object>();
		List<HashMap<String,Object>> houseDetail= new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> controllerList = new ArrayList<HashMap<String,Object>>();		
		List<HashMap<String,Object>> cctvList = new ArrayList<HashMap<String,Object>>();
		List<Integer> deviceIds = new ArrayList<Integer>();		
		Map<String, Object> map = new HashMap<>();
		
		map.put("gsm_key",  gsmKey);
		map.put("green_house_id",  greenHouseId);
		
		houseDetail = houseEnvMapper.getHouseDetail(map);
		for(int i=0; i<houseDetail.size();i++){
			Integer houseId = (Integer)houseDetail.get(i).get("id");
			cctvList = houseEnvMapper.getCctvList(houseId);
			if(cctvList != null){
				houseDetail.get(i).put("cctvList", cctvList);	
			}			
		}
		deviceIds = houseEnvMapper.getMappedDevice(map);
		
		map.put("deviceIds", deviceIds);
		controllerList = houseEnvMapper.getMappedController(map);
		List<ControlLogicSettingVO> logicList = controlLogicMapper.getControlLogicSetting(gsmKey, greenHouseId, null);
//		result.put("deviceList", deviceList);
		result.put("controllerList", controllerList);
		result.put("houseDetail", houseDetail);
		result.put("logicList", logicList);
		
		return result;
	}

	public List<HashMap<String, Object>> list(Integer gsmKey, boolean all, boolean detail) {
		List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> controllerList = new ArrayList<HashMap<String,Object>>();
//		List<HashMap<String,Object>> cctvList = new ArrayList<HashMap<String,Object>>();
		List<DeviceEnvVO> mappedDeviceList;
		List<Integer> deviceIds = new ArrayList<Integer>();
		Map<String, Object> map = new HashMap<>();
		HashMap<String,Object> sunriseInfo = new HashMap<>();
		List<HashMap<String,Object>> cctv = new ArrayList<>(); 
		if(gsmKey == null){
			result = houseEnvMapper.getHouseDetail(map);
			return result;
		}else{
			map.put("gsm_key",  gsmKey);
			result = houseEnvMapper.getHouseDetail(map);

			for(int i=0; i<result.size(); i++){
				final HashMap<String, Object> houseMap = result.get(i);
				map.put("green_house_id",houseMap.get("id"));
				deviceIds = houseEnvMapper.getMappedDevice(map);
				houseMap.put("selectedDeviceList", deviceIds);
				if( all ) {
					map.put("deviceIds", deviceIds);
					if( deviceIds.size() > 0 ) {
						controllerList = houseEnvMapper.getMappedController(map);
						for (int j = 0; j < controllerList.size(); j++) {
							HashMap<String, Object> param = new HashMap<>();
							param.put("controller_id", controllerList.get(j).get("id"));
							mappedDeviceList = deviceEnvMapper.list(param);
							controllerList.get(j).put("deviceList", mappedDeviceList);
						}
					}
					houseMap.put("controllerList", controllerList);
				}
				if(detail ) {
					Integer houseId = (Integer)houseMap.get("id");
					cctv = houseEnvMapper.getCctvList(houseId);
					if(cctv !=null){					
						houseMap.put("cctvList",cctv);	
					}
					sunriseInfo = houseEnvMapper.getSunriseInfo(map);
					if(sunriseInfo!=null){
						houseMap.put("sunriseInfo", sunriseInfo);
					}
				}
			}

			return result;
		}
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

	@Override
	public List<DeviceEnvVO> houseDeviceList(Integer houseId) {
		return houseEnvMapper.getHouseDeviceList(houseId);
		
	}

	@Override
	public List<HashMap<String,Object>> groundDeviceList(Integer houseId) {
		List<HashMap<String,Object>> tempDeviceList = new ArrayList<>();
		List<HashMap<String,Object>> deviceList = new ArrayList<>();
		tempDeviceList = houseEnvMapper.getGroundDeviceList(houseId);
		for(HashMap<String,Object> device : tempDeviceList){
			if(device.get("dtg").toString().equals("sensor_gr_temp") || device.get("dtg").toString().equals("sensor_gr_moisture")){
				String deviceTypeKey = (String)device.get("dt") + (String)device.get("device_type_idx");
				device.put("device_type_key", deviceTypeKey);
				deviceList.add(device);
			}			
		}
		return deviceList;
	}
	
	@Override
	public HashMap<String, Object> deleteHouseDeviceMap(HashMap<String, Object> map) {
		HashMap<String,Object> param = new HashMap<>();
		Integer houseId = (Integer) map.get("houseId");
		List<Integer> deviceIds = (List<Integer>) map.get("deviceId");
		for(Integer deviceId : deviceIds){
			param.put("house_id", houseId);
			param.put("device_id", deviceId);
			houseEnvMapper.deleteHouseDeviceMap(param);
		}
		return map;
	}

	@Override
	public List<HashMap<String, Object>> getAllList() {
		return houseEnvMapper.getAllList();
	}

	@Override
	public Integer insertForecastData(LinkedHashMap<String, Object> hm) {
		return houseEnvMapper.insertForecastData(hm);
		
	}

	@Override
	public List<HashMap<String, Object>> getWeatherCast(Integer houseId, String fromDate, String toDate) {
		HashMap<String,Object> param = new HashMap<>();
		try{			
			HashMap<String,Object> houseInfo = new HashMap<>();
			List<HashMap<String,Object>> houseDetail = new ArrayList<>();
			HashMap<String,Object> gridXY = new HashMap<>();
			houseInfo = get(null, houseId);
			houseDetail = (List<HashMap<String, Object>>) houseInfo.get("houseDetail");
			Double longitude = Double.parseDouble(houseDetail.get(0).get("latitude").toString());
			Double latitude = Double.parseDouble(houseDetail.get(0).get("longitude").toString());
			
			
			latitude = Math.ceil(latitude);
			longitude = Math.ceil(longitude);		
			gridXY= getGridxy(latitude,longitude);
			param.put("house_id", houseId);
			param.put("from_date", fromDate);
			param.put("to_date", toDate);
			param.put("nx", gridXY.get("x").toString());
			param.put("ny", gridXY.get("y").toString());
		}catch(Exception e){
			e.printStackTrace();
		}		 
		return houseEnvMapper.getWeatherCast(param);
	}
	
	private HashMap<String, Object> getGridxy(double latitude, double longitude) {
        double re1 = 6371.00877; // 지구 반경(km)
        double grid = 5.0; // 격자 간격(am)
        double slatA = 30.0; // 투영 위도1(degree)
        double slatB = 60.0; // 투영 위도2(degree)
        double olonD = 126.0; // 기준점 경도(degree)
        double olatD = 38.0; // 기준점 위도(degree)
        double xo = 43; // 기준점 X좌표(GRID)
        double yo = 136; // 기1준점 Y좌표(GRID)
        double degrad = Math.PI / 180.0;
        // double RADDEG = 180.0 / Math.PI;
 
        double re = re1 / grid;
        double slat1 = slatA * degrad;
        double slat2 = slatB * degrad;
        double olon = olonD * degrad;
        double olat = olatD * degrad;        
 
        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        HashMap<String, Object> map = new HashMap<String, Object>();
       
        double ra = Math.tan(Math.PI * 0.25 + (latitude) * degrad * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = longitude * degrad - olon;
        if (theta > Math.PI){
            theta -= 2.0 * Math.PI;
        }
        if (theta < -Math.PI){
            theta += 2.0 * Math.PI;
        }
        theta *= sn;
        map.put("lat", latitude);
       map.put("lng", longitude);
        map.put("x", (int)Math.floor(ra * Math.sin(theta) + xo + 0.5));
        map.put("y", (int)Math.floor(ro - ra * Math.cos(theta) + yo + 0.5));
 
        return map;
    }

	@Override
	public List<HashMap<String, Object>> getWeatherCategory() {
		return houseEnvMapper.getWeatherCategory();
	}

	@Override
	public Integer insertSunriseData(HashMap<String, Object> hm) {
		return houseEnvMapper.insertSunriseData(hm);		
	}
}
