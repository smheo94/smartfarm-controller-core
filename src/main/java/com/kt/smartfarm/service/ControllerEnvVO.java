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
public class ControllerEnvVO {
	Long id;
	Long gsmKey;
	String controllerInfoId;
	String ip;
	String controllerType; // 양액기 = 1 / 구동기 = 0
	String controllerStatus;
	String description;
	Map<String,Object> properties;
	Integer port;
	ControllerInfoVO controllerInfo;
	
//	List<ArrayDeviceVO> arrayDeviceData;
	List<DeviceEnvVO> deviceList;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
