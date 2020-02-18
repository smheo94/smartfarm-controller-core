package com.kt.smartfarm.service;

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
public class ControlLogicSettingDeviceVO {
	Long id;
	Long  controlSettingId;
	Integer logicId;
	Integer deviceNum;
	Integer deviceInsertOrder;
	Long deviceId;
	Integer required;


	@JsonIgnore
	public Long tmpGsmKey;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
