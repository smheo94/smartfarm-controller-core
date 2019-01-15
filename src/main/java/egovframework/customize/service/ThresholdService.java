package egovframework.customize.service;

import java.util.List;

public interface ThresholdService {

	List<ThresholdVO> insert(List<ThresholdVO> thresholdVO);
//	ThresholdVO update(ThresholdVO thresholdVO);
	List<ThresholdVO> getThreshold(Integer gsmKey, Integer houseId);
	int delete(Integer gsmKey, Integer greenHouseId);

}
