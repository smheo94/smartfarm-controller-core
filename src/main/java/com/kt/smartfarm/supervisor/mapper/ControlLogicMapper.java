/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
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
package com.kt.smartfarm.supervisor.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.customize.service.ControlLogicDeviceVO;
import egovframework.customize.service.ControlLogicVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("controlLogicMapper")
public interface ControlLogicMapper {
	
	Integer insert(ControlLogicVO map);
	Integer delete(Map<String,Object> map);
	Integer update(ControlLogicVO map);	
	List<ControlLogicVO> logicList();
	List<ControlLogicDeviceVO> logicDeviceList();
	List<ControlLogicVO> getRegList(HashMap<String,Object> param);
	List<HashMap<String,Object>> getLogicProperties();
	
}
