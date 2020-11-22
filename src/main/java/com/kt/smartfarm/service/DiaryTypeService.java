package com.kt.smartfarm.service;

import java.util.List;
import java.util.Map;


public interface DiaryTypeService {
	List<DiaryTypeVO> getDiaryTypeList(Long gsmKey, Long houseId, Long userIdx);
	List<DiaryTypePropertiesVO> getDiaryTypePropertyList(Long diaryTypeId);
	List<DiaryTypePropertiesVO> getDiaryTypePropertyList(Map param);

}
