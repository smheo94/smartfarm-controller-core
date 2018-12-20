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

import egovframework.cmmn.util.InterceptPre;
import egovframework.cmmn.util.Result;
import egovframework.customize.service.HouseCropsDiaryVO;
import egovframework.customize.service.HouseDiaryService;
import egovframework.customize.service.HouseDiaryVO;
import egovframework.customize.service.HousePictureDiaryVO;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;

/*
 * mgrEnv
 * 온실환경설정 및 제어환경설정
 * 센서구성,제어기구성,온실구성,제어로직구성,외부기상대구성,임계치구성
 */
@Controller
@RequestMapping(value={"/env/houseDiary","/houseDiary"})
public class HouseDiaryController {


	@Resource(name = "houseDiaryService")
	private HouseDiaryService houseDiaryService;
	
	/**
	 * @description house에 등록되어 있는 작물 정보
	 * @param greenHouseId 
	 * @return
	 */
	@RequestMapping(value= "/cropsInfo/{green_house_id}", method = RequestMethod.GET)
	@ResponseBody
	public Result houseCropsInfo(@PathVariable("green_house_id") Integer greenHouseId){
		try{
			return new Result(houseDiaryService.getHouseCropsInfo(greenHouseId));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * @description 작업일지, 가계부 , 사진일지 입력
	 * @param houseDiaryVO
	 * @return
	 */
	@RequestMapping(value= "", method = RequestMethod.POST)
	@ResponseBody
//	public Result insertDiary(@RequestPart(value="houseDiary", required=false) HouseDiaryVO houseDiaryVO, @RequestPart(value="file", required=false) MultipartFile[] file){
	public Result insert(@RequestBody HouseDiaryVO houseDiaryVO){
		try{
			return new Result(houseDiaryService.insertHouseDiary(houseDiaryVO));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@RequestMapping(value= "/diaryFile", method = RequestMethod.POST, consumes = { "*/*" })
	@ResponseBody
	public Result insertDiaryFile(@RequestParam("content_type") String contentType, @RequestParam("id") Integer id, 
			@RequestPart(value="file", required=false) MultipartFile[] file){
		try{
			return new Result(houseDiaryService.insertDiaryFile(contentType,id,file));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * @description 작업일지, 가계부 update
	 * @param houseDiaryVO
	 * @return
	 */
	@RequestMapping(value= "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Result update(@RequestBody HouseDiaryVO houseDiaryVO){	
		try{
			return new Result(houseDiaryService.updateHouseDiary(houseDiaryVO));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@RequestMapping(value= "/diaryFile/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Result update(@PathVariable("id") Integer id,@RequestParam("content_type") String contentType,@RequestPart(value="file", required=false) MultipartFile[] file){
		try{
			return new Result(houseDiaryService.updateDiaryFile(contentType,id,file));

		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	
	/**
	 * @description 작업일지, 가계부 월별 리스트 
	 * @param greenHouseId
	 * @return
	 */
	@RequestMapping(value= "/monthly/{greenHouseId}", method = RequestMethod.GET)
	@ResponseBody
	public Result MonthlyHouseDiaryList(@PathVariable("greenHouseId") Integer greenHouseId,
			@RequestParam("year") Integer year, @RequestParam("month") Integer month){
		try{
			return new Result(houseDiaryService.getMonthlyHouseDiaryList(greenHouseId,year,month));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * @description 작업일지, 가계부 상세조회
	 * @param id
	 * @return
	 */
	
	@RequestMapping(value= "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Result HouseDiaryDetail(@PathVariable Integer id){
		try{
			return new Result(houseDiaryService.getHouseDiaryDetail(id));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * @description 작업일지, 가계부 월별 리스트 
	 * @param id
	 * @return
	 */
	@RequestMapping(value= "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result DeleteHouseDiary(@PathVariable("id") Integer id){
		try{
			return new Result(houseDiaryService.deleteHouseDiary(id));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * 작물
	 * 종류
	 * 리스트 return 해주는것
	 */
	@RequestMapping(value= "/categoryList", method = RequestMethod.GET)
	@ResponseBody
	public Result CategoryList(){
		try{
			return new Result(houseDiaryService.getCategoryList());
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	
	
	@RequestMapping(value= "/cropsDiary", method = RequestMethod.POST)
	@ResponseBody
//	public Result insertCropsDiary(@RequestPart(value="cropsDiary", required=false) HouseCropsDiaryVO houseCropsVO, @RequestPart(value="file", required=false) MultipartFile[] file){
	public Result insertCropsDiary(@RequestBody HouseCropsDiaryVO houseCropsVO){
		try{
			return new Result(houseDiaryService.insertCropsDiary(houseCropsVO));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * @description 작업일지, 가계부 입력
	 * @param houseCropsVO
	 * @return
	 */
	@RequestMapping(value= "/cropsDiary/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Result updateCropsDiary(@RequestBody HouseCropsDiaryVO houseCropsVO){
		try{
			return new Result(houseDiaryService.updateCropsDiary(houseCropsVO));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	/**
	 * @description 작업일지, 가계부 월별 리스트 
	 * @param greenHouseId
	 * @return
	 */
	@RequestMapping(value= "/cropsDiary/monthly/{green_house_id}", method = RequestMethod.GET)
	@ResponseBody
	public Result MonthlyCropsDiaryList(@PathVariable("green_house_id") Integer greenHouseId, 
	@RequestParam("year") Integer year, @RequestParam("month") Integer month){
		try{
			return new Result(houseDiaryService.getMonthlyCropsDiaryList(greenHouseId,year,month));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * @description 작업일지, 가계부 월별 리스트 
	 * @param id
	 * @return
	 */
	@RequestMapping(value= "/cropsDiary/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteCropsDiary(@PathVariable("id") Integer id){
		try{
			return new Result(houseDiaryService.deleteCropsDiary(id));
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
