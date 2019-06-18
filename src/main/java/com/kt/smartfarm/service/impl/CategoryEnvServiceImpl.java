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

import com.kt.smartfarm.service.CategoryEnvService;
import com.kt.smartfarm.service.CategoryEnvVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import com.kt.smartfarm.supervisor.mapper.CategoryEnvMapper;

import javax.annotation.Resource;


@Service("categoryEnvService")
public class CategoryEnvServiceImpl extends EgovAbstractServiceImpl implements CategoryEnvService {


	//@Autowired
	@Resource(name = "categoryEnvMapper")
	CategoryEnvMapper categoryEnvMapper;
		
	@Override
	public CategoryEnvVO insert(CategoryEnvVO vo) {
		categoryEnvMapper.insert(vo);
		return vo;
	}

	
	@Override
	public CategoryEnvVO update(CategoryEnvVO vo) {
		categoryEnvMapper.update(vo);
		return vo;
	}

	
	@Override
	public List<CategoryEnvVO> list() {
		return categoryEnvMapper.list();		
	}

	@Override
	public CategoryEnvVO selectCategoryDetail(Integer categoryId) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("category_id", categoryId);
		return categoryEnvMapper.selectCategoryDetail(param);
	}

	@Override
	public Integer delete(Integer categoryId) {
		return categoryEnvMapper.delete(categoryId);
	}

}
