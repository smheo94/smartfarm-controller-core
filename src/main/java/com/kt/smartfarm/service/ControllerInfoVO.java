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
public class ControllerInfoVO {	
	Integer id;
	String model;
	String name;
	String name_i18n;	 
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
