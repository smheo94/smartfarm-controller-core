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

import egovframework.customize.service.HouseCropsDiaryVO;
import egovframework.customize.service.HouseDiaryService;
import egovframework.customize.service.HouseDiaryVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kt.smartfarm.supervisor.mapper.HouseDiaryMapper;


@Service("houseDiaryService")
public class HouseDiaryServiceImpl extends EgovAbstractServiceImpl implements HouseDiaryService {
    private static final Logger log = LoggerFactory.getLogger(HouseDiaryServiceImpl.class);

	@Resource(name="houseDiaryMapper")
    private HouseDiaryMapper	houseDiaryMapper;


	@Override
	public HouseDiaryVO insertHouseDiary(HouseDiaryVO houseDiaryVO) {
		try{
			houseDiaryMapper.insertHouseDiary(houseDiaryVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return houseDiaryVO;
	}
	
	@Override
	public Integer insertDiaryFile(String contentType, Integer id, MultipartFile[] file) {
		Integer result=0;
		try{			
//			String fileName=file[i].getOriginalFilename();
		
//			if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") ||
//		            fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".gif") ||
//		            fileName.toLowerCase().endsWith(".bmp") || fileName.toLowerCase().endsWith(".hwp") ||
//		            fileName.toLowerCase().endsWith(".ppt") ||fileName.toLowerCase().endsWith(".pptx") ||		            
//		            fileName.toLowerCase().endsWith(".pdf") ||fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".txt")) {
				for(int i=0; i<file.length; i++){
					if(!file[i].isEmpty()){
						String fileName=file[i].getOriginalFilename();
						byte[] bytes = file[i].getBytes();
						if(contentType.equals("11") || contentType.equals("21")){
							HouseDiaryVO houseDiaryVO = new HouseDiaryVO();
							houseDiaryVO.setId(id);
							houseDiaryVO.setFile(bytes);
							houseDiaryVO.setFileName(fileName);
							result = houseDiaryMapper.insertHouseDiaryFile(houseDiaryVO);
						}
						else if(contentType.equals("31")){
							HouseCropsDiaryVO houseCropsDiaryVO= new HouseCropsDiaryVO();
							houseCropsDiaryVO.setId(id);
							houseCropsDiaryVO.setFile(bytes);
							houseCropsDiaryVO.setFileName(fileName);
							result = houseDiaryMapper.insertCropsDiaryFile(houseCropsDiaryVO);
						}
					}	
				}
//			}
		
		}catch(Exception e){
			log.debug("insertDiaryFile Error = " + e);
		}
		return result;
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
		List<HashMap<String,Object>> list =houseDiaryMapper.getMonthlyHouseDiaryList(param); 
		return list;
		
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
	public HouseCropsDiaryVO insertCropsDiary(HouseCropsDiaryVO houseCropsVO) {
		try{
			houseDiaryMapper.insertCropsDiary(houseCropsVO);			
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
		List<HouseCropsDiaryVO> list =houseDiaryMapper.getMonthlyCropsDiaryList(param); 
		return list;
	}

	@Override
	public HashMap<String, Object> getHouseCropsInfo(Integer greenHouseId) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("green_house_id", greenHouseId);
		return houseDiaryMapper.getHouseCropsInfo(param);
	}
}
