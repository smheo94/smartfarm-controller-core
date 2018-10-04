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

import egovframework.customize.service.ControlLogicSettingCheckConditionVO;
import egovframework.customize.service.ControlLogicSettingDeviceVO;
import egovframework.customize.service.ControlLogicSettingVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper("controlLogicSettingMapper")
public interface ControlLogicSettingMapper {
	List<ControlLogicSettingVO> getControlLogicSetting();
	List<ControlLogicSettingDeviceVO> getControlDevices(Long logicId);
	List<ControlLogicSettingCheckConditionVO> getCheckConditions(Long logicId);

	Integer insertControlSettingChkConditionDevice(ControlLogicSettingCheckConditionVO vo);
	Integer deleteControlSettingChkConditionDevice(@Param("id") Integer id);

	Integer insertControlSettingChkCondition(ControlLogicSettingCheckConditionVO vo);
	Integer updateControlSettingChkCondition(ControlLogicSettingCheckConditionVO vo);
	Integer deleteControlSettingChkCondition(@Param("id") Integer id,@Param("control_setting_id") Integer controlSettingId);

	Integer insertControlSettingDevice(ControlLogicSettingDeviceVO vo);
	Integer updateControlSettingDevice(ControlLogicSettingDeviceVO vo);
	Integer deleteControlSettingDevice(@Param("id") Integer id,@Param("control_setting_id") Integer controlSettingId);

	Integer insertControlSettingPreOrder(ControlLogicSettingVO vo);
	Integer updateControlSettingPreOrder(ControlLogicSettingVO vo);
	Integer deleteControlSettingPreOrder(@Param("id") Integer id,@Param("control_setting_id") Integer controlSettingId);

	Integer insertControlSetting(ControlLogicSettingVO vo);
	Integer updateControlSetting(ControlLogicSettingVO vo);
	Integer deleteControlSetting(@Param("id") Integer id,@Param("logic_setting_id") Integer logicSettingId);
}
