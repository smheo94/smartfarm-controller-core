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
package egovframework.customize.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import egovframework.cmmn.util.Result;
import egovframework.customize.service.CommonEnvService;
import egovframework.customize.service.CommonEnvVO;
import egovframework.customize.service.DeviceService;
import egovframework.customize.service.EgovSampleService;
import egovframework.customize.service.HouseDiaryService;
import egovframework.customize.service.SampleDefaultVO;
import egovframework.customize.service.SampleVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springmodules.validation.commons.DefaultBeanValidator;

/*
 * mgrEnv
 * 온실환경설정 및 제어환경설정
 * 센서구성,제어기구성,온실구성,제어로직구성,외부기상대구성,임계치구성
 */
@Controller
@RequestMapping("/houseDiary")
public class EgovSampleController2 {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	/** EgovSampleService */
	
	@Resource(name = "houseDiaryService")
	private HouseDiaryService houseDiaryService;
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	
	
	/**
	 * @description 작ㅇ버일지 카테고리 리스트
	 * @param request
	 * @return
	 */
	@RequestMapping(value= "/category/all", method = RequestMethod.GET)
	@ResponseBody
	public Result selectAllCategory(HttpServletRequest request){
		try{
			//applyDefault 빼자
			HashMap<String,Object> result = houseDiaryService.selectAllCategory();			
			return new Result<HashMap<String,Object>>("OK", HttpStatus.OK, result);	
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	/**
	 * @description 작업일지 월별 리스트
	 * @param year = 2018
	 * @param month = 08
	 * 
	 **/
	@RequestMapping(value="/monthlyDiaryList/{year}/{month}", method = RequestMethod.GET)
	@ResponseBody
	public Result selectMonthlyDiaryList(@PathVariable String year, @PathVariable String month){
		try{
			List<HashMap<String,Object>> diaryList = houseDiaryService.getMonthlyDiary(year,month);
			return new Result<List<HashMap<String,Object>>>("OK", HttpStatus.OK, diaryList);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}		
	}

	
	
	

}
