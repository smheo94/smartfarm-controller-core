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

import com.kt.smartfarm.service.ControlLogicDeviceVO;
import com.kt.smartfarm.service.ControlLogicVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper("controlLogicMapper")
public interface ControlLogicMapper {
	List<ControlLogicVO> getLogicList();
	List<ControlLogicDeviceVO> getLogicDeviceList(@Param("logicId") Long logicId);
	List<HashMap<String,Object>> getLogicPropertyList(@Param("logicId") Long logicId);
}
