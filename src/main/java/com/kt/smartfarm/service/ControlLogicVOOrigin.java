package com.kt.smartfarm.service;

import com.kt.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
public class ControlLogicVOOrigin {
	protected  Integer id;
	protected  String name;
	protected  String javaClassName;
	protected  String uiClassName;
	protected  String description;
	protected  String uiHelp;
	protected  Integer canMultiLogic;
	protected  Integer defaultPeriodSize;

	protected  List<ControlLogicDeviceVO> controlDeviceList;

	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
