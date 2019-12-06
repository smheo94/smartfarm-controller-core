
package com.kt.smartfarm.service.impl;

import com.kt.smartfarm.service.HouseCropsDiaryVO;
import com.kt.smartfarm.service.HouseDiaryService;
import com.kt.smartfarm.service.HouseDiaryVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.util.*;

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
			houseDiaryMapper.insertHouseDiaryHouseMap(houseDiaryVO);

		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return houseDiaryVO;
	}
	@Override
	public HouseCropsDiaryVO insertCropsDiary(HouseCropsDiaryVO houseCropsVO) {
		try{
			houseDiaryMapper.insertCropsDiary(houseCropsVO);
			houseDiaryMapper.insertCropsDiaryHouseMap(houseCropsVO);
		}catch(Exception e){
			log.debug("insertCropsDiary Exception : "+e);
		}
		return houseCropsVO;
	}
	@Override
	public Integer deleteDiaryFileList(String contentType, Integer id, List<Integer> fileIdList) {
		try {
			HashMap<String, Object> param = new HashMap<>();
			param.put("id", id);
			param.put("file_idx_list", fileIdList);
			if(contentType.equals("11") || contentType.equals("21") || contentType.equals("1") || contentType.equals("2")) {
				return houseDiaryMapper.deleteHouseDiaryFile(param);
			} else if(contentType.equals("31") || contentType.equals("3")){
				return houseDiaryMapper.deleteCropsDiaryFile(param);
			}
		} catch(Exception e) {
			log.debug("deleterDiaryFile Error");
			return -1;
		}
		return 0;
	}
	@Override
	public Integer insertDiaryFile(String contentType, Integer id, MultipartFile[] file) {
		Integer result=0;
		try{
			//deleteDiaryFIleNotExists(file, id);
			for(int i=0; i<file.length; i++){
				if(!file[i].isEmpty()){
					String fileName=file[i].getOriginalFilename();
					byte[] bytes = file[i].getBytes();
					if(contentType.equals("11") || contentType.equals("21") || contentType.equals("1") || contentType.equals("2")){
						HouseDiaryVO houseDiaryVO = new HouseDiaryVO();
						houseDiaryVO.setId(id);
						houseDiaryVO.setFile(bytes);
						houseDiaryVO.setFileName(fileName);
//						HouseDiaryVO dbhouseDiaryVO  = houseDiaryMapper.selectHouseDiaryFile(houseDiaryVO);
//						if( dbhouseDiaryVO != null ) {
//							result = houseDiaryMapper.updateHouseDiaryFile(houseDiaryVO);
//						} else {
							result = houseDiaryMapper.insertHouseDiaryFile(houseDiaryVO);
//						}
					}
					else if(contentType.equals("31") || contentType.equals("3")){
						HouseCropsDiaryVO houseCropsDiaryVO= new HouseCropsDiaryVO();
						houseCropsDiaryVO.setId(id);
						houseCropsDiaryVO.setFile(bytes);
						houseCropsDiaryVO.setFileName(fileName);
//						HouseCropsDiaryVO dbhouseDiaryVO  = houseDiaryMapper.selectCropsDiaryFile(houseCropsDiaryVO);
//						if( dbhouseDiaryVO != null ) {
//							result = houseDiaryMapper.updateCropsDiaryFile(houseCropsDiaryVO);
//						} else {
							result = houseDiaryMapper.insertCropsDiaryFile(houseCropsDiaryVO);
//						}
					}
				}
			}

		}catch(Exception e){
			log.debug("insertDiaryFile Error = " + e);
		}
		return result;
	}

	@Override
	public HouseDiaryVO updateHouseDiary(HouseDiaryVO houseDiaryVO) {
		try{
			houseDiaryMapper.updateHouseDiary(houseDiaryVO);
			HashMap<String,Object> param = new HashMap<>();
			param.put("id", houseDiaryVO.getId());
			houseDiaryMapper.deleteHouseDiaryHouseMap(param);
			houseDiaryMapper.insertHouseDiaryHouseMap(houseDiaryVO);
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("updateHouseDiary Error : " + e);
		}		
		return houseDiaryVO;
	}
	@Override
	public HouseCropsDiaryVO updateCropsDiary(HouseCropsDiaryVO houseCropsVO) {
		try{
			houseDiaryMapper.updateCropsDiary(houseCropsVO);
			HashMap<String,Object> param = new HashMap<>();
			param.put("id", houseCropsVO.getId());
			houseDiaryMapper.deleteCropsDiaryHouseMap(param);
			houseDiaryMapper.insertCropsDiaryHouseMap(houseCropsVO);
		}catch(Exception e){
			log.debug("updateCropsDiary Exception : "+e);
		}		
		return houseCropsVO;
	}

	
	
	
	@Override
	public HashMap<String,Object> getHouseDiaryDetail(Integer id) {
		HashMap<String,Object> result = new HashMap<>();
		try{			
			HashMap<String,Object> param = new HashMap<>();			
			param.put("id", id);
			result = houseDiaryMapper.getHouseDiaryDetail(param);			
			result.put("houseDiaryFile", houseDiaryMapper.getHouseDiaryFile(param));
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("getHouseDiaryDetail Exception : " + e);
		}		
		return result;
	}
	
	@Override
	public HashMap<String,Object> getCropsDiaryDetail(Integer id) {
		HashMap<String,Object> result = new HashMap<>();
		try{			
			HashMap<String,Object> param = new HashMap<>();			
			param.put("id", id);
			result = houseDiaryMapper.getCropsDiaryDetail(param);
			result.put("houseDiaryFile", houseDiaryMapper.getCropsDiaryFile(param));
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("getCropsDiaryDetail Exception : " + e);
		}		
		return result;
	}


	@Override
	public HashMap<String,Object> getMonthlyHouseDiaryList(List<Integer> gsmKeyList,  Integer gsmKey, Integer houseId,Integer year, Integer month) {
		HashMap<String,Object> param = new HashMap<>();
		HashMap<String,Object> result = new HashMap<>();
		if(year != null && month != null){
			param = getMonthDate(year,month);	
		}
		if( gsmKey != null) {
			param.put("gsm_key", gsmKey);
		}
		if( gsmKeyList != null) {
			param.put("gsm_key_llist", gsmKeyList);
		}
		param.put("green_house_id", houseId);
		List<HashMap<String,Object>> diaryList =houseDiaryMapper.getMonthlyHouseDiaryList(param);
		List<HashMap<String,Object>> cropsDiaryList =houseDiaryMapper.getMonthlyCropsDiaryList(param);
		result.put("houseDiary", diaryList);
		result.put("cropsDiary", cropsDiaryList);
		return result;
		
	}

/*
 	//하나로 통합 
	@Override
	public List<HouseCropsDiaryVO> getMonthlyCropsDiaryList(Integer greenHouseId, Integer year, Integer month) {
	 	HashMap<String,Object> param = new HashMap<>();
	 	if(year != null && month != null){
	 		param = getMonthDate(year,month);	
	 	}
		param.put("house_id", greenHouseId);
		List<HouseCropsDiaryVO> list =houseDiaryMapper.getMonthlyCropsDiaryList(param); 
		return list;
	}
*/
	@Override
	public HashMap<String, Object> getHouseCropsInfo(Integer greenHouseId) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("green_house_id", greenHouseId);
		return houseDiaryMapper.getHouseCropsInfo(param);
	}
	
	
	@Override
	public List<HashMap<String, Object>> getCategoryList() {
		return houseDiaryMapper.getCategoryList22();		
	}
	
	@Override
	public Integer deleteHouseDiary(Integer id) {
		try{
			HashMap<String,Object> param = new HashMap<>();
			param.put("id", id);
			param.put("file_all", true);
			houseDiaryMapper.deleteHouseDiary(param);
			houseDiaryMapper.deleteHouseDiaryHouseMap(param);
			return houseDiaryMapper.deleteHouseDiaryFile(param);
		}catch(Exception e){
			log.debug("deleteHouseDiary Exception : " + e);
			return null;
		}
	}
	@Override
	public Integer deleteCropsDiary(Integer id) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("id", id);
		param.put("file_all", true);
		houseDiaryMapper.deleteCropsDiary(param);
		houseDiaryMapper.deleteCropsDiaryHouseMap(param);
		return houseDiaryMapper.deleteCropsDiaryFile(param);
	}
	
	private HashMap<String,Object> getMonthDate(Integer year,Integer month){
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
	public List<HashMap<String, Object>> getImageDiaryList(Integer houseId) {
		List<HashMap<String,Object>> imageDiaryList = new ArrayList<HashMap<String,Object>>();
		try{			
			HashMap<String,Object> param = new HashMap<>();
			param.put("houseId", houseId);
			imageDiaryList = houseDiaryMapper.getImageDiaryList(param);	
		}catch(Exception e){
			log.debug("getImageDiaryList Error = " + e.getMessage());			
		}		
		return imageDiaryList;
	}
}
