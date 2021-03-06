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
import com.kt.cmmn.util.InterceptPost;
import com.kt.cmmn.util.InterceptPre;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.config.Message;
import com.kt.smartfarm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;


@Slf4j
@Controller
@RequestMapping(value = {"/device"})
//@RequestMapping("/env/{gsm_key}/device")
public class DeviceEnvController {

    public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
    private static final String extraUrl = "";

    @Autowired
    private Message msg;
    @Resource(name = "deviceEnvService")
    private DeviceEnvService deviceEnvService;

    @Resource(name = "authCheckService")
    private AuthCheckService authCheckService;

    /**
     * 구동기,센서 등록
     *
     * @param device *
     * @return
     **/
    @SuppressWarnings("unchecked")
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    //@ApiOperation(value = "구동기,센서 등록 : OLD( /gsm/{gsm_key}/")
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<List<DeviceEnvVO>> insert(@RequestBody List<DeviceEnvVO> device) {
        try {
            return new Result(deviceEnvService.insert(device));
        } catch (Exception e) {
            log.warn("Exception :{}", device, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, device);
        }
    }

    /**
     * @param gsmKey
     * @return
     * @description 저장된 구동모듈에 해당하는 deviceList 가져ㅑ오기
     */
    @RequestMapping(value = "/gsm/{gsm_key}", method = RequestMethod.GET)
    //@ApiOperation(value = "GMS 에 저장된 전체 Device 가져오기")
    @ResponseBody
    public Result<List<DeviceEnvVO>> list(@PathVariable("gsm_key") Long gsmKey,
                                          @RequestParam("withVDeviceList") Boolean withVDeviceList,
                                          @RequestParam(required = false) Boolean withEDeviceList) {
        try {
            if (!authCheckService.authCheck(gsmKey, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(deviceEnvService.list(gsmKey, null, withVDeviceList, withEDeviceList));
        } catch (Exception e) {
            log.warn("Exception :{}, {}, {}", gsmKey, withEDeviceList, withVDeviceList, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    /**
     * @param gsmKey
     * @param controllerId
     * @param device
     * @return
     * @description 구동기 모듈에 해당하는 구동기,센서 수정
     */
    @RequestMapping(value = "/gsm/{gsm_key}/controller/{controllerId}", method = RequestMethod.PUT)
    //@ApiOperation(value = "구동기 모듈에 해당하는 구동기,센서 수정 : OLD( /gsm/{gsm_key}/{controllerId}")
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<DeviceEnvVO> update(@PathVariable("gsm_key") Long gsmKey, @PathVariable("controllerId") String controllerId, @RequestBody List<DeviceEnvVO> device) {
        try {
            if (!authCheckService.authCheck(gsmKey, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(deviceEnvService.update(device)); // gsmKey, controllerId, deviceId 기준으로 업데이트
        } catch (Exception e) {
            log.warn("Exception :{}, {}, {}", controllerId, device, gsmKey, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, device);
        }
    }

    /**
     * @param gsmKey
     * @param controllerId
     * @return
     * @description 저장된 구동모듈에 해당하는 deviceList 가져ㅑ오기
     */
    @RequestMapping(value = "/gsm/{gsm_key}/controller/{controllerId}", method = RequestMethod.GET)
    //@ApiOperation(value = "저장된 구동모듈에 해당하는 deviceList 가져오기 : OLD( /gsm/{gsm_key}/list/{controllerId}")
    @ResponseBody
    public Result<List<DeviceEnvVO>> list(@PathVariable("gsm_key") Long gsmKey, @PathVariable("controllerId") Long controllerId,
                                          @RequestParam(value = "withVDeviceList", required = false) Boolean withVDeviceList) {
        try {
            if (!authCheckService.authCheck(gsmKey, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(deviceEnvService.list(gsmKey, controllerId, withVDeviceList, null));
        } catch (Exception e) {
            log.warn("Exception :{}, {}, {}", gsmKey, controllerId, withVDeviceList, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

//	@RequestMapping(value= "/gsm/{gsm_key}/controller/{controllerId}", method = RequestMethod.GET)
//	@ApiOperation(value = "컨트롤러에 등록된 Device동작하나(?): OLD( /gsm/{gsm_key}/{controllerId})")
//	//@RequestMapping(value= "/{controllerId}", method = RequestMethod.GET)
//	@ResponseBody
//	public Result<DeviceEnvVO> getDevice( @PathVariable("gsm_key") Long gsmKey,  @PathVariable("controllerId") Long controllerId){
//		try {
//			return new Result(deviceEnvService.getDevice(gsmKey, controllerId));
//		} catch(Exception e) { log.warn("Exception :{}", a, e);
//			return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
//		}
//	}

    /**
     * 구동모듈에 해당하는 device 삭제
     *
     * @param gsmKey
     * @param controllerId
     * @return
     */
    @RequestMapping(value = "/gsm/{gsm_key}/controller/{controllerId}", method = RequestMethod.DELETE)
    //@ApiOperation(value = "구동모듈에 해당하는 Device 삭제: OLD( /gsm/{gsm_key}/{controllerId})")
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<DeviceEnvVO> deleteControllerDeivces(@PathVariable("gsm_key") Long gsmKey, @PathVariable("controllerId") Long controllerId) {
        try {
            if (!authCheckService.authCheck(gsmKey, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(deviceEnvService.deleteControllerDeivces(gsmKey, controllerId));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", gsmKey, controllerId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @param vo
     * @return
     * @description 가상장치 등록
     */
    @RequestMapping(value = "/{parentDeviceId}/relationdevices", method = RequestMethod.POST)
    //Device Type 별로 입력 @RequestMapping(value= "/vDeviceList", method = RequestMethod.POST)
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<List<VDeviceEnvVO>> vDeviceList(@PathVariable("parentDeviceId") Long parentDeviceId, @RequestBody List<VDeviceEnvVO> vo) {
        try {
            vo.stream().forEach(v -> v.setParentDeviceId(parentDeviceId));
            deviceEnvService.deleteVDeviceEnv(null, parentDeviceId, null, null);
            return new Result(deviceEnvService.insertVDeviceEnv(vo));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", parentDeviceId, vo, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    @RequestMapping(value = "/{deviceId}/relationdevices", method = RequestMethod.PUT)
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<VDeviceEnvVO> updateVDeviceList(@PathVariable("deviceId") Long deviceId, @RequestBody VDeviceEnvVO vo) {
        try {
            vo.setParentDeviceId(deviceId);
            return new Result(deviceEnvService.updateVDeviceEnv(vo));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", deviceId, vo, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    @RequestMapping(value = "/{id}/relationdevices", method = RequestMethod.DELETE)
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<VDeviceEnvVO> deleteVDeviceList(@PathVariable("id") Long id) {
        try {
            return new Result(deviceEnvService.deleteVDeviceEnv(id, null, null, null));
        } catch (Exception e) {
            log.warn("Exception :{}", id, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    @Deprecated
    @RequestMapping(value = "/{parentId}/relationdevicesbyparent", method = RequestMethod.DELETE)
    @ResponseBody
    //@InterceptPre
    @InterceptLog
    public Result<Integer> deleteVDeviceListByParentId(@PathVariable("parentId") Long parentId) {
        try {
            //return new Result(deviceEnvService.deleteVDeviceEnv(null, parentId, null, null));
            return new Result(1);
        } catch (Exception e) {
            log.warn("Exception :{}", parentId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @param deviceId
     * @return
     * @description 가상장치 등록
     */
    @RequestMapping(value = "/{deviceId}/relationdevices", method = RequestMethod.GET)
    //Device Type 별로 입력 @RequestMapping(value= "/vDeviceList", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<VDeviceInfoVO>> vDeviceList(@PathVariable("deviceId") Long deviceId) {
        try {
            return new Result(deviceEnvService.getVDeviceEnvList(deviceId));
        } catch (Exception e) {
            log.warn("Exception :{}", deviceId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @param deviceId
     * @return
     * @description 장치조회
     */
    @RequestMapping(value = "/{deviceId}", method = RequestMethod.GET)
    //Device Type 별로 입력 @RequestMapping(value= "/vDeviceList", method = RequestMethod.POST)
    @ResponseBody
    public Result<DeviceEnvVO> vDeviceList(@PathVariable("deviceId") Long deviceId,
                                           @RequestParam(value = "withVDeviceList", required = false) Boolean withVDeviceList) {
        try {
            return new Result(deviceEnvService.getDevice(deviceId, withVDeviceList));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", deviceId, withVDeviceList, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    /**
     * @param deviceId
     * @return
     * @description 장치조회
     */
    @RequestMapping(value = "/{deviceId}", method = RequestMethod.DELETE)
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<DeviceEnvVO> deleteDevice(@PathVariable("deviceId") Long deviceId, @Param("gsmKey") Long gsmKey) {
        try {
            if (!authCheckService.authCheck(gsmKey, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(deviceEnvService.deleteDevice(deviceId));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", deviceId, gsmKey, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

//	@RequestMapping(value= "/deviceTypeList", method = RequestMethod.GET)
//	@ResponseBody
//	public Result<DeviceEnvVO> deviceTypeList(){
//		try {
//			return new Result(deviceEnvService.getDevice(deviceId, withVDeviceList));
//		} catch(Exception e) { log.warn("Exception :{}", a, e);
//			return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
//		}
//	}

    /**
     * @param vo
     * @return
     * @author yechae
     * @description 전류감지기 등록(electric currnet)
     */
    @RequestMapping(value = "/{deviceId}/electricdevices", method = RequestMethod.POST)
    //@ApiOperation(value="전류 감지기 등록")
    @ResponseBody
    @InterceptPost
    public Result<List<EDeviceEnvVO>> insertEDeviceList(@PathVariable("deviceId") Long deviceId, @RequestBody List<EDeviceEnvVO> vo) {
        try {
            return new Result(deviceEnvService.insertEDeviceEnv(vo));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", deviceId, vo, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @param deviceId, vo
     * @return
     * @author yechae
     * @description 전류감지기 수정(electric currnet)
     */

    @RequestMapping(value = "/{deviceId}/electricdevice", method = RequestMethod.PUT)
    //@ApiOperation(value="전류 감지기 수정")
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<EDeviceEnvVO> updateEDeviceList(@PathVariable("deviceId") Long deviceId, @RequestBody EDeviceEnvVO vo) {
        try {
            return new Result(deviceEnvService.updateEDeviceEnv(vo));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", deviceId, vo, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @param id
     * @return
     * @author yechae
     * @description 전류감지기 삭제 by deviceId(electric currnet)
     */
    @RequestMapping(value = "/{id}/electricdevice", method = RequestMethod.DELETE)
    //@ApiOperation(value = "전류 감지기 삭제 ")
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<EDeviceEnvVO> deleteEDevice(@PathVariable("id") Long id) {
        try {
            return new Result(deviceEnvService.deleteEDeviceEnv(id));
        } catch (Exception e) {
            log.warn("Exception :{}", id, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @param deviceId
     * @return
     * @author yechae
     * @description 전류감지기 조회(electric current)
     */
    @RequestMapping(value = "/{deviceId}/electricdevices", method = RequestMethod.GET)
    //@ApiOperation(value = "전류감지기 조회")
    @ResponseBody
    public Result<List<EDeviceEnvVO>> eDeviceList(@PathVariable("deviceId") Long deviceId) {
        try {
            return new Result(deviceEnvService.getEDeviceEnvList(deviceId));
        } catch (Exception e) {
            log.warn("Exception :{}", deviceId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @param deviceId
     * @return
     * @author yechae
     * @description 전류감지기 삭제 by p_id(electric currnet)
     */
    @RequestMapping(value = "/{deviceId}/electricdevices", method = RequestMethod.DELETE)
    //@ApiOperation(value = "Device의 전류 감지기 전체 삭제")
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<EDeviceEnvVO> deleteEDevices(@PathVariable("deviceId") Long deviceId) {
        try {
            return new Result(deviceEnvService.deleteEDevicesEnv(deviceId));
        } catch (Exception e) {
            log.warn("Exception :{}", deviceId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

}
