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

import com.kt.smartfarm.service.*;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper("controlLogicSettingMapper")
public interface ControlLogicSettingMapper {
	List<ControlLogicSettingVO> getControlLogicSetting(@Param("gsm_key") Long gsmKey, @Param("green_house_id") Long greenHouseId,
													   @Param("control_setting_id") Long controlSettingId, @Param("logic_id")  List<Long> logicId);
//	ControlLogicSettingHistoryVO getControlLogicSettingHistoryDetail(@Param("gsm_key") Long gsmKey, @Param("green_house_id") Long greenHouseId,
//													   @Param("control_setting_id") Long controlSettingId, @Param("log_dt") Long logDt);

	List<ControlLogicSettingHistoryVO> getControlLogicSettingHistoryList(@Param("gsm_key") Long gsmKey, @Param("green_house_id") Long greenHouseId,
                                                                         @Param("control_setting_id") Long controlSettingId
														, @Param("from_date") Long fromData, @Param("to_date") Long toDate);


	List<ControlLogicSettingDeviceVO> getControlDevices(Long logicId);
	List<ControlLogicSettingCheckConditionVO> getCheckConditions(Long logicId);

	List<ControlSettingOperatorVO> listControlSettingOperator();

	Integer insertControlSettingChkConditionDevice(ControlLogicSettingCheckConditionVO vo);
	Integer deleteControlSettingChkConditionDevice(@Param("id") Long id);

	Integer insertControlSettingChkCondition(ControlLogicSettingCheckConditionVO vo);
	Integer updateControlSettingChkCondition(ControlLogicSettingCheckConditionVO vo);
	Integer deleteControlSettingChkCondition(@Param("id") Long id,@Param("control_setting_id") Long controlSettingId);

	Integer insertControlSettingDevice(ControlLogicSettingDeviceVO vo);
	Integer updateControlSettingDevice(ControlLogicSettingDeviceVO vo);
	Integer deleteControlSettingDevice(@Param("id") Long id,@Param("control_setting_id") Long controlSettingId);

	Integer insertControlSettingPreOrder(ControlLogicSettingVO vo);
	Integer deleteControlSettingPreOrder(@Param("id") Long id,@Param("control_setting_id") Long controlSettingId);

	Integer insertControlSetting(ControlLogicSettingVO vo);
	Integer updateControlSetting(ControlLogicSettingVO vo);
	Integer updateControlSettingEnv(Map<String, Object> param);
	Integer deleteControlSetting(@Param("control_setting_id") Long controlSettingId);
	Integer delChkConditionSetting(Long chkConditionId);

	Integer copyToNewGSMContolSetting(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );
	Integer copyToNewGSMContolSettingCheckCondition(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );
	Integer copyToNewGSMContolSettingCheckConditionDevice(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );
	Integer copyToNewGSMControlSettingDevice(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );
	Integer copyToNewGSMControlPreOrder(@Param("from_gsm_key") Long fromGsmKey, @Param("to_gsm_key") Long toGsmKey );
	void updateLogicEnv(Map<String, Object> param);


}
