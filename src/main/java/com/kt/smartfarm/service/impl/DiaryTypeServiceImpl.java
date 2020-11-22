/*
 * Copyright 2008-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kt.smartfarm.service.impl;

import com.kt.smartfarm.service.DiaryTypePropertiesVO;

import com.kt.smartfarm.service.DiaryTypeService;
import com.kt.smartfarm.service.DiaryTypeVO;
import com.kt.smartfarm.mapper.DiaryTypeMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import java.util.Map;
import java.util.Objects;

import java.util.stream.Collectors;


@Slf4j
@Service("diaryTypeService")
public class DiaryTypeServiceImpl extends EgovAbstractServiceImpl implements DiaryTypeService {

	@Resource(name="diaryTypeMapper")
	DiaryTypeMapper mapper;
	@Override
	public List<DiaryTypeVO> getDiaryTypeList(Long gsmKey, Long houseId, Long userIdx) {
		final List<DiaryTypeVO> diaryTypeList = mapper.getDiaryTypeList(gsmKey, houseId, userIdx);
		final List<DiaryTypePropertiesVO> diaryTypePropertyList = mapper.getDiaryTypePropertyList((Long)null);
		diaryTypeList.forEach( cl ->{
			try {
				cl.setPropertyList(diaryTypePropertyList.stream().filter(m -> Objects.equals(m.getDiaryTypeId(), cl.getId())).collect(Collectors.toList()));
				cl.setAbleHouseList(mapper.getDiaryAbleHouseId(gsmKey, cl.getId()));
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		});
		return diaryTypeList;
	}

	@Override
	public List<DiaryTypePropertiesVO> getDiaryTypePropertyList(Long diaryTypeId) {
		return  mapper.getDiaryTypePropertyList(diaryTypeId);
	}

	@Override
	public List<DiaryTypePropertiesVO> getDiaryTypePropertyList(Map param) {
		return  mapper.getDiaryTypePropertyList(param);
	}
}
