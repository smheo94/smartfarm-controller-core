package com.kt.smartfarm.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kt.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


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
	public Long greenHouseId;
	public Integer logicId;
	public String defLogicName;
	public String javaClassName;
	public String logicName;
	public String logicEnv;
	public String logicPeriodEnv;
	public Integer logicPeriodSize;
	public String autoManualMode;
	public Date createDt;
	public Date updateDt;
	public Long preOrderSettingId;
	public Object sensorData;
	public Integer defaultPeriodSize;
	public Integer viewOrder;
	public String updateSystem;
	public List<ControlLogicSettingCheckConditionVO> checkConditionList;
	public List<ControlLogicSettingDeviceVO> deviceList;

	@JsonIgnore
	public Long tmpGsmKey;

	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
