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

import com.kt.smartfarm.supervisor.mapper.ControlLogicSettingMapper;
import egovframework.customize.service.*;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("controlLogicSettingService")
public class ControlLogicSettingServiceImpl extends EgovAbstractServiceImpl implements ControlLogicSettingService {

	@Resource(name="controlLogicSettingMapper")
	ControlLogicSettingMapper mapper;

	@Override
	public List<ControlLogicSettingVO> getLogicSetting(Integer gsmKey, Integer houseId, Integer controlSettingId){
		return mapper.getControlLogicSetting(gsmKey, houseId, controlSettingId);
	}
	@Override
	public 	ControlLogicSettingVO insertLogicSetting(ControlLogicSettingVO vo){
		mapper.insertControlSetting(vo);
		if( vo.getControlSettingId() == null )  {
			return vo;
		}
		if( vo.getPreOrderSettingId() != null ) {
			mapper.insertControlSettingPreOrder(vo);
		}
		if( vo.getCheckConditionList() != null ) {
			vo.getCheckConditionList().forEach( checkList ->{
				checkList.setControlSettingId(vo.controlSettingId);
				mapper.insertControlSettingChkCondition(checkList);
				if( checkList.getId() != null )
				{
					mapper.insertControlSettingChkConditionDevice(checkList);
				}
			});
		}
		if( vo.getDeviceList() != null ) {
			vo.getDeviceList().forEach( device ->{
				device.setControlSettingId(vo.controlSettingId);
				mapper.insertControlSettingDevice(device);
			});
		}
		return vo;
	}
	@Override
	public Integer delLogicSetting(Integer controlSettingId){
		List<ControlLogicSettingVO> logicSettingVOList = mapper.getControlLogicSetting(null, null, controlSettingId);
		if( logicSettingVOList == null || logicSettingVOList.size() ==0 || logicSettingVOList.get(0) ==null) {
			return 0;
		}
		final ControlLogicSettingVO logicSettingVO = logicSettingVOList.get(0);	
		if( logicSettingVO.getDeviceList() != null ) {
			mapper.deleteControlSettingDevice(null, logicSettingVO.controlSettingId);
		}
		if( logicSettingVO.getCheckConditionList() != null ) {
			mapper.deleteControlSettingChkCondition(null, logicSettingVO.controlSettingId);
		}	
		return mapper.deleteControlSetting(logicSettingVO.controlSettingId);
		
		
//		if( logicSettingVO.getPreOrderSettingId() != null ) {
//
//		}
// 		return null;
	}

	@Override
	public ControlLogicSettingVO updateLogicSetting(ControlLogicSettingVO vo) {
		if( vo.getControlSettingId() == null )  {
			return vo;
		}
		mapper.updateControlSetting(vo);
		if( vo.getPreOrderSettingId() != null ) {
			mapper.deleteControlSettingPreOrder(null, vo.controlSettingId);
            mapper.insertControlSettingPreOrder(vo);
		}
		if( vo.getCheckConditionList() != null ) {
			vo.getCheckConditionList().forEach( checkList ->{
				int isExist = mapper.updateControlSettingChkCondition(checkList);
				if(isExist == 0){
					mapper.insertControlSettingChkCondition(checkList);	
				}
				
				if( checkList.getId() != null )
				{
					mapper.deleteControlSettingChkConditionDevice(checkList.getId());
					mapper.insertControlSettingChkConditionDevice(checkList);
				}
			});
		}
		if( vo.getDeviceList() != null ) {
			vo.getDeviceList().forEach( device -> mapper.updateControlSettingDevice(device));
		}
		return vo;
	}

	@Override
	public void updateLogicSettingEnv(Map<String,Object> param) {
		mapper.updateControlSettingEnv(param);
	}


	@Override
	public List<ControlSettingOperatorVO> listControlSettingOperation() {
		return mapper.listControlSettingOperator();
	}
	@Override
	public Integer delChkConditionSetting(Integer chkConditionId) {
		return mapper.delChkConditionSetting(chkConditionId);
	}

}
