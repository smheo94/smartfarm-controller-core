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
import com.kt.smartfarm.config.Message;
import com.kt.smartfarm.service.SystemService;
import com.kt.smartfarm.task.scheduler.SmartfarmTaskScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/system")
public class SystemController {
    @Autowired
    private Message msg;
    @Resource(name = "systemService")
    private SystemService systemService;


    /**
     * @param appName
     * @return
     * @description 앱 버전 정보 조회 ( 최신버전 )
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/openapi/app/", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String, Object>> getLastVersion(@RequestParam("app_name") String appName) {
        try {
            return new Result(systemService.getAppVersion(appName));
        } catch (Exception e) {
            log.warn("App Version :{}", appName, e);
            return new Result(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


    @RequestMapping(value = "/data/", method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String, Object>> getData(@RequestBody HashMap<String, Object> param) {
        try {
            return new Result(systemService.getAnyQueryResult(param));
        } catch (Exception e) {
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Autowired
    Environment env;

    @RequestMapping(value = "/ping/", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> ping() {
        final StringBuilder profile = new StringBuilder();
        String[] profiles = env.getActiveProfiles();
        if (profiles != null) {
            Arrays.asList(profiles).stream().forEach(s -> profile.append(",").append(s));
        }
        profile.append("/").append(env.getProperty("spring.profiles.active"));
        return new Result(profile);
    }

    @RequestMapping(value = "/time/", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> systemtime() {
        return new Result<>(System.currentTimeMillis());
    }

    @Autowired
    SmartfarmTaskScheduler taskScheduler;

    @PreAuthorize("hasRole('ROLE_SYS')")
    @ResponseBody
    @RequestMapping(value = "/run_schedule/", method = RequestMethod.GET)
    public void RunSchedule(@RequestParam("schedulerName") String schedulerName)  {
        if( "UltraWeather".equalsIgnoreCase(schedulerName)) {
            try {
                taskScheduler.runUltraShortWeatherSchedule();
            } catch (URISyntaxException e) {
                log.error("Run Ultra Weater Error : {}", e);
            }
        }
    }
}
