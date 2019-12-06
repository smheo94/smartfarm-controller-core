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

import java.util.List;
import com.kt.cmmn.util.Result;
import com.kt.smartfarm.service.CategoryEnvService;
import com.kt.smartfarm.service.CategoryEnvVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/category")
public class CategoryEnvController {

	//@Resource(name = "categoryEnvService")
	@Autowired
	private CategoryEnvService categoryEnvService;

	
	@RequestMapping(value= "/", method = RequestMethod.PUT)
	@ResponseBody
	public Result<CategoryEnvVO> update(@RequestBody CategoryEnvVO category){
		try {

			return new Result(categoryEnvService.update(category));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, category);
		}
	}
	
	@RequestMapping(value= "/", method = RequestMethod.POST)
	@ResponseBody
	public Result<CategoryEnvVO> insert( @RequestBody CategoryEnvVO category){
		try {
			return new Result(categoryEnvService.insert(category));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, category);
		}
	}
	
	
	@RequestMapping(value= "/", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<CategoryEnvVO>> list(){
		try {
			return new Result(categoryEnvService.list());
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	
	@RequestMapping(value= "/{categoryId}", method = RequestMethod.GET)	
	public Result<List<CategoryEnvVO>> selectCategoryDetail( @PathVariable("categoryId") Integer categoryId){
		try {
			return new Result(categoryEnvService.selectCategoryDetail(categoryId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
	
	@RequestMapping(value= "/{categoryId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result<CategoryEnvVO> delete(@PathVariable("categoryId") Integer categoryId){
		try {
			return new Result(categoryEnvService.delete(categoryId));
		} catch(Exception e) {
			return new Result(e.getMessage(), HttpStatus.CONFLICT, null);
		}
	}
	
}
