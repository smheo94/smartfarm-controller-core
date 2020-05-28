package com.kt.smartfarm.service;

import java.util.List;


public interface DiaryTypeService {
	List<DiaryTypeVO> getDiaryTypeList(Long gsmKey, Long houseId, Long userIdx);
	List<DiaryTypePropertiesVO> getDiaryTypePropertyList(Long diaryTypeId);

}
