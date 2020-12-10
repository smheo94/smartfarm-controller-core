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

import com.kt.cmmn.util.DateUtil;
import com.kt.cmmn.util.InterceptLog;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.config.Message;
import com.kt.smartfarm.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*
 * mgrEnv
 * 온실환경설정 및 제어환경설정
 * 센서구성,제어기구성,온실구성,제어로직구성,외부기상대구성,임계치구성
 */
@Controller
@RequestMapping(value = "/houseDiary")
public class HouseDiaryController {

    private static final Logger log = LoggerFactory.getLogger(HouseDiaryController.class);
    @Autowired
    private Message msg;
    @Resource(name = "houseDiaryService")
    private HouseDiaryService houseDiaryService;

    @Resource(name = "authCheckService")
    private AuthCheckService authCheckService;


    /**
     * @param greenHouseId
     * @return
     * @description house에 등록되어 있는 작물 정보
     */
    @RequestMapping(value = "/cropsInfo/{green_house_id}", method = RequestMethod.GET)
    @ResponseBody
    public Result houseCropsInfo(@PathVariable("green_house_id") Long greenHouseId) {
        try {
            return new Result(houseDiaryService.getHouseCropsInfo(greenHouseId));
        } catch (Exception e) {
            log.warn("Exception :{}", greenHouseId, e);
            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }


    /**
     * @param houseDiaryVO
     * @return
     * @description 작업일지, 가계부 , 사진일지 입력
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @InterceptLog
//	public Result insertDiary(@RequestPart(value="houseDiary", required=false) HouseDiaryVO houseDiaryVO, @RequestPart(value="file", required=false) MultipartFile[] file){
    public Result insert(@RequestBody HouseDiaryVO houseDiaryVO) {
        try {
            if (!authCheckService.authCheck(null, null, null, houseDiaryVO.getHouseIdList())) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseDiaryVO);
            }
            return new Result(houseDiaryService.insertHouseDiary(houseDiaryVO));
        } catch (Exception e) {
            log.warn("Exception :{}", houseDiaryVO, e);
            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    @RequestMapping(value = "/diaryFile", method = RequestMethod.POST, consumes = {"*/*"})
    @ResponseBody
    public Result insertDiaryFile(@RequestParam(value = "content_type", required = false) String contentType, @RequestParam("id") Integer id,
                                  @RequestPart(value = "file", required = false) MultipartFile[] file) {
        try {
            return new Result(houseDiaryService.insertDiaryFile(contentType, id, file));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", contentType, id, e);
            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }


    @RequestMapping(value = "/diaryFile", method = RequestMethod.DELETE, consumes = {"*/*"})
    @ResponseBody
    @InterceptLog
    public Result deleteDiaryFile(@RequestParam(value = "content_type", required = false) String contentType, @RequestParam("id") Integer id,
                                  @RequestBody FileIdxListVO fileIdList) {
        try {
            return new Result(houseDiaryService.deleteDiaryFileList(contentType, id, fileIdList.fileIdxList));
        } catch (Exception e) {
            log.warn("Exception :{}, {}, {}", contentType, id, fileIdList, e);
            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    /**
     * @param houseDiaryVO
     * @return
     * @description 작업일지, 가계부 update
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @InterceptLog
    public Result update(@PathVariable("id") Integer id, @RequestBody HouseDiaryVO houseDiaryVO) {
        try {
            houseDiaryVO.setId(id);
            return new Result(houseDiaryService.updateHouseDiary(houseDiaryVO));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", id, houseDiaryVO, e);

            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    /**
     * @param gsmKey
     * @return
     * @description 작업일지, 가계부 월별,전체 리스트
     */
    @RequestMapping(value = "/type/{baseDiaryTypeId}/list", method = RequestMethod.GET)
    @ResponseBody
    public Result getHouseDiaryListWithType(@PathVariable("baseDiaryTypeId") Integer baseDiaryTypeId,
                                            @RequestParam(value = "gsmKey") Long gsmKey,
                                            @RequestParam(value = "diaryTypeId", required = false) Integer diaryTypeId,
                                            @RequestParam(value = "startDate", required = false) String startDate,
                                            @RequestParam(value = "endDate", required = false) String endDate) {
        HashMap<String,Object> param = new HashMap<>();
        try {
            param.put("base_diary_type_id", baseDiaryTypeId);
            param.put("gsm_key", gsmKey);
            if ( diaryTypeId != null ) {
                param.put("diary_type_id", diaryTypeId);
            }
            if ( startDate != null ) {
                param.put("start_date", DateUtil.parse(startDate));
            }
            if ( endDate != null ) {
                param.put("end_date", DateUtil.parse(endDate));
            }
            return new Result(houseDiaryService.getHouseDiaryList(param));
        } catch (Exception e) {
            log.warn("getHouseDiaryListWithType :{}, {}, {}, {}, {}", baseDiaryTypeId, gsmKey, startDate, endDate, e);
            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    /**
     * @param greenHouseId
     * @return
     * @description 작업일지, 가계부 월별,전체 리스트
     */
    @RequestMapping(value = "/list/{greenHouseId}", method = RequestMethod.GET)
    @ResponseBody
    public Result monthlyHouseDiaryList(@PathVariable("greenHouseId") Long greenHouseId,
                                        @RequestParam(value = "year", required = false) Integer year,
                                        @RequestParam(value = "month", required = false) Integer month,
                                        @RequestParam(value = "startDate", required = false) String startDate,
                                        @RequestParam(value = "endDate", required = false) String endDate
                                        ) {
        try {
            HashMap<String,Object> param = new HashMap<>();
            if(year != null && month != null){
                this.setMonthPeriod(param, year, month);
            }
            if ( startDate != null ) {
                param.put("start_date", DateUtil.parse(startDate));
            }
            if ( endDate != null ) {
                param.put("end_date", DateUtil.parse(endDate));
            }
            param.put("green_house_id", greenHouseId);
            return new Result(houseDiaryService.getHouseDiaryList(param));
            if (!authCheckService.authCheck(null, greenHouseId)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, greenHouseId);
            }
            return new Result(houseDiaryService.getMonthlyHouseDiaryList(null, null, greenHouseId, year, month));
        } catch (Exception e) {
            log.warn("Exception :{}, {}, {}", greenHouseId,year, month, e);
            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }


    /**
     * @param gsmKey
     * @return
     * @description 작업일지, 가계부 월별,전체 리스트
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result monthlyHouseDiaryListByGSM(@RequestParam(value = "gsmKey", required = false) Long gsmKey,
                                             @RequestParam(value = "gsmKeyList", required = false) List<Long> gsmKeyList,
                                             @RequestParam(value = "year", required = false) Integer year,
                                             @RequestParam(value = "month", required = false) Integer month) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, gsmKeyList, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseDiaryService.getMonthlyHouseDiaryList(gsmKeyList, gsmKey, null, year, month));
            HashMap<String,Object> param = new HashMap<>();
            if(year != null && month != null){
                this.setMonthPeriod(param, year, month);
            }
            if( gsmKey != null) {
                param.put("gsm_key", gsmKey);
            }
            if( gsmKeyList != null) {
                param.put("gsm_key_list", gsmKeyList);
            }
            return new Result(houseDiaryService.getHouseDiaryList(param));
        } catch (Exception e) {
            log.warn("MonthlyHouseDiaryListByGSM :{}, {}, {}, {}", gsmKey, gsmKeyList, year, month, e);
            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    private void setMonthPeriod(HashMap param, int year, int month) {
        Calendar sd = Calendar.getInstance();
        month = month - 1;
        sd.set(year, month, 1, 0,0,0);
        param.put("start_date", sd.getTime());
        sd.add(Calendar.MONTH, 1);
        sd.add(Calendar.MILLISECOND, -1);
        param.put("end_date", sd.getTime());
    }

    /**
     * @param id
     * @return
     * @description 작업일지, 가계부 상세조회
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result houseDiaryDetail(@PathVariable Integer id) {
        try {
            return new Result(houseDiaryService.getHouseDiaryDetail(id));
        } catch (Exception e) {
            log.warn("houseDiaryDetail :{}", id, e);

            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    /**
     * @param id
     * @return
     * @description 작업일지, 가계부 월별 리스트
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteHouseDiary(@PathVariable("id") Integer id) {
        try {
            return new Result(houseDiaryService.deleteHouseDiary(id));
        } catch (Exception e) {
            log.warn("DeleteHouseDiary :{}", id, e);

            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }


    @RequestMapping(value = "/cropsDiary/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public Result cropsDiaryDetail(@PathVariable Integer id) {
        try {
            return new Result(null);
            //return new Result(houseDiaryService.getCropsDiaryDetail(id));
        } catch (Exception e) {
            log.warn("cropsDiaryDetail :{}", id, e);

            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    /**
     * 작물
     * 종류
     * 리스트 return 해주는것
     */
    @RequestMapping(value = "/categoryList", method = RequestMethod.GET)
    @ResponseBody
    public Result categoryList() {
        try {
            return new Result(null);
            //return new Result(houseDiaryService.getCategoryList());
        } catch (Exception e) {
            log.warn("CategoryList GET", e);

            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }


    @RequestMapping(value = "/cropsDiary", method = RequestMethod.POST)
    @ResponseBody
//	public Result insertCropsDiary(@RequestPart(value="cropsDiary", required=false) HouseCropsDiaryVO houseCropsVO, @RequestPart(value="file", required=false) MultipartFile[] file){
    public Result insertCropsDiary(@RequestBody HouseCropsDiaryVO houseCropsVO) {
        try {
            return new Result(houseCropsVO);
            //return new Result(houseDiaryService.insertCropsDiary(houseCropsVO));
        } catch (Exception e) {
            log.warn("insertCropsDiary :{}", houseCropsVO, e);

            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    /**
     * @param houseCropsVO
     * @return
     * @description 작업일지, 가계부 입력
     */
    @RequestMapping(value = "/cropsDiary/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @Deprecated
    public Result updateCropsDiary(@RequestBody HouseCropsDiaryVO houseCropsVO) {
        try {
            return new Result(houseCropsVO);
            //return new Result(houseDiaryService.updateCropsDiary(houseCropsVO));
        } catch (Exception e) {
            log.warn("cropsDiary UPDATE :{}", houseCropsVO, e);

            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    /**
     * @param id
     * @return
     * @description 작업일지, 가계부 월별 리스트
     */
    @RequestMapping(value = "/cropsDiary/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Deprecated
    public Result deleteCropsDiary(@PathVariable("id") Integer id) {
        try {
            return new Result(id);
            //return new Result(houseDiaryService.deleteCropsDiary(id));
        } catch (Exception e) {
            log.warn("cropsDiary DELETE:{}", id, e);

            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    /**
     * @param houseId
     * @return
     * @description 사진일지 리스트
     */
    @RequestMapping(value = "/imageDiary/{houseId}", method = RequestMethod.GET)
    @ResponseBody
    public Result getImageDiaryList(@PathVariable("houseId") Long houseId) {
        try {
            //error message가 null 이고 push_type이 9인것들
            return new Result(houseDiaryService.getImageDiaryList(houseId));
        } catch (Exception e) {
            log.warn("imageDiary :{}", houseId, e);

            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    @RequestMapping(value = "/imageDiary/v2/", method = RequestMethod.GET)
    @ResponseBody
    public Result getImageDiaryList(
            @RequestParam(required = false, name = "gsmKey") Long gsmKey,
            @RequestParam(required = false, name = "houseId") List<Long> houseIdList,
            @RequestParam(required = false, name = "fromDate") Long fromDate,
            @RequestParam(required = false, name = "toDate") Long toDate,
            @RequestParam(required = false, name = "size") Integer size,
            @RequestParam(required = false, name = "page") Integer page
    ) throws HttpStatusCodeException {
		try {
			//error message가 null 이고 push_type이 9인것들
			return new Result(houseDiaryService.getImageDiaryListV2(gsmKey, houseIdList, fromDate, toDate, page, size, false));
		} catch (Exception e) {
			log.warn("imageDiary V2 GET :{}, {}, {}, {}, {}, {}", gsmKey, houseIdList, fromDate, toDate, size, page, e);
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
    }

    @RequestMapping(value = "/imageDiary/v2/totalCount", method = RequestMethod.GET)
    @ResponseBody
    public Result getImageDiaryListCount(
            @RequestParam(required = false, name = "gsmKey") Long gsmKey,
            @RequestParam(required = false, name = "houseId") List<Long> houseIdList,
            @RequestParam(required = false, name = "fromDate") Long fromDate,
            @RequestParam(required = false, name = "toDate") Long toDate
    ) throws HttpStatusCodeException {
		try {
			//error message가 null 이고 push_type이 9인것들
			return new Result(houseDiaryService.getImageDiaryListV2(gsmKey, houseIdList, fromDate, toDate, null, null, true));
		} catch (Exception e) {
			log.warn("getImageDiaryListCount :{}, {}, {}, {}", gsmKey, houseIdList, fromDate, toDate, e);
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
		}
    }

    @RequestMapping(value = "/sweet/graph", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<SweetContentVO>> getSweetContentGraphList(
            @RequestParam(required = false, name = "gsmKey") Long gsmKey,
            @RequestParam(required = false, name = "houseId") Long houseId,
            @RequestParam(required = false, name = "fromDate") String fromDate,
            @RequestParam(required = false, name = "toDate") String toDate
    ) throws HttpStatusCodeException {
        try {
            if (!authCheckService.authCheck(gsmKey, houseId)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseDiaryService.getSweetContentGraphList(gsmKey, houseId,
                    fromDate, toDate));
        } catch (Exception e) {
            log.warn("getSweetContentGraphList :{}, {}, {}, {}", gsmKey, houseId, fromDate, toDate,  e);
            return new Result("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }
}
