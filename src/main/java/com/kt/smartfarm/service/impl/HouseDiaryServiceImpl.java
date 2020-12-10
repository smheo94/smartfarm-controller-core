
package com.kt.smartfarm.service.impl;

import com.kt.smartfarm.mapper.HouseDiaryMapper;
import com.kt.smartfarm.service.HouseDiaryService;
import com.kt.smartfarm.service.HouseDiaryVO;
import com.kt.smartfarm.service.SweetContentVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;


@Service("houseDiaryService")
public class HouseDiaryServiceImpl extends EgovAbstractServiceImpl implements HouseDiaryService {
    private static final Logger log = LoggerFactory.getLogger(HouseDiaryServiceImpl.class);

	@Resource(name="houseDiaryMapper")
    private HouseDiaryMapper	houseDiaryMapper;


	@Override
	@Transactional
	@EventListener
	public HouseDiaryVO insertHouseDiary(HouseDiaryVO houseDiaryVO) {
		try{
            if( houseDiaryVO.getGreenHouseId() != null && ( houseDiaryVO.getHouseIdList() == null  || houseDiaryVO.getHouseIdList().size() == 0 ) )  {
                houseDiaryVO.setHouseIdList(Arrays.asList(houseDiaryVO.getGreenHouseId()));
            }
            if( "4".equalsIgnoreCase(houseDiaryVO.getDiaryTypeId())) {
				Map<String,Object> diaryData = houseDiaryVO.getDiaryData();
				List<String> imageUrl = (List<String>)diaryData.getOrDefault("imageUrl", new ArrayList<String>());
				if( imageUrl != null && imageUrl.size() > 0 ) {
					houseDiaryVO.setCctvImageUrl(imageUrl.get(0));
					if (houseDiaryMapper.existsImageDiaryImageUrl(houseDiaryVO) > 0) {
						log.info("Already Exist Image Url : {}", houseDiaryVO);
						return houseDiaryVO;
					} else if (houseDiaryMapper.updateImageDiary(houseDiaryVO) > 0) {
						log.info("Exists House Diary,   so Update : {}", houseDiaryVO);
						return houseDiaryVO;
					}
				}
			}
			houseDiaryMapper.insertHouseDiary(houseDiaryVO);
			houseDiaryMapper.insertHouseDiaryHouseMap(houseDiaryVO);

		}catch(Exception e){
			log.warn("insertHouseDiary" , e);
		}
		return houseDiaryVO;
	}
//	@Override
//	public HouseCropsDiaryVO insertCropsDiary(HouseCropsDiaryVO houseCropsVO) {
//		try{
//			log.info("Insert Crops Diary : {}", houseCropsVO);
//            if( houseCropsVO.getGreenHouseId() != null && ( houseCropsVO.getHouseIdList() == null  || houseCropsVO.getHouseIdList().size() == 0 ) )  {
//                houseCropsVO.setHouseIdList(Arrays.asList(houseCropsVO.getGreenHouseId()));
//            }
//			houseDiaryMapper.insertCropsDiary(houseCropsVO);
//			houseDiaryMapper.insertCropsDiaryHouseMap(houseCropsVO);
//		}catch(Exception e){
//			log.warn("insertCropsDiary" , e);
//		}
//		return houseCropsVO;
//	}
	@Override
	public Integer deleteDiaryFileList(String contentType, Integer id, List<Integer> fileIdList) {
		try {
			log.info("Delete Diary File : {}, {}, {}", contentType, id, fileIdList);
			HashMap<String, Object> param = new HashMap<>();
			param.put("id", id);
			param.put("file_idx_list", fileIdList);
			return houseDiaryMapper.deleteHouseDiaryFile(param);
//			if(contentType.equals("11") || contentType.equals("21") || contentType.equals("1") || contentType.equals("2")) {
////				return houseDiaryMapper.deleteHouseDiaryFile(param);
////			} else if(contentType.equals("31") || contentType.equals("3")){
////				return houseDiaryMapper.deleteCropsDiaryFile(param);
////			}
		} catch(Exception e) {
			log.warn("deleteDiaryFileList" , e);
			return -1;
		}
//		return 0;
	}
	@Override
	public Integer insertDiaryFile(String contentType, Integer id, MultipartFile[] file) {
		log.info("Insert Diary Diary : {}, {}, {}", contentType, id, file);
		Integer result=0;
		try{
			//deleteDiaryFIleNotExists(file, id);
			for(int i=0; i<file.length; i++){
				if(!file[i].isEmpty()){
					String fileName=file[i].getOriginalFilename();
					byte[] bytes = file[i].getBytes();
//					if( contentType == null ) {
					HouseDiaryVO houseDiaryVO = new HouseDiaryVO();
					houseDiaryVO.setId(id);
					houseDiaryVO.setFile(bytes);
					houseDiaryVO.setFileName(fileName);
					result = houseDiaryMapper.insertHouseDiaryFile(houseDiaryVO);
//					}
//					else if(contentType.equals("11") || contentType.equals("21") || contentType.equals("1") || contentType.equals("2")){
//						HouseDiaryVO houseDiaryVO = new HouseDiaryVO();
//						houseDiaryVO.setId(id);
//						houseDiaryVO.setFile(bytes);
//						houseDiaryVO.setFileName(fileName);
////						HouseDiaryVO dbhouseDiaryVO  = houseDiaryMapper.selectHouseDiaryFile(houseDiaryVO);
////						if( dbhouseDiaryVO != null ) {
////							result = houseDiaryMapper.updateHouseDiaryFile(houseDiaryVO);
////						} else {
//							result = houseDiaryMapper.insertHouseDiaryFile(houseDiaryVO);
////						}
//					}
//					else if(contentType.equals("31") || contentType.equals("3")){
//						HouseCropsDiaryVO houseCropsDiaryVO= new HouseCropsDiaryVO();
//						houseCropsDiaryVO.setId(id);
//						houseCropsDiaryVO.setFile(bytes);
//						houseCropsDiaryVO.setFileName(fileName);
////						HouseCropsDiaryVO dbhouseDiaryVO  = houseDiaryMapper.selectCropsDiaryFile(houseCropsDiaryVO);
////						if( dbhouseDiaryVO != null ) {
////							result = houseDiaryMapper.updateCropsDiaryFile(houseCropsDiaryVO);
////						} else {
//							result = houseDiaryMapper.insertCropsDiaryFile(houseCropsDiaryVO);
////						}
//					}
				}
			}

		}catch(Exception e){
			log.warn("insertDiaryFile" , e);
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
			log.warn("updateHouseDiary" , e);
		}		
		return houseDiaryVO;
	}
//	@Override
//	public HouseCropsDiaryVO updateCropsDiary(HouseCropsDiaryVO houseCropsVO) {
//		try{
//			log.info("Update Crops Diary : {}", houseCropsVO);
//			houseDiaryMapper.updateCropsDiary(houseCropsVO);
//			HashMap<String,Object> param = new HashMap<>();
//			param.put("id", houseCropsVO.getId());
//			houseDiaryMapper.deleteCropsDiaryHouseMap(param);
//			houseDiaryMapper.insertCropsDiaryHouseMap(houseCropsVO);
//		}catch(Exception e){
//			log.warn("updateCropsDiary" , e);
//		}
//		return houseCropsVO;
//	}

	
	
	
	@Override
	public HouseDiaryVO getHouseDiaryDetail(Integer id) {
		HouseDiaryVO result = null;
		try{			
			HashMap<String,Object> param = new HashMap<>();			
			param.put("id", id);
			result = houseDiaryMapper.getHouseDiaryDetail(param);
			result.setHouseDiaryFile( houseDiaryMapper.getHouseDiaryFile(param));
		}catch(Exception e){
			log.warn("getHouseDiaryDetail" , e);
		}		
		return result;
	}
	
//	@Override
//	public HouseCropsDiaryVO getCropsDiaryDetail(Integer id) {
//		HouseCropsDiaryVO result = null;
//		try{
//			HashMap<String,Object> param = new HashMap<>();
//			param.put("id", id);
//			result = houseDiaryMapper.getCropsDiaryDetail(param);
//			result.setHouseDiaryFile( houseDiaryMapper.getCropsDiaryFile(param));
//		}catch(Exception e){
//			log.debug(e.getMessage());
//			log.debug("getCropsDiaryDetail Exception : " + e);
//		}
//		return result;
//	}

	@Override
	public HashMap<String,Object> getHouseDiaryList(HashMap<String,Object> param) {
		HashMap<String,Object> result = new HashMap<>();
		List<HashMap<String,Object>> diaryList =houseDiaryMapper.getHouseDiaryList(param);
		//List<HashMap<String,Object>> cropsDiaryList =houseDiaryMapper.getCropsDiaryList(param);
		result.put("houseDiary", diaryList);
		result.put("cropsDiary", new ArrayList<HashMap<String,Object>>());
		return result;
	}

	@Override
	public HashMap<String,Object> getMonthlyHouseDiaryList(List<Long> gsmKeyList,  Long gsmKey, Long houseId,Integer year, Integer month) {
		HashMap<String,Object> param = new HashMap<>();
		if(year != null && month != null){
			param = getMonthDate(year,month);	
		}
		if( gsmKey != null) {
			param.put("gsm_key", gsmKey);
		}
		if( gsmKeyList != null) {
			param.put("gsm_key_list", gsmKeyList);
		}
		param.put("green_house_id", houseId);
		return this.getHouseDiaryList(param);
	}

/*
 	//하나로 통합 
	@Override
	public List<HouseCropsDiaryVO> getMonthlyCropsDiaryList(Long greenHouseId, Integer year, Integer month) {
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
	public HashMap<String, Object> getHouseCropsInfo(Long greenHouseId) {
		HashMap<String,Object> param = new HashMap<>();
		param.put("green_house_id", greenHouseId);
		return houseDiaryMapper.getHouseCropsInfo(param);
	}
	
	
//	@Override
//	public List<HashMap<String, Object>> getCategoryList() {
//		return houseDiaryMapper.getCategoryList22();
//	}
	
	@Override
	public Integer deleteHouseDiary(Integer id) {
		try{
			log.info("Delete House Diary : {}", id);
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
//	@Override
//	public Integer deleteCropsDiary(Integer id) {
//		log.info("Delete Crops Diary : {}", id);
//		HashMap<String,Object> param = new HashMap<>();
//		param.put("id", id);
//		param.put("file_all", true);
//		houseDiaryMapper.deleteCropsDiary(param);
//		houseDiaryMapper.deleteCropsDiaryHouseMap(param);
//		return houseDiaryMapper.deleteCropsDiaryFile(param);
//	}
	
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
	public List<HashMap<String, Object>> getImageDiaryList(Long houseId) {
		List<HashMap<String,Object>> imageDiaryList = new ArrayList<>();
		try{			
			HashMap<String,Object> param = new HashMap<>();
			param.put("house_id", houseId);
			imageDiaryList = houseDiaryMapper.getImageDiaryList(param);	
		}catch(Exception e){
			log.debug("getImageDiaryList Error = " + e.getMessage());			
		}		
		return imageDiaryList;
	}

	@Override
	public List<HashMap<String, Object>>  getImageDiaryListV2(Long gsmKey, List<Long> houseIdList, Long fromDate, Long toDate, Integer page, Integer size, Boolean total_count) {
        List<HashMap<String,Object>> imageDiaryList = new ArrayList<>();
        try{
            HashMap<String,Object> param = new HashMap<>();
            param.put("gsm_key", gsmKey);
            param.put("house_id_list", houseIdList);
            if( fromDate != null && fromDate > 0  ) {
                param.put("from_date", new Date(fromDate));
            }
            if( toDate != null && toDate > 0) {
                param.put("to_date", new Date(toDate));
            }
            if( page != null ) {
                param.put("page_start", page * (size == null ? 10 : size));
                param.put("page_end", (size == null ? 10 : size));
            }
			param.put("total_count", total_count);
            imageDiaryList = houseDiaryMapper.getImageDiaryList(param);
        }catch(Exception e){
            log.debug("getImageDiaryList Error = " + e.getMessage());
        }
        return imageDiaryList;
	}

	@Override
	public List<SweetContentVO> getSweetContentGraphList(Long gsmKey, Long houseId,
														 String fromDate, String toDate) {
		List<SweetContentVO> lastSweetContentLastList = houseDiaryMapper.getSweetContentLastList(gsmKey, houseId, fromDate, toDate);
		return lastSweetContentLastList;
	}


}
