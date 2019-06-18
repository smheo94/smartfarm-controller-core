package com.kt.smartfarm.service;

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
public class ArrayDeviceVO {
	String deviceTypeId;
	String modbusAddress1;
	String modbusAddress2;
	String modbusAddress3;
	Integer mode;
	Integer controllerId;
	String deviceId;
	String nickname;
	String deviceTypeNickname;
}
