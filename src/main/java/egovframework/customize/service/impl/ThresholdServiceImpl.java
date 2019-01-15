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

import egovframework.customize.service.ThresholdService;
import egovframework.customize.service.ThresholdVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kt.smartfarm.supervisor.mapper.ThresholdMapper;


@Service("thresholdService")
public class ThresholdServiceImpl extends EgovAbstractServiceImpl implements ThresholdService {
    private static final Logger log = LoggerFactory.getLogger(ThresholdServiceImpl.class);

    @Resource(name="thresholdMapper")
    private ThresholdMapper	thresholdMapper;
	
/*
	@Override
	public ThresholdVO getThresholdDefault(ThresholdVO vo) {
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String,Object> mapResult = new HashMap<String,Object>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(EgovSampleController.DEFAULT_SETUP_FILE_PATH + "outweather.json");
		} catch (FileNotFoundException e) {					
			log.error("[Exception]", e);
		}		
		mapResult = defaultToMap(objectMapper, fis);
		vo.setInsideHighHum(Double.parseDouble(mapResult.get("inside_high_hum").toString()));
		vo.setInsideLowHum(Double.parseDouble(mapResult.get("inside_low_hum").toString()));
		vo.setInsideHighTemp(Double.parseDouble(mapResult.get("inside_high_temp").toString()));
		vo.setInsideLowTemp(Double.parseDouble(mapResult.get("inside_low_temp").toString()));
		vo.setInsideHighHumUse(Integer.parseInt(mapResult.get("inside_high_hum_use").toString()));
		vo.setInsideLowHumUse(Integer.parseInt(mapResult.get("inside_low_hum_use").toString()));
		vo.setInsideHighTempUse(Integer.parseInt(mapResult.get("inside_high_temp_use").toString()));
		vo.setInsideLowTempUse(Integer.parseInt(mapResult.get("inside_low_temp_use").toString()));
		
		vo.setOutsideHighHum(Double.parseDouble(mapResult.get("outside_high_hum").toString()));
		vo.setOutsideLowHum(Double.parseDouble(mapResult.get("outside_low_hum").toString()));
		vo.setOutsideHighTemp(Double.parseDouble(mapResult.get("outside_high_temp").toString()));
		vo.setOutsideLowTemp(Double.parseDouble(mapResult.get("outside_low_temp").toString()));
		vo.setOutsideHighHumUse(Integer.parseInt(mapResult.get("outside_high_hum_use").toString()));
		vo.setOutsideLowHumUse(Integer.parseInt(mapResult.get("outside_low_hum_use").toString()));
		vo.setOutsideHighTempUse(Integer.parseInt(mapResult.get("outside_high_temp_use").toString()));
		vo.setOutsideLowTempUse(Integer.parseInt(mapResult.get("outside_low_temp_use").toString()));
		
		fis = null;
		return vo;		
	}
	*/
/*
	@Override
	public void updateThresholdDefault(ThresholdVO vo) {
		ObjectMapper objectMapper = new ObjectMapper();
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(EgovSampleController.DEFAULT_SETUP_FILE_PATH + "outweather.json");
		} catch (FileNotFoundException e) {
			log.error("outWeatherHandle", e);
		}
		HashMap<String,Object> data = objectMapper.convertValue(vo, HashMap.class);
		if(data!=null){
			try {
				objectMapper.writeValue(fos, data);
			} catch (JsonGenerationException e) {
				log.error("outWeatherHandle", e);			
			} catch (JsonMappingException e) {						
				log.error("outWeatherHandle", e);			
			} catch (IOException e) {						
				log.error("outWeatherHandle", e);			
			}
		}		
		
		try {
			if( fos != null ) {
				fos.close();
			}
		} catch (IOException e) {
			log.error("outWeatherHandle", e);
		}
		fos = null;
	}
	
	private HashMap<String,Object> defaultToMap(ObjectMapper objectMapper, FileInputStream fis) {
		HashMap<String, Object> result = new HashMap<String,Object>();
		try {
			result = objectMapper.readValue(fis, HashMap.class);
		} catch (JsonParseException e) {
			log.error("[Exception]", e);
		} catch (JsonMappingException e) {
			log.error("[Exception]", e);
		} catch (IOException e) {
			log.error("[Exception]", e);
		}
		
		try {
			if(fis != null ) {
				fis.close();
			}
		} catch (IOException e) {
			log.error("[Exception]", e);
		}
		return result;
	}
*/
	@Override
	public List<ThresholdVO> insert(List<ThresholdVO> thresholdVO) {
		thresholdMapper.insert(thresholdVO);
		return thresholdVO;
	}
//
//	@Override
//	public ThresholdVO update(ThresholdVO thresholdVO) {
//		thresholdMapper.update(thresholdVO);
//		return thresholdVO;
//				
//	}

	@Override
	public List<ThresholdVO> getThreshold(Integer gsmKey, Integer greenHouseId) {
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("gsm_key", gsmKey);
		param.put("green_house_id", greenHouseId);
		return thresholdMapper.getThreshold(param);
	}
	
	@Override
	public int delete(Integer gsmKey, Integer greenHouseId) {
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("gsm_key", gsmKey);
		param.put("green_house_id", greenHouseId);
		return thresholdMapper.delete(param);
	}

}
