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

import com.kt.cmmn.util.Result;
import com.kt.smartfarm.config.EncryptConfig;
import com.kt.smartfarm.config.Message;
import com.kt.smartfarm.config.SmartfarmInterceptorConfig;
import com.kt.smartfarm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

//import io.swagger.annotations.ApiOperation;


@Slf4j
@Controller
@RequestMapping(value = "")
public class CodeDataController {

    @Autowired
    private Message msg;
    @Resource(name = "deviceEnvService")
    private DeviceEnvService deviceEnvService;


    @Resource(name = "controlLogicSettingService")
    private ControlLogicSettingService controlLogicSettingService;

    @Autowired
    SmartfarmInterceptorConfig smartfarmInterceptorConfig;

    /**
     * @param request
     * @param controllerType 1 : 단동형 Only,
     *                       2 : 연동형 Only ,
     *                       3 : 연동/단동형 Only,
     *                       4: 과수원 Only,
     *                       7 : 싱글,연동,과수 사용,
     *                       8 : 노지 Only,
     *                       15 : 단동,연동,과수,노지 사용
     * @param kind           sensor_inner
     *                       sensor_outer
     *                       info
     *                       actuator
     *                       nutrient
     *                       v_device
     * @return
     * @description 구동기 타입에 따른 deviceList
     */
    @RequestMapping(value = "/housetype/{houseType}/kind/{kind}/devicetype", method = RequestMethod.GET)
    //@ApiOperation(value = "하우스 타입/구동기종류별 구동기 Type 리스트: OLD( /gsm/{gsm_key}/listType/{houseType}/{kind}")
    //@RequestMapping(value= "/listType/{houseType}/{kind}", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<DeviceTypeVO>> getDeviceList(@PathVariable("houseType") String houseType, @PathVariable String kind) {
        try {

            return new Result(deviceEnvService.getDeviceTypeByHouseType(houseType, kind));
        } catch (Exception e) {
            log.warn("Exception :{}, {}", houseType, kind, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * controllerType 하고 kind 가져가는 api 만들어줘
     */
    @RequestMapping(value = "/housetype", method = RequestMethod.GET)
    //@ApiOperation(value = "HouseType 리스트: OLD( /gsm/{gsm_key}/houseTypeKindInfo")
    @ResponseBody
    public Result<HashMap<String, Object>> houseTypeKindInfo() {
        try {
            return new Result(deviceEnvService.getHouseTypeKindInfo());
        } catch (Exception e) {
            log.warn("Exception :", e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    @RequestMapping(value = "/devicetype", method = RequestMethod.GET)
    //@ApiOperation(value = "Device Type 리스트: OLD( /gsm/{gsm_key}/deviceTypeList")
    @ResponseBody
    public Result<List<HashMap<String, Object>>> deviceTypeList() {
        try {
            return new Result(deviceEnvService.getDeviceTypeList());
        } catch (Exception e) {
            log.warn("Exception :", e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    /**
     * @return
     * @description 가상 장치 리스트
     */
    @RequestMapping(value = "/vdeviceinfo", method = RequestMethod.GET)
    //@ApiOperation(value = "가상 장치 종류 리스트: OLD( /gsm/{gsm_key}/vDeviceList")
    @ResponseBody
    public Result<List<VDeviceInfoVO>> vDeviceList() {
        try {
            return new Result(deviceEnvService.getVDeviceList());
        } catch (Exception e) {
            log.warn("Exception :", e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    /**
     * @return
     * @description 컨트롤 셋팅 오퍼레이터 리스트
     */
    @RequestMapping(value = "/control_setting_operator", method = RequestMethod.GET)
    //@ApiOperation(value = "컨트롤 셋팅 오퍼레이터 리스트")
    @ResponseBody
    public Result<List<ControlSettingOperatorVO>> listControlSettingOperator() {
        try {
            return new Result(controlLogicSettingService.listControlSettingOperation());
        } catch (Exception e) {
            log.warn("Exception :", e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    /**
     * @return
     * @description 컨트롤 셋팅 오퍼레이터 리스트
     */
    @RequestMapping(value = "/server_type", method = RequestMethod.GET)
    //@ApiOperation(value = "컨트롤 셋팅 오퍼레이터 리스트")
    @ResponseBody
    public Result<String> serverType() {
        try {
            return new Result(smartfarmInterceptorConfig.getSystemType());
        } catch (Exception e) {
            log.warn("Exception :", e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @return
     * @description 컨트롤 셋팅 오퍼레이터 리스트
     */
    @RequestMapping(value = "/code/liquid", method = RequestMethod.GET)
    //@ApiOperation(value = "액체 비료 목록을 전달")
    @ResponseBody
    public Result<List<LiquidVO>> liquidList(@RequestParam(name = "liquidId", required = false) String liquidId) {
        try {
            return new Result(controlLogicSettingService.getCdLiquid(liquidId));
        } catch (Exception e) {
            log.warn("Exception :{}", liquidId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @return
     * @description 컨트롤 셋팅 오퍼레이터 리스트
     */
    @RequestMapping(value = "/enc_text", method = RequestMethod.GET)
    //@ApiOperation(value = "액체 비료 목록을 전달")
    @ResponseBody
    public Result<String> encText(@RequestParam(name = "text", required = true) String originText) {
        try {
            log.info("receive text : {}", originText);
            return new Result(EncryptConfig.encryptor.encrypt(originText));
        } catch (Exception e) {
            log.warn("Exception :{}", originText, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


}
