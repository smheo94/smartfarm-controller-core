package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface HouseDiaryService {

//	HashMap<String, Object> selectAllCategory();

	
//	List<HashMap<String, Object>> getMonthlyDiary(Integer year, Integer month);
	HouseDiaryVO insertHouseDiary(HouseDiaryVO houseDiaryVO, MultipartFile[] file);
	HouseDiaryVO updateHouseDiary(HouseDiaryVO houseDiaryVO, MultipartFile[] file);
	
	HouseDiaryVO getHouseDiaryDetail(Integer id);
	List<HashMap<String,Object>> getMonthlyHouseDiaryList(Integer houseId, Integer year, Integer month);
	Integer deleteHouseDiary(Integer id);
	
	List<HashMap<String,Object>> getCategoryList();
	
	HouseCropsDiaryVO insertCropsDiary(HouseCropsDiaryVO houseCropsVO, MultipartFile[] file);
	HouseCropsDiaryVO updateCropsDiary(HouseCropsDiaryVO houseCropsVO, MultipartFile[] file);
	Integer DeleteCropsDiary(Integer id);
	List<HouseCropsDiaryVO> getMonthlyCropsDiaryList(Integer greenHouseId, Integer year, Integer month);
	HashMap<String,Object> getHouseCropsInfo(Integer greenHouseId);
}
