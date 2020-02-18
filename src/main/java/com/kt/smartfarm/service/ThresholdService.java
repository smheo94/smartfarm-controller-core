package com.kt.smartfarm.service;

import java.util.List;

public interface ThresholdService {

	List<ThresholdVO> getThreshold(Long gsmKey, Long houseId);
	int update(Long gsmKey, Long houseId, List<ThresholdVO> thresholdVOs);

	Integer copyToNewGSM(Long fromGsmKey, Long toGsmKey);
}
