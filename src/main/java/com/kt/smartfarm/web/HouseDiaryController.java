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

import com.kt.cmmn.util.InterceptLog;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/*
 * mgrEnv
 * 온실환경설정 및 제어환경설정
 * 센서구성,제어기구성,온실구성,제어로직구성,외부기상대구성,임계치구성
 */
@Controller
@RequestMapping(value="/houseDiary")
public class HouseDiaryController {

	private static final Logger log = LoggerFactory.getLogger(HouseDiaryController.class);
	@Resource(name = "houseDiaryService")
	private HouseDiaryService houseDiaryService;

	@Resource(name="authCheckService")
	private AuthCheckService authCheckService;



	/**
	 * @description house에 등록되어 있는 작물 정보
	 * @param greenHouseId 
	 * @return
	 */
	@RequestMapping(value= "/cropsInfo/{green_house_id}", method = RequestMethod.GET)
	@ResponseBody
	public Result houseCropsInfo(@PathVariable("green_house_id") Long greenHouseId){
		try{
			return new Result(houseDiaryService.getHouseCropsInfo(greenHouseId));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}
	

	/**
	 * @description 작업일지, 가계부 , 사진일지 입력
	 * @param houseDiaryVO
	 * @return
	 */
	@RequestMapping(value= "", method = RequestMethod.POST)
	@ResponseBody
	@InterceptLog
//	public Result insertDiary(@RequestPart(value="houseDiary", required=false) HouseDiaryVO houseDiaryVO, @RequestPart(value="file", required=false) MultipartFile[] file){
	public Result insert(@RequestBody HouseDiaryVO houseDiaryVO){
		try{
			return new Result(houseDiaryService.insertHouseDiary(houseDiaryVO));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}
	
	@RequestMapping(value= "/diaryFile", method = RequestMethod.POST, consumes = { "*/*" })
	@ResponseBody
	public Result insertDiaryFile(@RequestParam(value = "content_type", required = false) String contentType, @RequestParam("id") Integer id,
			@RequestPart(value="file", required=false) MultipartFile[] file){
		try{
			return new Result(houseDiaryService.insertDiaryFile(contentType,id,file));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}


	@RequestMapping(value= "/diaryFile", method = RequestMethod.DELETE, consumes = { "*/*" })
	@ResponseBody
	@InterceptLog
	public Result deleteDiaryFile(@RequestParam(value = "content_type", required = false) String contentType, @RequestParam("id") Integer id,
								  @RequestBody FileIdxListVO fileIdList){
		try{
			return new Result(houseDiaryService.deleteDiaryFileList(contentType,id,fileIdList.fileIdxList));
		}catch(Exception e){

			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}
	
	/**
	 * @description 작업일지, 가계부 update
	 * @param houseDiaryVO
	 * @return
	 */
	@RequestMapping(value= "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@InterceptLog
	public Result update(@PathVariable("id") Integer id,  @RequestBody HouseDiaryVO houseDiaryVO){
		try{
			houseDiaryVO.setId(id);
			return new Result(houseDiaryService.updateHouseDiary(houseDiaryVO));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}
	

	
	
	/**
	 * @description 작업일지, 가계부 월별,전체 리스트 
	 * @param greenHouseId
	 * @return
	 */
	@RequestMapping(value= "/list/{greenHouseId}", method = RequestMethod.GET)
	@ResponseBody
	public Result MonthlyHouseDiaryList(@PathVariable("greenHouseId") Long greenHouseId,
			@RequestParam(value="year",required=false) Integer year, @RequestParam(value="month",required=false) Integer month){
		try{
			return new Result(houseDiaryService.getMonthlyHouseDiaryList(null, null, greenHouseId,year,month));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}


	/**
	 * @description 작업일지, 가계부 월별,전체 리스트
	 * @param gsmKey
	 * @return
	 */
	@RequestMapping(value= "/list", method = RequestMethod.GET)
	@ResponseBody
	public Result MonthlyHouseDiaryListByGSM(@RequestParam(value="gsmKey", required=false) Long gsmKey,
											 @RequestParam(value = "gsmKeyList", required=false) List<Long>gsmKeyList,
										@RequestParam(value="year",required=false) Integer year, @RequestParam(value="month",required=false) Integer month){
		try{
			return new Result(houseDiaryService.getMonthlyHouseDiaryList(gsmKeyList, gsmKey,null, year,month));
		}catch(Exception e){

			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}
	
	/**
	 * @description 작업일지, 가계부 상세조회
	 * @param id
	 * @return
	 */
	
	@RequestMapping(value= "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Result houseDiaryDetail(@PathVariable Integer id){
		try{
			return new Result(houseDiaryService.getHouseDiaryDetail(id));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
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
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}


	@RequestMapping(value= "/cropsDiary/{id}", method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public Result cropsDiaryDetail(@PathVariable Integer id){
		try{
			return new Result(houseDiaryService.getCropsDiaryDetail(id));
		}catch(Exception e){

			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
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
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}

	
	
	@RequestMapping(value= "/cropsDiary", method = RequestMethod.POST)
	@ResponseBody
//	public Result insertCropsDiary(@RequestPart(value="cropsDiary", required=false) HouseCropsDiaryVO houseCropsVO, @RequestPart(value="file", required=false) MultipartFile[] file){
	public Result insertCropsDiary(@RequestBody HouseCropsDiaryVO houseCropsVO){
		try{
			return new Result(houseDiaryService.insertCropsDiary(houseCropsVO));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}
	
	/**
	 * @description 작업일지, 가계부 입력
	 * @param houseCropsVO
	 * @return
	 */
	@RequestMapping(value= "/cropsDiary/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Deprecated
	public Result updateCropsDiary(@RequestBody HouseCropsDiaryVO houseCropsVO){
		try{
			return new Result(houseDiaryService.updateCropsDiary(houseCropsVO));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}

	/**
	 * @description 작업일지, 가계부 월별 리스트 
	 * @param id
	 * @return
	 */
	@RequestMapping(value= "/cropsDiary/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	@Deprecated
	public Result deleteCropsDiary(@PathVariable("id") Integer id){
		try{
			return new Result(houseDiaryService.deleteCropsDiary(id));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}
	
	/**
	 * @description 사진일지 리스트 
	 * @param houseId
	 * @return
	 */
	@RequestMapping(value= "/imageDiary/{houseId}", method = RequestMethod.GET)
	@ResponseBody
	public Result getImageDiaryList(@PathVariable("houseId") Long houseId){
		try{
			//error message가 null 이고 push_type이 9인것들
			return new Result(houseDiaryService.getImageDiaryList(houseId));
		}catch(Exception e){
			
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
	}

    @RequestMapping(value= "/imageDiary/v2/", method = RequestMethod.GET)
    @ResponseBody
    public Result getImageDiaryList(
                                    @RequestParam(required = false, name = "gsmKey") Long gsmKey,
									@RequestParam(required = false, name = "houseId") List<Long>houseIdList,
                                    @RequestParam(required = false, name = "fromDate") Long fromDate,
                                    @RequestParam(required = false, name = "toDate") Long toDate,
                                    @RequestParam(required = false, name = "size") Integer size,
                                    @RequestParam(required = false, name = "page") Integer page
    ) throws HttpStatusCodeException {
        try{

			try{
				//error message가 null 이고 push_type이 9인것들
				return new Result(houseDiaryService.getImageDiaryListV2(gsmKey, houseIdList, fromDate, toDate, page, size, false));
			}catch(Exception e){

				return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
			}
        }catch(Exception e){
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage() );
        }
    }

	@RequestMapping(value= "/imageDiary/v2/totalCount", method = RequestMethod.GET)
	@ResponseBody
	public Result getImageDiaryListCount(
			@RequestParam(required = false, name = "gsmKey") Long gsmKey,
			@RequestParam(required = false, name = "houseId") List<Long>houseIdList,
			@RequestParam(required = false, name = "fromDate") Long fromDate,
			@RequestParam(required = false, name = "toDate") Long toDate
	) throws HttpStatusCodeException {
		try{

			try{
				//error message가 null 이고 push_type이 9인것들
				return new Result(houseDiaryService.getImageDiaryListV2(gsmKey, houseIdList, fromDate, toDate, null, null, true));
			}catch(Exception e){

				return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
			}
		}catch(Exception e){
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage() );
		}
	}
}
