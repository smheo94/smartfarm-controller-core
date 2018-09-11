package egovframework.customize.service;

public interface ThresholdService {

	ThresholdVO insert(ThresholdVO thresholdVO);
	ThresholdVO update(ThresholdVO thresholdVO);
	ThresholdVO getThreshold(String gsmKey);

}
