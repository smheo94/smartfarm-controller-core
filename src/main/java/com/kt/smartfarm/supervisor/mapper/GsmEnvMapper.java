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

import com.kt.smartfarm.service.GsmEnvVO;
import com.kt.smartfarm.service.GsmThresholdVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper("gsmEnvMapper")
public interface GsmEnvMapper {
	void createGSMSeq(@Param("gsmKey") Integer gsmKey);
	GsmEnvVO get(@Param("gsmKey") Integer gsmKey);
	Integer delete(@Param("gsmKey") Integer gsmKey);
	Integer insert(GsmEnvVO gsmInfo);
	Integer update(GsmEnvVO gsmInfo);
	List<Map<String,Object>> gsmOfDeviceList(@Param("gsmKey")Integer gsmKey);
	List<Map<String, Object>> getGsmList(@Param("gsmKey") Integer gsmKey, @Param("userInfoId") Integer userInfoId, @Param("categoryId") Integer categoryId, @Param("farmName") String farmName,
										 @Param("authUserIdx") String authUserIdx, @Param("isSmartfarmSystem")Boolean isSmartfarmSystem);
	List<Map<String, Object>> getHouseList(@Param("gsmKey") Integer gsmKey);
	List<GsmEnvVO> notMappedList();
	Integer userRegistGSM(HashMap<String, Object> param);
	List<GsmEnvVO> getGsmInfoByUser(Integer userInfoId);
	
	Integer gsmThresholdInsert(GsmThresholdVO gsmThresholdVO);
	Integer gsmThresholdUpdate(GsmThresholdVO gsmThresholdVO);
	GsmThresholdVO gsmThresholdGet(HashMap<String,Object> param);
	Integer getNewGSMSeqValue(@Param("from_gsm_key") Integer fromGsmKey, @Param("to_gsm_key") Integer toGsmKey );
	Integer setNewGSMSeqKey(@Param("to_gsm_key") Integer toGsmKey, @Param("set_val") Integer setValue );
}
