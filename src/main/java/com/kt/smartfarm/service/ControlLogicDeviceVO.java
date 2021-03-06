package com.kt.smartfarm.service;

import java.util.Map;

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
public class ControlLogicDeviceVO {
	Long id;
	Integer logicId;
	Integer deviceNum;
	String deviceParamCode;
	String deviceParamName;
	String deviceType;
	Integer isMain;
	Integer ableArray;
	Integer description;	
	Integer displayOrder;
	Integer isUsed;
	Integer required;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
