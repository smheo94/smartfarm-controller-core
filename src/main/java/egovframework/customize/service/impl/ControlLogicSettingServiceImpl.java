/*
 * Copyright 2008-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.customize.service.impl;

import com.kt.smartfarm.supervisor.mapper.ControlLogicMapper;
import com.kt.smartfarm.supervisor.mapper.ControlLogicSettingMapper;
import egovframework.customize.service.*;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service("controlLogicSettingService")
public class ControlLogicSettingServiceImpl extends EgovAbstractServiceImpl implements ControlLogicSettingService {

	@Autowired
	ControlLogicSettingMapper mapper;

	@Override
	public List<ControlLogicSettingVO> getLogicSetting(Integer gsmKey, Integer houseId){
		return mapper.getControlLogicSetting(gsmKey, houseId, null);
	}
	@Override
	public 	ControlLogicSettingVO insertLogicSetting(ControlLogicSettingVO vo){
		mapper.insertControlSetting(vo);
		if( vo.getLogicSettingId() == null )  {
			return vo;
		}
		if( vo.getPreOrderSettingId() != null ) {
			mapper.insertControlSettingPreOrder(vo);
		}
		if( vo.getCheckConditionList() != null ) {
			vo.getCheckConditionList().stream().forEach( checkList ->{
				checkList.setControlSettingId(vo.logicSettingId);
				mapper.insertControlSettingChkCondition(checkList);
				if( checkList.getId() != null )
				{
					mapper.insertControlSettingChkConditionDevice(checkList);
				}
			});
		}
		if( vo.getDeviceList() != null ) {
			vo.getDeviceList().stream().forEach( device ->{
				device.setControlSettingId(vo.logicSettingId);
				mapper.insertControlSettingDevice(device);
			});
		}
		return vo;
	}
	@Override
	public Integer delLogicSetting(Integer logicSettingId){
		List<ControlLogicSettingVO> logicSettingVOList = mapper.getControlLogicSetting(null, null, logicSettingId);
		if( logicSettingVOList == null || logicSettingVOList.size() ==0 || logicSettingVOList.get(0) ==null) {
			return 0;
		}
		final ControlLogicSettingVO logicSettingVO = logicSettingVOList.get(0);
		return	mapper.deleteControlSetting(logicSettingVO.logicSettingId);
//		if( logicSettingVO.getDeviceList() != null ) {
//			mapper.deleteControlSettingDevice(null, logicSettingVO.logicSettingId);
//		}
//		if( logicSettingVO.getCheckConditionList() != null ) {
//			mapper.deleteControlSettingChkCondition(null, logicSettingVO.logicSettingId);
//		}
//		if( logicSettingVO.getPreOrderSettingId() != null ) {
//
//		}
// 		return null;
	}

	@Override
	public ControlLogicSettingVO updateLogicSetting(ControlLogicSettingVO vo) {
		if( vo.getLogicSettingId() == null )  {
			return vo;
		}
		mapper.updateControlSetting(vo);
		if( vo.getPreOrderSettingId() != null ) {
			mapper.updateControlSettingPreOrder(vo);
		}
		if( vo.getCheckConditionList() != null ) {
			vo.getCheckConditionList().stream().forEach( checkList ->{
				mapper.updateControlSettingChkCondition(checkList);
				if( checkList.getId() != null )
				{
					mapper.deleteControlSettingChkConditionDevice(checkList.getId());
					mapper.insertControlSettingChkConditionDevice(checkList);
				}
			});
		}
		if( vo.getDeviceList() != null ) {
			vo.getDeviceList().stream().forEach( device ->{
				mapper.updateControlSettingDevice(device);
			});
		}
		return vo;
	}
}
