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
public class DeviceEnvVO extends DeviceEnvSimpleVO {

	Integer userInfoId; //deprecated
	Long gsmKey; //deprecated
	Long greenHouseId; //deprecated
	Long controllerId; //deprecated
	String deviceTypeNickname;  //deprecated
	String description; //deprecated
	Integer status;
	String modbusAddress1; //deprecated
	String modbusAddress2; //deprecated
	String modbusAddress3; //deprecated
	Integer mode; //deprecated
	String updateDate; //deprecated
    String deviceTypeName; //deprecated
    String deviceTypeDescription; //deprecated
    String opmodeError; //deprecated
	String valueExchange; //deprecated

	Integer roundNumber; //deprecated
	Integer accumM; //deprecated
	Integer accumH; //deprecated
	Integer accumD; //deprecated
	Integer opTime; //deprecated
	String controlUnit;
	List<VDeviceEnvVO> relationDeviceList; //deprecated
	List<EDeviceEnvVO> electricDeviceList; //deprecated
}
