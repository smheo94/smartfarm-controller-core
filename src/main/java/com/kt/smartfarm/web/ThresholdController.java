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
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.config.Message;
import com.kt.smartfarm.service.ThresholdService;
import com.kt.smartfarm.service.ThresholdVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/threshold")
public class ThresholdController {

    public static final String DEFAULT_SETUP_FILE_PATH = "data/env-default/";
    private static final String extraUrl = "";

    @Autowired
    private Message msg;
    @Resource(name = "thresholdService")
    private ThresholdService thresholdService;


    /**
     * @param gsmKey
     * @param greenHouseId
     * @param thresholdVOs
     * @return
     * @description 온실 등록 ( 제어기 선택은 어디서? 밑에서~ )
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/gsm/{gsmKey}/house/{greenHouseId}", method = RequestMethod.POST)
    @ResponseBody
    @InterceptLog
    public Result insert(@PathVariable Long gsmKey, @PathVariable Long greenHouseId,
                         @RequestBody List<ThresholdVO> thresholdVOs) {
        try {
            int cnt = thresholdService.update(gsmKey, greenHouseId, thresholdVOs);

            if (cnt > 0) {
                log.info("{} | {} | Threshold insert | SUCCEED | {} ", "UnknownHost", "UnknownUser", thresholdVOs);
                return new Result<List<ThresholdVO>>("OK", HttpStatus.OK, thresholdVOs);
            } else {
                log.info("{} | {} | Threshold insert | FAIL | {} ", "UnknownHost", "UnknownUser", thresholdVOs);
                return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "Query Failed");
            }
        } catch (Exception e) {
            log.warn("ThresholdController Insert :{}, {}, {}", gsmKey, greenHouseId, thresholdVOs, e);
            return new Result<String>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 관리자에게 문의해 주세요"); /*e.getMessage());*/
        }
    }

    @RequestMapping(value = "/gsm/{gsmKey}/house/{greenHouseId}", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<ThresholdVO>> list(@PathVariable Long gsmKey, @PathVariable Long greenHouseId) {
        try {
            return new Result(thresholdService.getThreshold(gsmKey, greenHouseId));
        } catch (Exception e) {
            log.warn("ThresholdController List :{}, {}", gsmKey, greenHouseId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


}
