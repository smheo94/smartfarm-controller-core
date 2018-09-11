package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface HouseDiaryService {

	HashMap<String, Object> selectAllCategory();

	List<HashMap<String, Object>> getMonthlyDiary(String year, String month);

}
