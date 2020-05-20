package com.kt.smartfarm.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
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
public class DeviceEnvVO {
	Long id;
	Integer deviceTypeId;
	Integer deviceTypeIdx;
	Integer userInfoId;
	Long gsmKey;
	Long greenHouseId;
	Long controllerId;
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
	//Integer noStopControl;
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
    String opmodeError;
	String valueExchange;

	Integer roundNumber;
	String topicGroup;
	Double resetPoint;
	Integer accumM;
	Integer accumH;
	Integer accumD;
	Integer opTime;
	Integer valueType;
	Map<String,Object> properties;
//	String properties;
//
//	String trdPtId;
//
//	public void setTrdPtId(String value) {
//		this.trdPtId = value;
//		makeProperties();
//	}
//	public void makeProperties() {
//		HashMap<String,Object> propertiesMap = new HashMap<>();
//		propertiesMap.put("trd_pt_id", trdPtId);
//		properties = MapUtils.toJson(propertiesMap);
//	}


	List<VDeviceEnvVO> relationDeviceList;
	List<EDeviceEnvVO> electricDeviceList;
	public String getDeviceTypeKey() {
		return deviceType + "$" + deviceTypeIdx;
	}
}
