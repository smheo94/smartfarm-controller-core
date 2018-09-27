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

import egovframework.customize.service.HouseCropsDiaryVO;
import egovframework.customize.service.HouseDiaryVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("houseDiaryMapper")
public interface HouseDiaryMapper {

	/*List<HashMap<String, Object>> getProductList();
	List<HashMap<String, Object>> getProductSpeciesAndCategory(String productName);*/
//	List<HashMap<String, Object>> selectDiaryMonthly(HashMap<String, Object> param);
	
	Integer insertHouseDiary(HouseDiaryVO houseDiaryVO);
	HouseDiaryVO getHouseDiaryDetail(HashMap<String, Object> param);
	List<HashMap<String, Object>> getCategoryList22();
	List<HouseDiaryVO> getMonthlyHouseDiaryList(HashMap<String, Object> param);
	Integer updateHouseDiary(HouseDiaryVO houseDiaryVO);
	Integer delete(HashMap<String,Object> param);
	
	void insertCropsDiary(HouseCropsDiaryVO houseCropsVO);
	void updateCropsDiary(HouseCropsDiaryVO houseCropsVO);
	Integer DeleteCropsDiary(HashMap<String, Object> param);
	List<HouseCropsDiaryVO> getMonthlyCropsDiaryList(HashMap<String, Object> param);
	HashMap<String, Object> getHouseCropsInfo(HashMap<String, Object> param);
	
	
	
	
}
