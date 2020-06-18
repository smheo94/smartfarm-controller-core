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

import com.kt.cmmn.util.AuthorityChecker;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.config.Message;
import com.kt.smartfarm.lamplog.LampLog;
import com.kt.smartfarm.lamplog.models.LOG_SECURITY_EVENT;
import com.kt.smartfarm.lamplog.models.LOG_SECURITY_TYPE;
import com.kt.smartfarm.lamplog.models.LOG_TYPE;
import com.kt.smartfarm.service.CategoryEnvService;
import com.kt.smartfarm.service.CategoryEnvVO;
import com.kt.smartfarm.service.LampLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping(value = "/category")
public class CategoryEnvController {

    //@Resource(name = "categoryEnvService")
    @Autowired
    private CategoryEnvService categoryEnvService;
    @Autowired
    private Message msg;

    @Autowired
    private LampLogService lampLogService;

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public Result<CategoryEnvVO> update(@RequestBody CategoryEnvVO category) {
        LampLog lampLog = lampLogService.createTransactionLog("category.update", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
        try {
            lampLog.addSecurity(LOG_SECURITY_TYPE.MNGT, LOG_SECURITY_EVENT.UPDATE);
            lampLog.success();
            return new Result(categoryEnvService.update(category));
        } catch (Exception e) {
            log.warn("Exception :{}", category, e);
            lampLog.exception(e.getMessage());
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, category);
        } finally {
            lampLogService.sendLog(lampLog);
        }

    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Result<CategoryEnvVO> insert(@RequestBody CategoryEnvVO category) {
        LampLog lampLog = lampLogService.createTransactionLog("category.update", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
        try {
            lampLog.addSecurity(LOG_SECURITY_TYPE.MNGT, LOG_SECURITY_EVENT.CREATE);
            lampLog.success();
            return new Result(categoryEnvService.insert(category));
        } catch (Exception e) {
            log.warn("Exception :{}", category, e);
            lampLog.exception(e.getMessage());
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, category);
        } finally {
            lampLogService.sendLog(lampLog);
        }
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<CategoryEnvVO>> list() {
        try {
            return new Result(categoryEnvService.list());
        } catch (Exception e) {
            log.warn("list :",  e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public Result<List<CategoryEnvVO>> selectCategoryDetail(@PathVariable("categoryId") Integer categoryId) {
        try {
            return new Result(categoryEnvService.selectCategoryDetail(categoryId));
        } catch (Exception e) {
            log.warn("selectCategoryDetail :{}", categoryId, e);
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        }
    }


    @RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<CategoryEnvVO> delete(@PathVariable("categoryId") Integer categoryId) {
        LampLog lampLog = lampLogService.createTransactionLog("category.delete", LOG_TYPE.IN_MSG, new AuthorityChecker().getName(), null, null);
        try {
            lampLog.addSecurity(LOG_SECURITY_TYPE.MNGT, LOG_SECURITY_EVENT.DELETE, String.valueOf(categoryId));
            lampLog.success();
            return new Result(categoryEnvService.delete(categoryId));
        } catch (Exception e) {
            log.warn("delete :{}", categoryId, e);
            lampLog.exception(e.getMessage());
            return new Result(msg.getMessage("errors.ask_to_admin"), HttpStatus.CONFLICT, null);
        } finally {
            lampLogService.sendLog(lampLog);
        }
    }

}
