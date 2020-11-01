package com.kt.smartfarm.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


/**
 * 외부기상대, 임계치 설정 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class DeviceEnvSimpleVO {
	Long id;
	Integer deviceTypeId;
	Integer deviceTypeIdx;
	String nickname;

	Integer autoManualMode;

	String deviceType;
    String deviceTypeGroup;
    String kind;
    String dcac;
	String valueMod;
	Integer valueType;
	Map<String,Object> properties;

	public String getDeviceTypeKey() {
		return deviceType + "$" + deviceTypeIdx;
	}
}
