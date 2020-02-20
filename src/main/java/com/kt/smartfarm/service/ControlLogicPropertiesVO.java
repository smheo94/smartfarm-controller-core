package com.kt.smartfarm.service;

import com.kt.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
public class ControlLogicPropertiesVO {
	Long id;
	String uiClassName;
	String description;
	String bootstrapSize;
	Map<String,Object> propertiesJson;
	Integer logicId;
	Integer propertiesId;
	Integer isPeriod;
	Integer priority;
	Integer onPc;
	Integer onMobile;
	Integer onPanel;
	Integer manualCommandNum;
	Map<String,Object> dependDevice;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
