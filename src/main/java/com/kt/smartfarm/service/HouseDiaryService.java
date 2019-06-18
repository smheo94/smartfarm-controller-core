package com.kt.smartfarm.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface HouseDiaryService {

//	HashMap<String, Object> selectAllCategory();

	
//	List<HashMap<String, Object>> getMonthlyDiary(Integer year, Integer month);
	HouseDiaryVO insertHouseDiary(HouseDiaryVO houseDiaryVO);
	HouseDiaryVO updateHouseDiary(HouseDiaryVO houseDiaryVO);
	HouseCropsDiaryVO insertCropsDiary(HouseCropsDiaryVO houseCropsVO);
	HouseCropsDiaryVO updateCropsDiary(HouseCropsDiaryVO houseCropsVO);
	
	Object insertDiaryFile(String contentType, Integer id, MultipartFile[] file);
	Object updateDiaryFile(String contentType, Integer id, MultipartFile[] file);
	
	Integer deleteHouseDiary(Integer id);
	Integer deleteCropsDiary(Integer id);
	
	HashMap<String,Object> getMonthlyHouseDiaryList(Integer houseId, Integer year, Integer month);
//	List<HouseCropsDiaryVO> getMonthlyCropsDiaryList(Integer greenHouseId, Integer year, Integer month);
	
	HashMap<String,Object> getHouseDiaryDetail(Integer id);
	HashMap<String,Object> getCropsDiaryDetail(Integer id);
	
	List<HashMap<String,Object>> getCategoryList();
	
	
	HashMap<String,Object> getHouseCropsInfo(Integer greenHouseId);
	List<HashMap<String,Object>> getImageDiaryList(Integer houseId);
	
	
}
