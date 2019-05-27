package egovframework.customize.service;

import java.util.List;

public interface ThresholdService {

	List<ThresholdVO> getThreshold(Integer gsmKey, Integer houseId);
	int update(Integer gsmKey, Integer houseId, List<ThresholdVO> thresholdVOs);

	Integer copyToNewGSM(Integer fromGsmKey, Integer toGsmKey);
}
