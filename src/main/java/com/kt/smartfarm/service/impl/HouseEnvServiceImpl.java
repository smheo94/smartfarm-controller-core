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
package com.kt.smartfarm.service.impl;

import com.kt.cmmn.util.ClassUtil;
import com.kt.cmmn.util.SunriseSunset;
import com.kt.cmmn.util.WeatherCastGPSUtil;
import com.kt.smartfarm.service.*;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kt.smartfarm.mapper.ControlLogicSettingMapper;
import com.kt.smartfarm.mapper.DeviceEnvMapper;
import com.kt.smartfarm.mapper.HouseEnvMapper;

import javax.annotation.Resource;


@Service("houseEnvService")
public class HouseEnvServiceImpl extends EgovAbstractServiceImpl implements HouseEnvService {
	private static final Logger log = LoggerFactory.getLogger(HouseEnvServiceImpl.class);

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
	public Integer delete(Long gsmKey, Long greenHouseId) {
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
	public HashMap<String,Object> get(Long gsmKey, Long greenHouseId, Boolean isSmartfarmSystem) {
		HashMap<String,Object> result = new HashMap<String, Object>();
        List<HashMap<String, Object>> houseDetail = null;
        List<HashMap<String, Object>> controllerList = null;

        HashMap<String, Object> sunriseInfo = null;
        List<Long> deviceIds = null;
		Map<String, Object> map = new HashMap<>();
		
		map.put("gsm_key",  gsmKey);
		map.put("green_house_id",  greenHouseId);
		
		houseDetail = houseEnvMapper.getHouseDetail(map);

		for(int i=0; i<houseDetail.size();i++){
			List<CCTVSettingVO> cctvList = new ArrayList<>();
			Map<String,Object> houseDetailMap = houseDetail.get(i);
			Long houseId = ClassUtil.castToSomething(houseDetailMap.get("id"), Long.class);
			if( isSmartfarmSystem == false) {
				cctvList = getCCTVList(houseId);
			}
			houseDetailMap.put("cctvList", cctvList);
			Double latitude = (Double)houseDetailMap.get("latitude");
			Double longitude = (Double)houseDetailMap.get("longitude");
			SunriseSunset sunriseSunset =  SunriseSunset.SUNRIZESUNSET;
			if( latitude != null && longitude != null) {
				sunriseSunset = new SunriseSunset(latitude, longitude);

			}
			houseDetailMap.put("sunriseInfo", sunriseSunset.getSunriseSunSetMap());
//			sunriseInfo = houseEnvMapper.getSunriseInfo(map);
//			if(sunriseInfo!=null){
//				houseDetailMap.put("sunriseInfo", sunriseInfo);
//			}
		}
		deviceIds = houseEnvMapper.getMappedDevice(map);
		
		map.put("deviceIds", deviceIds);
		controllerList = houseEnvMapper.getMappedController(map);
		List<HashMap<String,Object>> nutrientControllerList  = houseEnvMapper.getNutrientController(map);
		if(  nutrientControllerList != null && nutrientControllerList.size() > 0 ) {
			controllerList.addAll(nutrientControllerList);
		}
		List<ControlLogicSettingVO> logicList = controlLogicMapper.getControlLogicSetting(gsmKey, greenHouseId, null, null);
//		result.put("deviceList", deviceList);
		result.put("controllerList", controllerList);
		result.put("houseDetail", houseDetail);
		result.put("logicList", logicList);
		
		return result;
	}
	@Override
	public Integer updateMiniVmsHash(List<CCTVMiniVMSVO> miniVmsList) {
		miniVmsList.stream().forEach(v -> houseEnvMapper.updateMiniVmsHash(v));
		return 0;
	}

	public List<CCTVSettingVO> getCCTVList(Long houseId) {
		List<CCTVSettingVO> cctvList = houseEnvMapper.getCctvList(houseId);
		if ( cctvList != null && cctvList.size() > 0 ) {
			cctvList.forEach( c -> {
				c.setCctvUid("****");
				c.setCctvPwd("****");
			});
		}
		return cctvList;
	}
	public List<HashMap<String, Object>> list(Long gsmKey, boolean all, boolean detail, Boolean isSmartfarmSystem) {
		return list(gsmKey, all, detail, isSmartfarmSystem, false);
	}
	public List<HashMap<String, Object>> list(Long gsmKey, boolean all, boolean detail, Boolean isSmartfarmSystem, Boolean isCCTVOnly) {
		List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
		Map<String, Object> map = new HashMap<>();
		if(gsmKey == null){
			result = houseEnvMapper.getHouseDetail(map);
		}else{
			map.put("gsm_key",  gsmKey);
			result = houseEnvMapper.getHouseDetail(map);

			for(int i=0; i<result.size(); i++){
				List<HashMap<String,Object>> controllerList = new ArrayList<HashMap<String,Object>>();
				List<CCTVSettingVO> cctv = new ArrayList<>();
				List<DeviceEnvVO> mappedDeviceList = new ArrayList<>();
				final HashMap<String, Object> houseMap = result.get(i);
				map.put("green_house_id",houseMap.get("id"));
                List<Long> deviceIds = houseEnvMapper.getMappedDevice(map);
				houseMap.put("selectedDeviceList", deviceIds);
				List<DeviceEnvVO> devices = deviceEnvMapper.list(map);
				houseMap.put("deviceList", devices);
				Long houseId = ClassUtil.castToSomething(houseMap.get("id"), Long.class);
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
					List<HashMap<String,Object>> nutrientControllerList  = houseEnvMapper.getNutrientController(map);
					if(  nutrientControllerList != null && nutrientControllerList.size() > 0 ) {
						nutrientControllerList.forEach( c -> c.put("deviceList", new ArrayList()));
						controllerList.addAll(nutrientControllerList);
					}
					houseMap.put("controllerList", controllerList);

					List<ControlLogicSettingVO> logicList = houseEnvMapper.getHouseControlSettings(ClassUtil.castToSomething(houseMap.get("id"), Long.class));
					houseMap.put("logicList", logicList);

					List<HouseProductVO> productList = houseEnvMapper.listHouseProduct(gsmKey, houseId, null);
					houseMap.put("productList", productList);
				}

				if(detail ) {
					Double latitude = (Double)houseMap.get("latitude");
					Double longitude = (Double)houseMap.get("longitude");
					SunriseSunset sunriseSunset =  SunriseSunset.SUNRIZESUNSET;
					if( latitude != null && longitude != null) {
						sunriseSunset = new SunriseSunset(latitude, longitude);
					}
					houseMap.put("sunriseInfo", sunriseSunset.getSunriseSunSetMap());
				}
				if( isCCTVOnly || detail ) {
					if( isSmartfarmSystem == false) {
						cctv = getCCTVList(houseId);
					}
					houseMap.put("cctvList",cctv);
				}
			}
			if( isCCTVOnly && result != null && result.size() > 0 ) {
			return result.stream().filter( h ->  { List cctvList =  (List)h.get("cctvList"); return cctvList != null && cctvList.size() > 0; })
					.collect(Collectors.toList());
			}
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
			log.debug(e.getMessage());
		}
		return productList;
	}

	@Override
	public HashMap<String, Object> insertHouseDeviceMap(HashMap<String, Object> map) {
		HashMap<String,Object> param = new HashMap<>();
		Long houseId = ClassUtil.castToSomething(map.get("houseId"), Long.class);
		List deviceIds = (List) map.get("deviceId");
		for(Object deviceId : deviceIds){
			param.put("house_id", houseId);
			param.put("device_id", ClassUtil.castToSomething(deviceId, Long.class));
			houseEnvMapper.insertHouseDeviceMap(param);
		}		
		
		return map;
	}

	@Override
	public List<DeviceEnvVO> houseDeviceList(Long houseId) {
		return houseEnvMapper.getHouseDeviceList(houseId);		
	}

	@Override
	public HashMap<String,Object> houseDeviceInfoList(Long houseId) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("deviceList", houseEnvMapper.getHouseDeviceList(houseId));
		result.put("deviceTypeList", houseEnvMapper.getHouseDeviceTypeList(houseId));
		return result;	
		
	}

    @Override
    public Integer copyToNewGSM(Long fromGsmKey, Long toGsmKey) {
        houseEnvMapper.copyToNewGSM(fromGsmKey, toGsmKey);
        houseEnvMapper.copyToNewGSMHouseProduct(fromGsmKey, toGsmKey);
        return houseEnvMapper.copyToNewGSMMapGreenHouseDevice(fromGsmKey, toGsmKey);
    }

	@Override
	public List<HouseProductVO> listHouseProduct(Long greenHouseId) {
		return houseEnvMapper.listHouseProduct(null, greenHouseId , null);
	}

	@Override
	public List<HouseProductVO> insertHouseProducts(List<HouseProductVO> listHouseProduct) {
		listHouseProduct.stream().forEach( h -> houseEnvMapper.insertHouseProduct(h));
		return listHouseProduct;
	}

	@Override
	public HouseProductVO insertHouseProduct(HouseProductVO houseProduct) {
		houseEnvMapper.insertHouseProduct(houseProduct);
		return houseProduct;
	}

	@Override
	public HouseProductVO updateHouseProduct(HouseProductVO houseProduct) {
		houseEnvMapper.updateHouseProduct(houseProduct);
		return houseProduct;
	}

	@Override
	public Integer deleteHouseProduct(Long gsmKey, Long houseId, Long houseProductId) {
		return houseEnvMapper.deleteHouseProduct(gsmKey, houseId, houseProductId);
	}

	@Override
	public List<HashMap<String,Object>> groundDeviceList(Long houseId) {
		List<HashMap<String,Object>> tempDeviceList = new ArrayList<>();
		List<HashMap<String,Object>> deviceList = new ArrayList<>();
		tempDeviceList = houseEnvMapper.getGroundDeviceList(houseId);
		for(HashMap<String,Object> device : tempDeviceList){
			if(device.get("dtg").toString().equals("sensor_gr_temp") || device.get("dtg").toString().equals("sensor_gr_moisture")){
				String deviceTypeKey = device.get("dt").toString() + device.get("device_type_idx").toString();
				device.put("device_type_key", deviceTypeKey);
				deviceList.add(device);
			}			
		}
		return deviceList;
	}
	
	@Override
	public HashMap<String, Object> deleteHouseDeviceMap(HashMap<String, Object> map) {
		HashMap<String,Object> param = new HashMap<>();
		Long houseId = ClassUtil.castToSomething(map.get("houseId"), Long.class);
		List deviceIds = (List) map.get("deviceId");
		for(Object deviceId : deviceIds){
			param.put("house_id", houseId);
			param.put("device_id", ClassUtil.castToSomething(deviceId, Long.class));
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
	public Integer insertUltraShortWeather(LinkedHashMap<String, Object> hm){
		return houseEnvMapper.insertUltraShortWeatherData(hm);
	}

	@Override
	public List<HashMap<String, Object>> getWeatherCast(Long houseId, String fromDate, String toDate, Boolean isSmartfarmSystem) {
		HashMap<String,Object> param = new HashMap<>();
		try{			
			HashMap<String,Object> houseInfo = new HashMap<>();
			List<HashMap<String,Object>> houseDetail = new ArrayList<>();
			HashMap<String,Object> gridXY = new HashMap<>();
			houseInfo = get(null, houseId, isSmartfarmSystem);
			houseDetail = (List<HashMap<String, Object>>) houseInfo.get("houseDetail");
			Double longitude = Double.parseDouble(houseDetail.get(0).get("latitude").toString());
			Double latitude = Double.parseDouble(houseDetail.get(0).get("longitude").toString());
			
			
			latitude = Math.ceil(latitude);
			longitude = Math.ceil(longitude);		
			gridXY= WeatherCastGPSUtil.getGridxy(latitude,longitude);
			param.put("house_id", houseId);
			param.put("from_date", fromDate);
			param.put("to_date", toDate);
			param.put("nx", gridXY.get("x").toString());
			param.put("ny", gridXY.get("y").toString());
		}catch(Exception e){
			log.debug(e.getMessage());
		}		 
		return houseEnvMapper.getWeatherCast(param);
	}

	@Override
	public List<HashMap<String, Object>> getWeatherCategory() {
		return houseEnvMapper.getWeatherCategory();
	}

	@Override
	public Integer insertSunriseData(HashMap<String, Object> hm) {
		return houseEnvMapper.insertSunriseData(hm);		
	}
	
	@Override
	public Integer insertCctv(CCTVSettingVO cctv) {
		return houseEnvMapper.insertCCTVSetting(cctv);
	}

	@Override
	public List<CCTVSettingVO> getCCTVListByHouseId(Long houseId) {
		return getCCTVList(houseId);
	}

	@Override
	public Integer deleteCctv(Integer id) {
		return houseEnvMapper.deleteCctv(id);
	}

	@Override
	public Integer updateCctv(CCTVSettingVO cctv) {
		if ( cctv.getCctvUid() != null && "****".equals(cctv.getCctvUid()) )
			cctv.setCctvUid(null);
		if ( cctv.getCctvPwd() != null && "****".equals(cctv.getCctvPwd()) )
			cctv.setCctvPwd(null);
		return houseEnvMapper.updateCctv(cctv);
	}
}
