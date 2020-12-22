package com.kt.smartfarm.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kt.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class ControlLogicSettingVO {

	public Long controlSettingId;
	public Long gsmKey;
	public Long greenHouseId;
	public Integer logicId;
	public String defLogicName;
	public String javaClassName;
	public String uiClassName;
	public String description;
	public String uiHelp;
	public String logicName;
	public String logicEnv;

	public String logicPeriodEnv;
	public Integer logicPeriodSize;
	public String autoManualMode;
	public Date createDt;
	public Date updateDt;
	public Long preOrderSettingId;
	public Object sensorData;
	public Integer canMultiLogic;
	public Integer defaultPeriodSize;
	public Integer viewOrder;
	public String updateSystem;
	public List<ControlLogicSettingCheckConditionVO> checkConditionList;
	public List<ControlLogicSettingDeviceVO> deviceList;
	public List<ControlLogicDeviceVO> logicDeviceList;

	@JsonIgnore
	public Long tmpGsmKey;

	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
