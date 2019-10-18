package com.kt.smartfarm.service;

import java.util.List;
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
public class EventVO {
	Integer id;
	Integer gsmKey;
	String controllerInfoId;
	String ip;
	String controllerType;
	String controllerStatus;
	String description;
	String properties;
	Integer port;
	ControllerInfoVO controllerInfo;
	
//	List<ArrayDeviceVO> arrayDeviceData;
	List<DeviceEnvVO> deviceList;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
