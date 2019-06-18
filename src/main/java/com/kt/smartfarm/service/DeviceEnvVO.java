package com.kt.smartfarm.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * 외부기상대, 임계치 설정 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class DeviceEnvVO {
	Integer id;
	Integer deviceTypeId;
	Integer deviceTypeIdx;
	Integer userInfoId;
	Integer gsmKey;
	Integer greenHouseId;
	Integer controllerId;
	String nickname;
	String deviceTypeNickname;
	String description;
	String setupDate;
	Integer xPosition;
	Integer yPosition;
	Integer x2Position;
	Integer y2Position;
	String memo;
	String modbusAddress1;
	String modbusAddress2;
	String modbusAddress3;
	Integer noStopControl;
	Integer mode;
	String updateDate;
	Integer autoManualMode;
	Integer status;
	String deviceType;
    String deviceTypeGroup;
    String deviceTypeName;
    String deviceTypeDescription;
    String kind;
    String dcac;

	List<VDeviceEnvVO> relationDeviceList;

	public String getDeviceTypeKey() {
		return deviceType + "$" + deviceTypeIdx;
	}
}
