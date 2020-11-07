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

import com.kt.cmmn.util.*;
import com.kt.smartfarm.config.Message;
import com.kt.smartfarm.config.SmartfarmInterceptorConfig;
import com.kt.smartfarm.lamplog.LampLog;
import com.kt.smartfarm.lamplog.models.LOG_SECURITY_EVENT;
import com.kt.smartfarm.lamplog.models.LOG_SECURITY_TYPE;
import com.kt.smartfarm.lamplog.models.LOG_TYPE;
import com.kt.smartfarm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@Slf4j
@Controller
@RequestMapping(value = "/{gsm_key}/house")
public class HouseEnvController {

    public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
    private static final String extraUrl = "";

    @Autowired
    private Message msg;
    @Resource(name = "houseEnvService")
    private HouseEnvService houseEnvService;
    @Resource(name = "authCheckService")
    private AuthCheckService authCheckService;
    @Autowired
    SmartfarmInterceptorConfig config;

    @Autowired
    private LampLogService lampLogService;

    /**
     * @param gsmKey
     * @return
     * @description 온실 등록 ( 제어기 선택은 어디서? 밑에서~ )
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<HouseEnvVO> insert(@PathVariable("gsm_key") Long gsmKey, @RequestBody HouseEnvVO house) {
        LampLog lampLog = lampLogService.createTransactionLog("house.create", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
        try {
            lampLog.addSecurity(LOG_SECURITY_TYPE.PRCS, LOG_SECURITY_EVENT.CREATE, String.valueOf(gsmKey));
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                lampLog.forbidden();
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            lampLog.success();
            return new Result(houseEnvService.insert(house));
        } catch (Exception e) {
            log.warn("insert :{}, {}", gsmKey, house, e);
            lampLog.exception(e.getMessage());
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, house);
        } finally {
            lampLogService.sendLog(lampLog);
        }

    }

    /**
     * @param
     * @return
     * @description 온실 등록후 device 와 온실 link
     */
    @RequestMapping(value = "/linkDevice", method = RequestMethod.POST)
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<HashMap<String, Object>> houesMapDeviceInsert(@PathVariable("gsm_key") Long gsmKey, @RequestBody HashMap<String, Object> map) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseEnvService.insertHouseDeviceMap(map));
        } catch (Exception e) {
            log.warn("Insert linkDevice :{}, {}", gsmKey, map, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, map);
        }
    }

    /**
     * @param
     * @return
     * @description 온실 등록후 device 와 온실 link
     */
    @RequestMapping(value = "/linkDevice", method = RequestMethod.DELETE)
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<HashMap<String, Object>> houesMapDeviceUpdate(@PathVariable("gsm_key") Long gsmKey, @RequestBody HashMap<String, Object> map) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseEnvService.deleteHouseDeviceMap(map));
        } catch (Exception e) {
            log.warn("Del LinkDevice :{}, {}", gsmKey,map, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, map);
        }
    }

    /**
     * @param gsmKey
     * @param house
     * @return
     * @description 온실 update
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<HouseEnvVO> update(@PathVariable("gsm_key") Long gsmKey, @RequestBody HouseEnvVO house) {
        LampLog lampLog = lampLogService.createTransactionLog("house.update", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
        try {
            lampLog.addSecurity(LOG_SECURITY_TYPE.PRCS, LOG_SECURITY_EVENT.UPDATE, String.valueOf(gsmKey));
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                lampLog.forbidden();
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            lampLog.success();
            return new Result(houseEnvService.update(house)); // gsmKey, id기준으로 업데이트
        } catch (Exception e) {
            log.warn("Update House :{}, {}", gsmKey, house, e);
            lampLog.exception(e.getMessage());
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, house);
        } finally {
            lampLogService.sendLog(lampLog);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<HashMap<String, Object>>> list(@PathVariable("gsm_key") Long gsmKey) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseEnvService.list(gsmKey, true, true, config.isSmartfarmSystem()));
        } catch (Exception e) {
            log.warn("List House :{}", gsmKey, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    @RequestMapping(value = "/{greenHouseId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<HashMap<String, Object>> get(@PathVariable("gsm_key") Long gsmKey, @PathVariable("greenHouseId") Long greenHouseId) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result<HashMap<String, Object>>(houseEnvService.get(gsmKey, greenHouseId, config.isSmartfarmSystem()));
        } catch (Exception e) {
            log.warn("Get :{}, {}", gsmKey, greenHouseId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    @RequestMapping(value = "/{greenHouseId}", method = RequestMethod.DELETE)
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<HouseEnvVO> delete(@PathVariable("gsm_key") Long gsmKey, @PathVariable("greenHouseId") Long greenHouseId) {
        LampLog lampLog = lampLogService.createTransactionLog("house.delete", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
        try {
            lampLog.addSecurity(LOG_SECURITY_TYPE.PRCS, LOG_SECURITY_EVENT.DELETE, String.valueOf(gsmKey));
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                lampLog.forbidden();
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            Result<HouseEnvVO> result = new Result(houseEnvService.delete(gsmKey, greenHouseId));
            lampLog.success();
            return result;
        } catch (Exception e) {
            log.warn("Delete House :{}, {}", gsmKey,greenHouseId, e);
            lampLog.exception(e.getMessage());
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        } finally {
            lampLogService.sendLog(lampLog);
        }
    }

    /**
     * @return "type_name": "노지농가", "root_type": 1, "description": "노지농가", "id": 95,
     * // green_house_type_id "type_name_i18n":
     * "__common.green_house.house_type.openfield", "house_type": 4
     * @description houseType 리스트
     */
    @RequestMapping(value = "/houseTypeList", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<HashMap<String, Object>>> selectHouseTypeList() {
        try {
            return new Result(houseEnvService.selectHouseTypeList());
        } catch (Exception e) {
            log.warn("Hout Type",  e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * @return
     * @description 작물 정보
     */
    @RequestMapping(value = "/productList", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public Result<List<ProductVO>> selectProductList() {
        try {
            return new Result(houseEnvService.selectProductList());
        } catch (Exception e) {
            log.warn("ProductList :", e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    @RequestMapping(value = "/{houseId}/houseProduct", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<HouseProductVO>> listHouseProduct(@PathVariable("gsm_key") Long gsmKey, @PathVariable("houseId") Long houseId) {
        try {
            if (!authCheckService.authCheck(gsmKey, houseId, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseEnvService.listHouseProduct(houseId));
        } catch (Exception e) {
            log.warn("HouseProduct Get {}, {}", gsmKey, houseId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    @RequestMapping(value = "/{houseId}/houseProducts", method = RequestMethod.POST)
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<List<HouseProductVO>> insertHouseProducts(@PathVariable("gsm_key") Long gsmKey, @PathVariable("houseId") Long houseId, @RequestBody List<HouseProductVO> listHouseProduct) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseEnvService.insertHouseProducts(listHouseProduct));
        } catch (Exception e) {
            log.warn("houseProducts Insert:{}, {}, {}", gsmKey, houseId, listHouseProduct, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, listHouseProduct);
        }
    }

    @RequestMapping(value = "/{houseId}/houseProduct", method = RequestMethod.POST)
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<HouseEnvVO> insertHouseProduct(@PathVariable("gsm_key") Long gsmKey, @PathVariable("houseId") Long houseId, @RequestBody HouseProductVO houseProduct) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseEnvService.insertHouseProduct(houseProduct));
        } catch (Exception e) {
            log.warn("houseProduct Insert :{}, {}, {}", gsmKey, houseId, houseProduct, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, houseProduct);
        }
    }

    @RequestMapping(value = "/{houseId}/houseProduct/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<HouseEnvVO> updateHouseProduct(@PathVariable("gsm_key") Long gsmKey, @PathVariable("houseId") Long houseId, @PathVariable("id") Long id, @RequestBody HouseProductVO houseProduct) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            houseProduct.id = id;
            return new Result(houseEnvService.updateHouseProduct(houseProduct));
        } catch (Exception e) {
            log.warn("houseProduct Update :{}, {}, {}", gsmKey, houseId, id, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, houseProduct);
        }
    }

    @RequestMapping(value = "/{houseId}/houseProducts", method = RequestMethod.DELETE)
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<Integer> deleteHouseProducts(@PathVariable("gsm_key") Long gsmKey, @PathVariable("houseId") Long houseId) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseEnvService.deleteHouseProduct(null, houseId, null));
        } catch (Exception e) {
            log.warn("houseProducts Delete :{}, {}",gsmKey, houseId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, houseId);
        }
    }

    @RequestMapping(value = "/{houseId}/houseProduct/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<Integer> deleteHouseProduct(@PathVariable("gsm_key") Long gsmKey, @PathVariable("houseId") Long houseId, @PathVariable("id") Long id) {
        try {
            if (!authCheckService.authCheck(gsmKey, null, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, gsmKey);
            }
            return new Result(houseEnvService.deleteHouseProduct(null, null, id));
        } catch (Exception e) {
            log.warn("houseProduct Delete :{}, {}, {}",gsmKey,houseId, id, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, id);
        }
    }


    @RequestMapping(value = "/{houseId}/deviceList", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<DeviceEnvVO>> houseDeviceList(@PathVariable("houseId") Long houseId) {
        try {
            if (!authCheckService.authCheck(null, houseId, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
            }
            return new Result(houseEnvService.houseDeviceList(houseId));
        } catch (Exception e) {
            log.warn("deviceList Get :{}", houseId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * 에버커스, 노루시스템즈를 위한 별도의 연동 api
     *
     * @description 온실에 추가되어 있는 장치 리스트와, 장치 타입
     */
    @RequestMapping(value = "/{houseId}/deviceInfoList", method = RequestMethod.GET)
    @ResponseBody
    public Result<HashMap<String, Object>> houseDeviceInfoList(@PathVariable("houseId") Long houseId) {
        try {
            if (!authCheckService.authCheck(null, houseId, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
            }
            return new Result(houseEnvService.houseDeviceInfoList(houseId));
        } catch (Exception e) {
            log.warn("deviceInfoList Get :{}", houseId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    /**
     * cctv 등록, 수정, 조회, 삭제, 온실 삭제되면 cascade
     *
     * @return
     */

    @RequestMapping(value = "/weather_cast", method = RequestMethod.GET)
    @ResponseBody
    public Result weatherCast(@RequestParam(value = "house_id", required = false) Long houseId,
                              @RequestParam(value = "from_date", required = false) String fromDate,
                              @RequestParam(value = "to_date", required = false) String toDate) {
        try {
            if (!authCheckService.authCheck(null, houseId, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
            }
            return new Result(houseEnvService.getWeatherCast(houseId, fromDate, toDate, config.isSmartfarmSystem()));
        } catch (Exception e) {
            log.warn("weather_cast :{}, {}, {}", houseId, fromDate, toDate, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    @RequestMapping(value = "/v2/weather_cast", method = RequestMethod.GET)
    @ResponseBody
    public Result weatherCastV2(@PathVariable("gsm_key") Long gsmKey,@RequestParam(value = "house_id", required = false) Long houseId, @RequestParam(value = "from_date", required = false) String fromDate,
                              @RequestParam(value = "to_date", required = false) String toDate) {
        try {
            if (!authCheckService.authCheck(null, houseId, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
            }
            return new Result(houseEnvService.getWeatherCastV2(houseId, fromDate, toDate, config.isSmartfarmSystem()));
        } catch (Exception e) {
            log.warn("weather_cast :{}, {}, {}", houseId, fromDate, toDate, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    @RequestMapping(value = "/{houseId}/ushort_weather", method = RequestMethod.GET)
    @ResponseBody
    public Result<UltraShortWeatherDataVO> getUltraShortWeatherData(@PathVariable("gsm_key") Long gsmKey,@PathVariable(value = "houseId") Long houseId) {
        try {
            if (!authCheckService.authCheck(null, houseId, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
            }
            return new Result(houseEnvService.getUltraShortWeatherData(houseId,  config.isSmartfarmSystem()));
        } catch (Exception e) {
            log.warn("ushort_weather :{},", houseId,  e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    @RequestMapping(value = "/weather_category", method = RequestMethod.GET)
    @ResponseBody
    public Result weatherCategory() {
        try {
            return new Result(houseEnvService.getWeatherCategory());
        } catch (Exception e) {
            log.warn("weather_category :",  e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    @RequestMapping(value = "/{houseId}/groundDeviceList", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<DeviceEnvVO>> groundDeviceList(@PathVariable("houseId") Long houseId) {
        try {
            if (!authCheckService.authCheck(null, houseId, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
            }
            return new Result(houseEnvService.groundDeviceList(houseId));
        } catch (Exception e) {
			log.warn("groundDeviceList GET : {}", houseId,  e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    // CCTV ===========================================

    // CCTV INSERT
    @RequestMapping(value = "/cctv", method = RequestMethod.POST)
    @ResponseBody
    @InterceptPost
    @InterceptLog
    public Result<CCTVSettingVO> insertCctv(@RequestBody CCTVSettingVO cctv) {
        try {
            return new Result(houseEnvService.insertCctv(cctv));
        } catch (Exception e) {
            log.warn("cctv INSERT :{}", cctv, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    // CCTV READ
    @RequestMapping(value = "/{houseId}/cctv", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<CCTVSettingVO>> selectCCTV(@PathVariable Long houseId) {
        try {
            if (!authCheckService.authCheck(null, houseId, null, null)) {
                return new Result("Not Allowed", HttpStatus.FORBIDDEN, houseId);
            }
            return new Result(houseEnvService.getCCTVListByHouseId(houseId));
        } catch (Exception e) {
            log.warn("cctv GET :{}", houseId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    // CCTV UPDATE
    @RequestMapping(value = "/cctv/minivms", method = RequestMethod.PUT)
    @ResponseBody
    @InterceptLog
    public Result<CCTVSettingVO> updateCctvMiniVms(@PathVariable("gsm_key") Long gsmKey, @RequestBody List<CCTVMiniVMSVO> miniVmsList) {
        try {
            miniVmsList.stream().forEach(v -> v.gsmKey = gsmKey);
            return new Result(houseEnvService.updateMiniVmsHash(miniVmsList));
        } catch (Exception e) {
            log.warn("minivms Update :{}, {}", gsmKey, miniVmsList, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    // CCTV UPDATE
    @RequestMapping(value = "/cctv", method = RequestMethod.PUT)
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<CCTVSettingVO> updateCctv(@RequestBody CCTVSettingVO cctv) {
        try {
            return new Result(houseEnvService.updateCctv(cctv));
        } catch (Exception e) {
            log.warn("cctv Update :{}", cctv, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

    // CCTV DELETE
    @RequestMapping(value = "/cctv/{cctvId}", method = RequestMethod.DELETE)
    @ResponseBody
    @InterceptPre
    @InterceptLog
    public Result<HashMap<String, Object>> deleteCctv(@PathVariable("cctvId") Integer cctvId) {
        try {
            return new Result(houseEnvService.deleteCctv(cctvId));
        } catch (Exception e) {
            log.warn("cctv Delete :{}", cctvId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }

}
