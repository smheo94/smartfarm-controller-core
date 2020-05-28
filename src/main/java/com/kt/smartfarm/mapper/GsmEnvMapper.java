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
package com.kt.smartfarm.mapper;

import java.util.HashMap;
import java.util.List;

import com.kt.smartfarm.service.GsmEnvVO;
import com.kt.smartfarm.service.GsmThresholdVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper("gsmEnvMapper")
public interface GsmEnvMapper {
	GsmEnvVO get(@Param("gsmKey") Long gsmKey);
	Integer delete(@Param("gsmKey") Long gsmKey);
	Long insert(GsmEnvVO gsmInfo);
	Integer update(GsmEnvVO gsmInfo);
	List<GsmEnvVO>  gsmOfDeviceList(@Param("gsmKey")Long gsmKey);
	List<GsmEnvVO>  getGsmList(@Param("gsmKey") Long gsmKey, @Param("userInfoId") Integer userInfoId, @Param("categoryId") Integer categoryId, @Param("farmName") String farmName,
										 @Param("authUserIdx") String authUserIdx, @Param("isSmartfarmSystem")Boolean isSmartfarmSystem);
	List<GsmEnvVO> notMappedList();
	Integer userRegistGSM(HashMap<String, Object> param);

	Long gsmThresholdInsert(GsmThresholdVO gsmThresholdVO);
	Long gsmThresholdUpdate(GsmThresholdVO gsmThresholdVO);
	GsmThresholdVO gsmThresholdGet(HashMap<String,Object> param);
}
