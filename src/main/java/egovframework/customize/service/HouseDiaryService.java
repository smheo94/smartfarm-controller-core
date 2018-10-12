package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface HouseDiaryService {

//	HashMap<String, Object> selectAllCategory();

	
//	List<HashMap<String, Object>> getMonthlyDiary(Integer year, Integer month);
	HouseDiaryVO insertHouseDiary(HouseDiaryVO houseDiaryVO);
	HouseDiaryVO getHouseDiaryDetail(Integer id);
	List<HouseDiaryVO> getMonthlyHouseDiaryList(Integer houseId, Integer year, Integer month);
	List<HashMap<String,Object>> getCategoryList();
	HouseDiaryVO updateHouseDiary(HouseDiaryVO houseDiaryVO);
	Integer deleteHouseDiary(Integer id);
	
	HouseCropsDiaryVO insertCropsDiary(HouseCropsDiaryVO houseCropsVO);
	HouseCropsDiaryVO updateCropsDiary(HouseCropsDiaryVO houseCropsVO);
	Integer DeleteCropsDiary(Integer id);
	List<HouseCropsDiaryVO> MonthlyCropsDiaryList(Integer greenHouseId, Integer year, Integer month);
	HashMap<String,Object> getHouseCropsInfo(Integer greenHouseId);
	

}
