package com.kt.smartfarm.mapper;


import com.kt.smartfarm.service.DiaryTypeAbleHouseVO;
import com.kt.smartfarm.service.DiaryTypePropertiesVO;
import com.kt.smartfarm.service.DiaryTypeVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper("diaryTypeMapper")
public interface DiaryTypeMapper {
    List<DiaryTypeVO> getDiaryTypeList(@Param(value = "gsmKey") Long gsmKey, @Param(value = "houseId") Long houseId, @Param(value = "userIdx") Long userIdx);
    List<DiaryTypePropertiesVO> getDiaryTypePropertyList(Map param);
    List<DiaryTypeAbleHouseVO> getDiaryAbleHouseId(@Param(value = "gsmKey") Long gsmKey, @Param(value = "diaryTypeId") Long diaryTypeId);

}
