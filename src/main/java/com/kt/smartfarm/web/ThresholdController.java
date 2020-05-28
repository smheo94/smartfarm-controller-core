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

import java.util.List;

import com.kt.cmmn.util.InterceptLog;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.ThresholdVO;
import com.kt.smartfarm.service.ThresholdService;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping(value="/threshold")
public class ThresholdController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	
	@Resource(name = "thresholdService")
	private ThresholdService thresholdService;
		
	
	/**
	 * @description 온실 등록 ( 제어기 선택은 어디서? 밑에서~ )
	 * @param gsmKey
	 * @param greenHouseId
	 * @param thresholdVOs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value= "/gsm/{gsmKey}/house/{greenHouseId}", method = RequestMethod.POST)
	@ResponseBody
	@InterceptLog
	public Result insert(@PathVariable Long gsmKey, @PathVariable Long greenHouseId,
											@RequestBody List<ThresholdVO> thresholdVOs){
		try {
			int cnt = thresholdService.update(gsmKey, greenHouseId, thresholdVOs);

			if ( cnt > 0 ) {
				log.info("{} | {} | Threshold insert | SUCCEED | {} ", "UnknownHost", "UnknownUser", thresholdVOs );
				return new Result<List<ThresholdVO>>("OK", HttpStatus.OK, thresholdVOs);
			} else {
				log.info("{} | {} | Threshold insert | FAIL | {} ", "UnknownHost", "UnknownUser", thresholdVOs );
				return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "Query Failed");
			}
		} catch(Exception e) {
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}
	
	@RequestMapping(value= "/gsm/{gsmKey}/house/{greenHouseId}", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<ThresholdVO>> list( @PathVariable Long gsmKey, @PathVariable Long greenHouseId){
		try {
			return new Result(thresholdService.getThreshold(gsmKey,greenHouseId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}

	
}
