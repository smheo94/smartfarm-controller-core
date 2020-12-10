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
package com.kt.smartfarm.mapper;

import com.kt.smartfarm.service.HouseDiaryVO;
import com.kt.smartfarm.service.SweetContentVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;


@Mapper("houseDiaryMapper")
public interface HouseDiaryMapper {

	Integer insertHouseDiary(HouseDiaryVO houseDiaryVO);
	Integer insertHouseDiaryHouseMap(HouseDiaryVO houseDiaryVO);
	Integer deleteHouseDiaryHouseMap(HashMap<String,Object> param);
	Integer insertHouseDiaryFile(HouseDiaryVO houseDiaryVO);
	Integer updateHouseDiary(HouseDiaryVO houseDiaryVO);
	Integer deleteHouseDiary(HashMap<String,Object> param);
	Integer deleteHouseDiaryFile(HashMap<String,Object> param);
	
	HouseDiaryVO getHouseDiaryDetail(HashMap<String, Object> param);
	List<HashMap<String,Object>> getHouseDiaryFile(HashMap<String, Object> param);
//	HouseCropsDiaryVO getCropsDiaryDetail(HashMap<String, Object> param);
//	List<HashMap<String,Object>> getCropsDiaryFile(HashMap<String, Object> param);
	List<HashMap<String,Object>> getHouseDiaryList(HashMap<String, Object> param);

//	Integer insertCropsDiary(HouseCropsDiaryVO houseCropsVO);
//	Integer insertCropsDiaryHouseMap(HouseCropsDiaryVO houseDiaryVO);
//	Integer deleteCropsDiaryHouseMap(HashMap<String,Object> param);
//	Integer insertCropsDiaryFile(HouseCropsDiaryVO houseCropsVO);
//	void updateCropsDiary(HouseCropsDiaryVO houseCropsVO);
//	Integer deleteCropsDiary(HashMap<String, Object> param);
//	Integer deleteCropsDiaryFile(HashMap<String,Object> param);
	
//	List<HashMap<String,Object>> getCropsDiaryList(HashMap<String, Object> param);
	HashMap<String, Object> getHouseCropsInfo(HashMap<String, Object> param);
	
//	List<HashMap<String, Object>> getCategoryList22();
	
	List<HashMap<String, Object>> getImageDiaryList(HashMap<String,Object> param);

	Integer existsImageDiaryImageUrl(HouseDiaryVO houseDiaryVO);

	Integer updateImageDiary(HouseDiaryVO houseDiaryVO);

	List<SweetContentVO> getSweetContentLastList(@Param(value = "gsmKey") Long gsmKey,
												 @Param(value = "houseId") Long houseId,
												 @Param(value = "fromDate") String fromDate,
												 @Param(value = "toDate") String toDate);
}
