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
package egovframework.customize.service.impl;

import egovframework.cmmn.util.Result;
import egovframework.customize.service.CommonEnvService;
import egovframework.customize.service.CommonEnvVO;
import egovframework.customize.service.HouseCropsDiaryVO;
import egovframework.customize.service.HouseDiaryService;
import egovframework.customize.service.HouseDiaryVO;
import egovframework.customize.service.HousePictureDiaryVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.kt.smartfarm.supervisor.mapper.HouseDiaryMapper;


@Service("houseDiaryService")
public class HouseDiaryServiceImpl extends EgovAbstractServiceImpl implements HouseDiaryService {
    private static final Logger log = LoggerFactory.getLogger(HouseDiaryServiceImpl.class);

	@Resource(name="houseDiaryMapper")
    private HouseDiaryMapper	houseDiaryMapper;


	@Override
	public HouseDiaryVO insertHouseDiary(HouseDiaryVO houseDiaryVO, MultipartFile[] file) {
		try{
			houseDiaryMapper.insertHouseDiary(houseDiaryVO);
			for(int i=0; i<file.length; i++){
				if(!file[i].isEmpty()){
					String fileName=file[i].getOriginalFilename();
					byte[] bytes = file[i].getBytes();
					houseDiaryVO.setFile(bytes);
					houseDiaryVO.setFileName(fileName);
					houseDiaryMapper.insertHouseDiaryFile(houseDiaryVO);
				}	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return houseDiaryVO;
	}
	
	@Override
	public HouseDiaryVO updateHouseDiary(HouseDiaryVO houseDiaryVO, MultipartFile[] file) {
		try{
			houseDiaryMapper.updateHouseDiary(houseDiaryVO);
			for(int i=0; i<file.length; i++){
				if(!file[i].isEmpty()){
					String fileName=file[i].getOriginalFilename();
					byte[] bytes = file[i].getBytes();
					houseDiaryVO.setFile(bytes);
					houseDiaryVO.setFileName(fileName);
					houseDiaryMapper.updateHouseDiaryFile(houseDiaryVO);
				}	
			}	
		}catch(Exception e){
			e.printStackTrace();
			log.debug("updateHouseDiary Error : " + e);
		}		
		return houseDiaryVO;
	}

	@Override
	public Integer deleteHouseDiary(Integer id) {
		try{
			HashMap<String,Object> param = new HashMap<>();
			param.put("id", id);
			houseDiaryMapper.deleteHouseDiary(param);
			return houseDiaryMapper.deleteHouseDiaryFile(param);
		}catch(Exception e){
			e.printStackTrace();
			log.debug("deleteHouseDiary Exception : " + e);
			return null;
		}
	}

	@Override
	public HouseDiaryVO getHouseDiaryDetail(Integer id) {
		HouseDiaryVO houseDiaryVO = new HouseDiaryVO();
		try{			
			HashMap<String,Object> param = new HashMap<>();
			param.put("id", id);
			houseDiaryVO = houseDiaryMapper.getHouseDiaryDetail(param);			
		}catch(Exception e){
			e.printStackTrace();
			log.debug("getHouseDiaryDetail Exception : " + e);
		}		
		return houseDiaryVO;
	}


	@Override
	public List<HashMap<String,Object>> getMonthlyHouseDiaryList(Integer houseId,Integer year, Integer month) {
		HashMap<String,Object> param = new HashMap<>();
		param = getMonthDate(year,month);
		param.put("green_house_id", houseId);
		return houseDiaryMapper.getMonthlyHouseDiaryList(param);
		
	}

	@Override
	public List<HashMap<String, Object>> getCategoryList() {
		return houseDiaryMapper.getCategoryList22();		
	}
    
	public HashMap<String,Object> getMonthDate(Integer year,Integer month){
		HashMap<String,Object> result = new HashMap<>();
		Calendar startDateCal = Calendar.getInstance();
		startDateCal.set(Calendar.YEAR, year);
		startDateCal.set(Calendar.MONTH, month - 1);
		startDateCal.set(Calendar.DAY_OF_MONTH, 1);
		startDateCal.set(Calendar.HOUR_OF_DAY, 0);
		startDateCal.set(Calendar.MINUTE, 0);
		startDateCal.set(Calendar.SECOND, 0);
		startDateCal.set(Calendar.MILLISECOND, 0);
		
		// 종료날을 다음달 1일로 셋팅
		Calendar endDateCal = (Calendar)startDateCal.clone();
		endDateCal.add(Calendar.MONTH, 1);
		endDateCal.add(Calendar.MILLISECOND, -1);
		
		Date startDate = startDateCal.getTime();
		Date endDate = endDateCal.getTime();		
		result.put("start_date", startDate);
		result.put("end_date", endDate);
		return result;
	}

	@Override
	public HouseCropsDiaryVO insertCropsDiary(HouseCropsDiaryVO houseCropsVO, MultipartFile[] file) {
		try{
			houseDiaryMapper.insertCropsDiary(houseCropsVO);
			for(int i=0; i<file.length; i++){
				if(!file[i].isEmpty()){
					String fileName=file[i].getOriginalFilename();
					byte[] bytes = file[i].getBytes();
					houseCropsVO.setFile(bytes);
					houseCropsVO.setFileName(fileName);
					houseDiaryMapper.insertCropsDiaryFile(houseCropsVO);
				}	
			}
		}catch(Exception e){
			log.debug("insertCropsDiary Exception : "+e);
		}
		return houseCropsVO;
	}
	@Override
	public HouseCropsDiaryVO updateCropsDiary(HouseCropsDiaryVO houseCropsVO, MultipartFile[] file) {
		try{
			houseDiaryMapper.updateCropsDiary(houseCropsVO);
			for(int i=0; i<file.length; i++){
				if(!file[i].isEmpty()){
					String fileName=file[i].getOriginalFilename();
					byte[] bytes = file[i].getBytes();
					houseCropsVO.setFile(bytes);
					houseCropsVO.setFileName(fileName);
					houseDiaryMapper.updateCropsDiaryFile(houseCropsVO);
				}	
			}
		}catch(Exception e){
			log.debug("updateCropsDiary Exception : "+e);
		}		
		return houseCropsVO;
	}

	@Override
	public Integer DeleteCropsDiary(Integer id) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("id", id);
		houseDiaryMapper.deleteCropsDiary(param);
		return houseDiaryMapper.deleteCropsDiaryFile(param);
	}

	@Override
	public List<HouseCropsDiaryVO> getMonthlyCropsDiaryList(Integer greenHouseId, Integer year, Integer month) {
	 	HashMap<String,Object> param = new HashMap<>();
		param = getMonthDate(year,month);
		param.put("house_id", greenHouseId);
		return houseDiaryMapper.getMonthlyCropsDiaryList(param);
	}

	@Override
	public HashMap<String, Object> getHouseCropsInfo(Integer greenHouseId) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("green_house_id", greenHouseId);
		return houseDiaryMapper.getHouseCropsInfo(param);
	}
}
