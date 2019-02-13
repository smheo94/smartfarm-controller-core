
package egovframework.customize.service.impl;

import egovframework.customize.service.HouseCropsDiaryVO;
import egovframework.customize.service.HouseDiaryService;
import egovframework.customize.service.HouseDiaryVO;
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
		}catch(Exception e){
			log.debug(e.getMessage());
		}
		return houseDiaryVO;
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
	public Integer insertDiaryFile(String contentType, Integer id, MultipartFile[] file) {
		Integer result=0;
		try{			
			
//			String fileName=file[i].getOriginalFilename();

//			if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") ||
//		            fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".gif") ||
//		            fileName.toLowerCase().endsWith(".bmp") || fileName.toLowerCase().endsWith(".hwp") ||
//		            fileName.toLowerCase().endsWith(".ppt") ||fileName.toLowerCase().endsWith(".pptx") ||		            
//		            fileName.toLowerCase().endsWith(".pdf") ||fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".txt")) {

			deleteDiaryFIleNotExists(file, id);
			for(int i=0; i<file.length; i++){
				if(!file[i].isEmpty()){
					String fileName=file[i].getOriginalFilename();
					byte[] bytes = file[i].getBytes();
					if(contentType.equals("11") || contentType.equals("21") || contentType.equals("1") || contentType.equals("2")){
						HouseDiaryVO houseDiaryVO = new HouseDiaryVO();
						houseDiaryVO.setId(id);
						houseDiaryVO.setFile(bytes);
						houseDiaryVO.setFileName(fileName);
						HouseDiaryVO dbhouseDiaryVO  = houseDiaryMapper.selectHouseDiaryFile(houseDiaryVO);
						if( dbhouseDiaryVO != null ) {
							result = houseDiaryMapper.updateHouseDiaryFile(houseDiaryVO);
						} else {
							result = houseDiaryMapper.insertHouseDiaryFile(houseDiaryVO);
						}
					}
					else if(contentType.equals("31") || contentType.equals("3")){
						HouseCropsDiaryVO houseCropsDiaryVO= new HouseCropsDiaryVO();
						houseCropsDiaryVO.setId(id);
						houseCropsDiaryVO.setFile(bytes);
						houseCropsDiaryVO.setFileName(fileName);
						HouseCropsDiaryVO dbhouseDiaryVO  = houseDiaryMapper.selectCropsDiaryFile(houseCropsDiaryVO);
						if( dbhouseDiaryVO != null ) {
							result = houseDiaryMapper.updateCropsDiaryFile(houseCropsDiaryVO);
						} else {
							result = houseDiaryMapper.insertCropsDiaryFile(houseCropsDiaryVO);
						}
					}
				}
			}

		}catch(Exception e){
			log.debug("insertDiaryFile Error = " + e);
		}
		return result;
	}

	private void deleteDiaryFIleNotExists(MultipartFile[] file, Integer diaryId) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("id", diaryId);
		final List<HashMap<String, Object>> houseDiaryFileList = houseDiaryMapper.getHouseDiaryFile(param);
		if( houseDiaryFileList != null && houseDiaryFileList.size() > 0  && file != null && file.length > 0 ) {
			for (HashMap<String, Object> houseDiaryFile : houseDiaryFileList) {
				String fileName = (String) houseDiaryFile.get("file_name");
				boolean isExists = false;
				for (MultipartFile filePart : file) {
					String newFileName = filePart.getOriginalFilename();
					if (Objects.equals(fileName, newFileName)) {
						isExists = true;
					}
				}
				if( isExists == false) {
					param.put("file_name", fileName);
					houseDiaryMapper.deleteHouseDiaryFile(param);
				}
			}
		}
	}

	@Override
	public Object updateDiaryFile(String contentType, Integer id, MultipartFile[] file) {
		Integer result=0;
		try{
			for(int i=0; i<file.length; i++){
				if(!file[i].isEmpty()){
					String fileName=file[i].getOriginalFilename();
					byte[] bytes = file[i].getBytes();
					if(contentType.equals("11") || contentType.equals("21") || contentType.equals("1") || contentType.equals("2")){
						HouseDiaryVO houseDiaryVO = new HouseDiaryVO();
						houseDiaryVO.setId(id);
						houseDiaryVO.setFile(bytes);
						houseDiaryVO.setFileName(fileName);
						result = houseDiaryMapper.updateHouseDiaryFile(houseDiaryVO);
					}
					else if(contentType.equals("31") || contentType.equals("3")){
						HouseCropsDiaryVO houseCropsDiaryVO= new HouseCropsDiaryVO();
						houseCropsDiaryVO.setId(id);
						houseCropsDiaryVO.setFile(bytes);
						houseCropsDiaryVO.setFileName(fileName);
						result = houseDiaryMapper.updateCropsDiaryFile(houseCropsDiaryVO);
					}
				}
			}
		}catch(Exception e){
			log.debug("updateDiaryFile Error : " + e.getMessage());
		}
		return result;
	}
	
	@Override
	public HouseDiaryVO updateHouseDiary(HouseDiaryVO houseDiaryVO) {
		try{
			houseDiaryMapper.updateHouseDiary(houseDiaryVO);				
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
			result.put("cropsDiaryFile", houseDiaryMapper.getCropsDiaryFile(param));
		}catch(Exception e){
			log.debug(e.getMessage());
			log.debug("getCropsDiaryDetail Exception : " + e);
		}		
		return result;
	}


	@Override
	public HashMap<String,Object> getMonthlyHouseDiaryList(Integer houseId,Integer year, Integer month) {
		HashMap<String,Object> param = new HashMap<>();
		HashMap<String,Object> result = new HashMap<>();
		if(year != null && month != null){
			param = getMonthDate(year,month);	
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
			houseDiaryMapper.deleteHouseDiary(param);
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
		houseDiaryMapper.deleteCropsDiary(param);
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
