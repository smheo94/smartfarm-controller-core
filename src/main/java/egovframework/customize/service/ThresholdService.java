package egovframework.customize.service;

import java.util.HashMap;

public interface ThresholdService {

	ThresholdVO insert(ThresholdVO thresholdVO);
	ThresholdVO update(ThresholdVO thresholdVO);
	ThresholdVO getThreshold(HashMap<String,Object> param);

}
