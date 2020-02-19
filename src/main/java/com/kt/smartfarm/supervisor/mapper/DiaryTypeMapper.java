package com.kt.smartfarm.supervisor.mapper;

import com.kt.smartfarm.service.DiaryTypeVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper("diaryTypeMapper")
public interface DiaryTypeMapper {
    public List<DiaryTypeVO> getDiaryTypeList(Long gsmKey, Long houseId, Long userIdx);
    public List<Map<String, Object>> getDiaryTypePropertyList(Long diaryTypeId);
}
