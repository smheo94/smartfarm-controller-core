package com.kt.smartfarm.service;

import java.util.HashMap;
import java.util.List;

import com.kt.smartfarm.supervisor.model.PushHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface HouseDiaryService {

//	HashMap<String, Object> selectAllCategory();

	
//	List<HashMap<String, Object>> getMonthlyDiary(Integer year, Integer month);
	HouseDiaryVO insertHouseDiary(HouseDiaryVO houseDiaryVO);
	HouseDiaryVO updateHouseDiary(HouseDiaryVO houseDiaryVO);
	HouseCropsDiaryVO insertCropsDiary(HouseCropsDiaryVO houseCropsVO);
	HouseCropsDiaryVO updateCropsDiary(HouseCropsDiaryVO houseCropsVO);

	Integer deleteDiaryFileList(String contentType, Integer id, List<Integer> fileIdxList);

	Object insertDiaryFile(String contentType, Integer id, MultipartFile[] file);
//	Object updateDiaryFile(String contentType, Integer id, MultipartFile[] file);
	
	Integer deleteHouseDiary(Integer id);
	Integer deleteCropsDiary(Integer id);
	
	HashMap<String,Object> getMonthlyHouseDiaryList(List<Integer> gsmKeyList, Integer gsmKey, Integer houseId, Integer year, Integer month);
	
	HouseDiaryVO getHouseDiaryDetail(Integer id);
	HouseCropsDiaryVO getCropsDiaryDetail(Integer id);
	
	List<HashMap<String,Object>> getCategoryList();
	
	
	HashMap<String,Object> getHouseCropsInfo(Integer greenHouseId);
	List<HashMap<String,Object>> getImageDiaryList(Integer houseId);


	Page<PushHistory> getImageDiaryListV2(Integer gsmKey, Integer houseId, Long fromDate, Long toDate, Pageable pageable);
}
