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
@RequestMapping("/controller")
public class EgovSampleController {

	public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
	private static final String extraUrl = "";
	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;

	@Resource(name = "commonEnvService")
	private CommonEnvService commonEnvService;
	
	@Resource(name = "deviceService")
	private DeviceService deviceService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
	
//	private final static String outWeatherName = "outWeather";
//	private final static String thresholdName = "threshold";

	/**
	 * 공통 컴포넌트로 해결
	 * house_id , env_name, env_value_json, env_default_json
	 */
	
	/**
	 * @description 외부기상대 설정.
	 * @param applyDefault = 0 / 1 ( false / true )
	 * @param CommonEnvVO
	 * @return
	 */
	
	/**
	 * 외부기상대 , 임계치
	 * 읽기, 기본값 읽어오기 , 업데이트 , 기본값 저장
	 * 
	 **/
	@RequestMapping(value= "/commonEnv/{envName}/{applyDefault}", method = RequestMethod.PUT)
	@ResponseBody
	public Result envUpdate(HttpServletRequest request, @PathVariable String envName, int applyDefault, @RequestBody CommonEnvVO vo){
		try{
			//applyDefault 빼자
			commonEnvService.updateEnvInfo(vo);
			/*
			if(outWeatherName.equals(envName)){
				if(applyDefault == 0){
					commonEnvService.updateOutWeather(vo);	
				}else if(applyDefault == 1){//기본값으로 저장.
					commonEnvService.updateOutWeatherDefault(vo);
				}else{
					return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "applyDefault value is wrong");	
				}	
			}else if(thresholdName.equals(envName)){
				if(applyDefault == 0){
					commonEnvService.updateThreshold(vo);	
				}else if(applyDefault == 1){//기본값으로 저장.
					commonEnvService.updateThresholdDefault(vo);
				}else{
					return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "applyDefault value is wrong");	
				}
			}else{
				// envNAme ERROR
			}		
			*/
			return new Result<String>("OK", HttpStatus.OK, "SUCCESS");	
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	/**
	 * @description 외부기상대 정보 읽어오기.
	 * @param gsm_key
	 * @param readDefault = 0 / 1 ( false / true )
	 * 
	 **/
	@RequestMapping(value="/commonEnv/{envName}/{gsm_key}", method = RequestMethod.GET)
	@ResponseBody
	public Result getOutWeather(@PathVariable String envName, int readDefault, int gsmKey){
		try{
			HashMap<String,Object> param = new HashMap<String,Object>();
			param.put("gsmKey", gsmKey);
			param.put("envName", envName);			
			CommonEnvVO vo = commonEnvService.getOutweatherInfo(param);			
			return new Result<CommonEnvVO>("OK", HttpStatus.OK, vo);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}		
	}

	
	/** 
	 * @description sensorInfoList를 가지고 온다.
	 * @param 
	 * @return
	 */
//	@RequestMapping(value="/sensoInforList", method = RequestMethod.GET)
//	@ResponseBody
//	public Result getSensorInfoList(){
//		try{
//			HashMap<String,Object> result = commonEnvService.getSensorInfoList();			
//			return new Result<HashMap<String,Object>>("OK", HttpStatus.OK, result);
//		}catch(Exception e){
//			e.printStackTrace();
//			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//		}		
//	}
	
	/** 
	 * @description deviceInfoList를 가지고 온다.
	 * @param 
	 * @return
	 */
//	
//	@RequestMapping(value="/deviceInfoList", method = RequestMethod.GET)
//	@ResponseBody
//	public Result getDeviceInfoList(){
//		try{
//			HashMap<String,Object> result = commonEnvService.getDeviceInfoList();			
//			return new Result<HashMap<String,Object>>("OK", HttpStatus.OK, result);
//		}catch(Exception e){
//			e.printStackTrace();
//			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//		}		
//	}
	
	/**
	 * 
	 * @description 구동기모듈에 등록된 구동기 목록
	 * @param request
	 * @param controllerId
	 * @return
	 */
	@RequestMapping(value= "/deviceList/{controllerId}", method = RequestMethod.GET)
	@ResponseBody
	public Result selectDeviceList(HttpServletRequest request, @PathVariable Integer controllerId){
		try{
			List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();		
			result = commonEnvService.selectDeviceOfControlModule(controllerId);
			return new Result<List<HashMap<String,Object>>>("OK", HttpStatus.OK, result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Description 자기 자신이면 제어기(온실운영시스템)에 request 보내고 응답 제대로 오면 DB에 저장
	 * @param 
	 * 	actuatorModuleInfo
	 * 		* 추가 * description = 구동기 명  
	 * 
	 * 			
	 * @return
	 */
	
	@RequestMapping(value="/insertControllerDeviceList", method = RequestMethod.POST)
	@ResponseBody
	public Result insertControllerDeviceList(HttpServletRequest request, @RequestBody HashMap<String,Object> actuatorModuleInfo){
		try{
			// config 으로 빼던지 DB에서 가져오던지?
			String contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
			// 일단 DB에 넣고 제어기호출이면 제어기 응답보고 rollback 하던가 commit 하던가			
			HashMap<String,Object> result = commonEnvService.insertDeviceList(actuatorModuleInfo,contextPath);				
			return new Result<HashMap<String,Object>>("OK", HttpStatus.OK, result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Description 자기 자신이면 제어기(온실운영시스템)에 request 보내고 응답 제대로 오면 DB에 저장
	 * @param 
	 * 	actuatorModuleInfo
	 * 		* 추가 * id = 컨트롤러 id
	 * 
	 * 			
	 * @return
	 */
	@RequestMapping(value="/updateControllerDeviceList", method = RequestMethod.POST)
	@ResponseBody
	public Result updateControllerDeviceList(HttpServletRequest request, @RequestBody HashMap<String,Object> actuatorModuleInfo){
		try{
			// config 으로 빼던지 DB에서 가져오던지?
			String contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
			// 일단 DB에 넣고 제어기호출이면 제어기 응답보고 rollback 하던가 commit 하던가			
			HashMap<String,Object> result = commonEnvService.updateDeviceList(actuatorModuleInfo,contextPath);				
			return new Result<HashMap<String,Object>>("OK", HttpStatus.OK, result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Description 자기 자신이면 제어기(온실운영시스템)에 request 보내고 응답 제대로 오면 DB에 저장
	 * @param 
	 * 	actuatorModuleInfo
	 * 		* 추가 * id = 컨트롤러 id
	 * 
	 * 			
	 * @return
	 */
	@RequestMapping(value="/deleteControllerDeviceList/{controllerId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteControllerDeviceList(HttpServletRequest request, @PathVariable String controllerId){
		try{
			// config 으로 빼던지 DB에서 가져오던지?
			String contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
			// 일단 DB에 넣고 제어기호출이면 제어기 응답보고 rollback 하던가 commit 하던가			
			HashMap<String,Object> result = commonEnvService.deleteControllerInfo(controllerId,contextPath);				
			return new Result<HashMap<String,Object>>("OK", HttpStatus.OK, result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * @Description 센서모듈과 센서 리스트를 연결시킨다.
	 * @param
	 * 		sensorModule 

	 * @return
	 */
	@RequestMapping(value="/insertSensorList", method = RequestMethod.POST)
	@ResponseBody
	public Result insertSensorList(HttpServletRequest request, @RequestBody HashMap<String,Object> sensorModuleInfo){
		try{
			boolean isNew = true;
			String contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
//			String serviceUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
			// insert 와 update 동일 메소드 사용
			HashMap<String,Object> result = commonEnvService.upsertSensorList(sensorModuleInfo, contextPath, isNew);
			return new Result<HashMap<String,Object>>("OK", HttpStatus.OK, result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	/**
	 * @description 센서모듈 삭제.
	 * @param sensorControllerId
	 * @return
	 */
	@RequestMapping(value="/deleteSensorList/{sensorControllerId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result insertSensorList(HttpServletRequest request, @PathVariable String sensorControllerId){
		try{
			
			String contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
//			String serviceUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
			
			HashMap<String,Object> result = commonEnvService.deleteSensorList(sensorControllerId,contextPath);
			
			return new Result<HashMap<String,Object>>("OK", HttpStatus.OK, result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @description 제어모듈 선택시 구동기와 모듈의 정보
	 * savedData
	 * @param selectedControllerId // 모듈 ID
	 * @param controllerType //	
	 * 		단동형 구동기 : 11
	 * 		연동형 구동기 : 12
	 * 		과수형 구동기 : 13 
	 * 		노지형 구동기 : 14
	 * 		식물농장형 구동기 : 15
	 * 		내부센서 : 21
	 * 		외부센서 : 22
	 * 		저온저장고 센서 : 23
	 * 		재배베드 센서 : 24
	 * 		동우양액기 : 31
	 * 		누리양액기 : 32 
	 * @return
	 */
	@RequestMapping(value="/getControllerData/{selectedControllerId}/{controllerType}", method = RequestMethod.GET)
	@ResponseBody
	public Result selectedControllerInfo(HttpServletRequest request, @PathVariable String selectedControllerId, @PathVariable Integer controllerType){
		try{			
			List<HashMap<String,Object>> result = commonEnvService.selectedControllerInfo(selectedControllerId, controllerType);			
			return new Result<List<HashMap<String,Object>>>("OK", HttpStatus.OK, result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	 
	/**
	 * @description 센서별 장치 리스트, 컨트롤러 정보
	 * @param sensorType
	 * 		21 = 내부센서
	 * 		23 = warehoues
	 * 		24 = grwoingbed
	 * @return
	 */
	@RequestMapping(value="/sensorList/{sensorType}", method = RequestMethod.GET)
	@ResponseBody
	public Result selectSensorList(HttpServletRequest request, @PathVariable Integer sensorType){
		try{	
			HashMap<String,Object> result = new HashMap<String,Object>();
			if(sensorType == 21){
				// 내부센서
				 result = commonEnvService.selectSensorList(sensorType);
			}else if(sensorType == 23){
				//warehouse
				result = commonEnvService.selectSensorList(sensorType);
			}else if(sensorType == 24){
				// growingbed
				result = commonEnvService.selectSensorList(sensorType);
			}
			return new Result<HashMap<String,Object>>("OK", HttpStatus.OK, result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	
	/**
	 * @description 컨트롤러 정보
	 * @param request
	 * @param controllerInfoId
	 * @return
	 */
	@RequestMapping(value= {"/controllerList", "/controllerList/{controllerInfoId}"}, method = RequestMethod.GET)
	@ResponseBody
	public Result selectSensorList(HttpServletRequest request, @PathVariable Optional<Integer> controllerInfoId){
		try{
			List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
			if(controllerInfoId.isPresent()){
				//controllerId
				result = commonEnvService.getControllerListOfControlModule(new int[] {controllerInfoId.get()});
			}else{
				// all
				result = commonEnvService.getControllerListOfControlModule(null);
			}
			return new Result<List<HashMap<String,Object>>>("OK", HttpStatus.OK, result);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	
	/**
	 * 글 수정화면을 조회한다.
	 * @param id - 수정할 글 id
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping("/updateSampleView.do")
	public String updateSampleView(@RequestParam("selectedId") String id, @ModelAttribute("searchVO") SampleDefaultVO searchVO, Model model) throws Exception {
		SampleVO sampleVO = new SampleVO();
		sampleVO.setId(id);
		// 변수명은 CoC 에 따라 sampleVO
		model.addAttribute(selectSample(sampleVO, searchVO));
		return "sample/egovSampleRegister";
	}

	/**
	 * 글을 조회한다.
	 * @param sampleVO - 조회할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("sampleVO") - 조회한 정보
	 * @exception Exception
	 */
	public SampleVO selectSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO) throws Exception {
		return sampleService.selectSample(sampleVO);
	}

	/**
	 * 글을 수정한다.
	 * @param sampleVO - 수정할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/updateSample.do")
	public String updateSample(@ModelAttribute("searchVO") SampleDefaultVO searchVO, SampleVO sampleVO, BindingResult bindingResult, Model model, SessionStatus status)
			throws Exception {

		beanValidator.validate(sampleVO, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("sampleVO", sampleVO);
			return "sample/egovSampleRegister";
		}

		sampleService.updateSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}

	/**
	 * 글을 삭제한다.
	 * @param sampleVO - 삭제할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/deleteSample.do")
	public String deleteSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO, SessionStatus status) throws Exception {
		sampleService.deleteSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}

}
