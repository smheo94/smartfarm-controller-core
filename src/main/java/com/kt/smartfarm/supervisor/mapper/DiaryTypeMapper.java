package com.kt.smartfarm.supervisor.mapper;


import com.kt.smartfarm.service.DiaryTypePropertiesVO;
import com.kt.smartfarm.service.DiaryTypeVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper("diaryTypeMapper")
public interface DiaryTypeMapper {
    public List<DiaryTypeVO> getDiaryTypeList(@Param(value="gsmKey") Long gsmKey, @Param(value="houseId") Long houseId, @Param(value="userIdx") Long userIdx);
    public List<DiaryTypePropertiesVO> getDiaryTypePropertyList(@Param(value="diaryTypeId") Long diaryTypeId);

}
