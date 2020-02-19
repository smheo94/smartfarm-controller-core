/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kt.smartfarm.web;

import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value={"/diary_type"})
public class DiaryTypeController {
	@Resource(name = "diaryTypeService")
	private DiaryTypeService service;

	/**
	 * 제어로직 전체 list
	 * @return
	 */
	@RequestMapping(value="", method = RequestMethod.GET)
	@ApiOperation("정의된 일지 목록을 조회합니다.")
	@ResponseBody
	public Result<List<DiaryTypeVO>> getDiaryTypeList(@RequestParam(value = "gsmKey", required = false) Long gsmKey, @RequestParam(value = "houseId", required = false) Long houseId, @RequestParam(value = "userIdx", required = false) Long userIdx){
		try {
			return new Result(service.getDiaryTypeList(gsmKey, houseId, userIdx));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	/**
	 * @description 제어로직 프로퍼티 조회
	 * @return
	 */
	@RequestMapping(value= "/{diaryTypeId}/property", method = RequestMethod.GET)
	@ApiOperation("특정 제어로직에 사용될 프로퍼티 목록을 조회합니다.")
	@ResponseBody
	public Result<List<DiaryTypePropertiesVO>> getDiaryTypePropertyList(@PathVariable("diaryTypeId") Long diaryTypeId){
		try {
			return new Result(service.getDiaryTypePropertyList(diaryTypeId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}


}
