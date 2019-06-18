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
public class ControlLogicEnvVO {
	Integer id;
	String name;
	String javaClassName;
	String uiClassName;
	String description;
	String uiHelp;
	Integer canMultiLogic;
	Integer defaultPeriodSize;	

	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
