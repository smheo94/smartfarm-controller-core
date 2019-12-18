/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
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
package com.kt.smartfarm.supervisor.mapper;

import java.util.HashMap;
import java.util.List;

import com.kt.smartfarm.service.HouseCropsDiaryVO;
import com.kt.smartfarm.service.HouseDiaryVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper("houseDiaryMapper")
public interface HouseDiaryMapper {

	/*List<HashMap<String, Object>> getProductList();
	List<HashMap<String, Object>> getProductSpeciesAndCategory(String productName);*/
//	List<HashMap<String, Object>> selectDiaryMonthly(HashMap<String, Object> param);
	
	Integer insertHouseDiary(HouseDiaryVO houseDiaryVO);
	Integer insertHouseDiaryHouseMap(HouseDiaryVO houseDiaryVO);
	Integer deleteHouseDiaryHouseMap(HashMap<String,Object> param);
	Integer insertHouseDiaryFile(HouseDiaryVO houseDiaryVO);
//	HouseDiaryVO selectHouseDiaryFile(HouseDiaryVO houseDiaryVO);
	Integer updateHouseDiary(HouseDiaryVO houseDiaryVO);
//	Integer updateHouseDiaryFile(HouseDiaryVO houseDiaryVO);
	Integer deleteHouseDiary(HashMap<String,Object> param);
	Integer deleteHouseDiaryFile(HashMap<String,Object> param);
	
	HouseDiaryVO getHouseDiaryDetail(HashMap<String, Object> param);
	List<HashMap<String,Object>> getHouseDiaryFile(HashMap<String, Object> param);


	HouseCropsDiaryVO getCropsDiaryDetail(HashMap<String, Object> param);
	List<HashMap<String,Object>> getCropsDiaryFile(HashMap<String, Object> param);
	
	List<HashMap<String,Object>> getMonthlyHouseDiaryList(HashMap<String, Object> param);


	Integer insertCropsDiary(HouseCropsDiaryVO houseCropsVO);
	Integer insertCropsDiaryHouseMap(HouseCropsDiaryVO houseDiaryVO);
	Integer deleteCropsDiaryHouseMap(HashMap<String,Object> param);
	Integer insertCropsDiaryFile(HouseCropsDiaryVO houseCropsVO);
//	HouseCropsDiaryVO selectCropsDiaryFile(HouseCropsDiaryVO houseDiaryVO);
	void updateCropsDiary(HouseCropsDiaryVO houseCropsVO);
//	Integer updateCropsDiaryFile(HouseCropsDiaryVO houseCropsVO);
	Integer deleteCropsDiary(HashMap<String, Object> param);
	Integer deleteCropsDiaryFile(HashMap<String,Object> param);
	
	List<HashMap<String,Object>> getMonthlyCropsDiaryList(HashMap<String, Object> param);
	HashMap<String, Object> getHouseCropsInfo(HashMap<String, Object> param);
	
	List<HashMap<String, Object>> getCategoryList22();
	
	List<HashMap<String, Object>> getImageDiaryList(HashMap<String,Object> param);
	
	
		
}
