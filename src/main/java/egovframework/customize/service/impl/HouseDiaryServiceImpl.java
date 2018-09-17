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

import egovframework.cmmn.util.Result;
import egovframework.customize.service.CommonEnvService;
import egovframework.customize.service.CommonEnvVO;
import egovframework.customize.service.HouseCropsDiaryVO;
import egovframework.customize.service.HouseDiaryService;
import egovframework.customize.service.HouseDiaryVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.client.RestTemplate;

import com.kt.smartfarm.supervisor.mapper.HouseDiaryMapper;


@Service("houseDiaryService")
public class HouseDiaryServiceImpl extends EgovAbstractServiceImpl implements HouseDiaryService {
    private static final Logger log = LoggerFactory.getLogger(HouseDiaryServiceImpl.class);

    @Autowired
    private HouseDiaryMapper	houseDiaryMapper;

    
    /*
	@Override
	public HashMap<String, Object> selectAllCategory() {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		HashMap<String, ArrayList<String>> productMap = new HashMap<String, ArrayList<String>>();		
		List<HashMap<String, Object>> productList;
		try {
			productList = houseDiaryMapper.getProductList();
			for(HashMap<String, Object> product : productList) {
				
				String productName = (String)product.get("product");
	
				List<HashMap<String, Object>> productSpeciesAndCategoryList = houseDiaryMapper.getProductSpeciesAndCategory(productName);
				for(HashMap<String, Object> productSpeciesAndCategory : productSpeciesAndCategoryList) {
					String species = (String) productSpeciesAndCategory.get("product_species");
					String category = (String) productSpeciesAndCategory.get("product_category");
					
					ArrayList<String> categoryList = productMap.get(species);
					if(categoryList == null) {
						categoryList = new ArrayList<String>();
					}					
					categoryList.add(category);
					productMap.put(species, categoryList);
				}
			}			
			dataMap.put("product_map", productMap);
		} catch (Exception e) {
			log.error("[Exception]", e);
		}		
		return dataMap;
	}
*/

/*
	@Override
	public List<HashMap<String, Object>> getMonthlyDiary(String year, String month) {
		HashMap<String,Object> param = new HashMap<>();
		// 년/월 셋팅 + 1일로 셋팅		
		Calendar startDateCal = Calendar.getInstance();
		startDateCal.set(Calendar.YEAR, Integer.parseInt(year));
		startDateCal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		startDateCal.set(Calendar.DAY_OF_MONTH, 1);
		startDateCal.set(Calendar.HOUR_OF_DAY, 0);
		startDateCal.set(Calendar.MINUTE, 0);
		startDateCal.set(Calendar.SECOND, 0);
		startDateCal.set(Calendar.MILLISECOND, 0);
		
		// 종료날을 다음달 1일로 셋팅
		Calendar endDateCal = (Calendar)startDateCal.clone();
		endDateCal.add(Calendar.MONTH, 1);
		endDateCal.add(Calendar.MILLISECOND, -1);
		
		Date startDate = startDateCal.getTime();
		Date endDate = endDateCal.getTime();		
		param.put("start_date", startDate);
		param.put("end_date", endDate);
		try {			
			return houseDiaryMapper.selectDiaryMonthly(param);
		} catch (Exception e) {
			log.error("getMonthlyDiary", e);
			return null;
		}
	}
*/


	@Override
	public HouseDiaryVO insertHouseDiary(HouseDiaryVO houseDiaryVO) {
		houseDiaryMapper.insertHouseDiary(houseDiaryVO);
		return houseDiaryVO;
	}



	@Override
	public HouseDiaryVO getHouseDiaryDetail(Integer id) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("id", id);
		return houseDiaryMapper.getHouseDiaryDetail(param);
	}



	@Override
	public List<HouseDiaryVO> getMonthlyHouseDiaryList(Integer houseId,String year, String month) {
		HashMap<String,Object> param = new HashMap<>();
		param = getMonthDate(year,month);
		param.put("house_id", houseId);
		return houseDiaryMapper.getMonthlyHouseDiaryList(param);
	}

	@Override
	public List<HashMap<String, Object>> getCategoryList() {
		return houseDiaryMapper.getCategoryList22();		
	}
    
	public HashMap<String,Object> getMonthDate(String year,String month){
		HashMap<String,Object> result = new HashMap<>();
		Calendar startDateCal = Calendar.getInstance();
		startDateCal.set(Calendar.YEAR, Integer.parseInt(year));
		startDateCal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		startDateCal.set(Calendar.DAY_OF_MONTH, 1);
		startDateCal.set(Calendar.HOUR_OF_DAY, 0);
		startDateCal.set(Calendar.MINUTE, 0);
		startDateCal.set(Calendar.SECOND, 0);
		startDateCal.set(Calendar.MILLISECOND, 0);
		
		// 종료날을 다음달 1일로 셋팅
		Calendar endDateCal = (Calendar)startDateCal.clone();
		endDateCal.add(Calendar.MONTH, 1);
		endDateCal.add(Calendar.MILLISECOND, -1);
		
		Date startDate = startDateCal.getTime();
		Date endDate = endDateCal.getTime();		
		result.put("start_date", startDate);
		result.put("end_date", endDate);
		return result;
	}



	@Override
	public HouseDiaryVO updateHouseDiary(HouseDiaryVO houseDiaryVO) {
		houseDiaryMapper.updateHouseDiary(houseDiaryVO);
		return houseDiaryVO;
	}



	@Override
	public Integer deleteHouseDiary(Integer id) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("id", id);
		return houseDiaryMapper.delete(param);
	}



	
	
	
	
	@Override
	public HouseCropsDiaryVO insertCropsDiary(HouseCropsDiaryVO houseCropsVO) {
		houseDiaryMapper.insertCropsDiary(houseCropsVO);
		return houseCropsVO;
	}



	@Override
	public HouseCropsDiaryVO updateCropsDiary(HouseCropsDiaryVO houseCropsVO) {
		houseDiaryMapper.updateCropsDiary(houseCropsVO);
		return houseCropsVO;
	}



	@Override
	public Integer DeleteCropsDiary(Integer id) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("id", id);
		return houseDiaryMapper.DeleteCropsDiary(param);
	}



	@Override
	public List<HouseCropsDiaryVO> MonthlyCropsDiaryList(Integer greenHouseId, String year, String month) {
	 	HashMap<String,Object> param = new HashMap<>();
		param = getMonthDate(year,month);
		param.put("house_id", greenHouseId);
		return houseDiaryMapper.getMonthlyCropsDiaryList(param);
	}
   
    
}
