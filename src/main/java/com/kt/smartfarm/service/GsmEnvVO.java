package com.kt.smartfarm.service;

import java.util.List;
import java.util.Map;

import com.kt.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class GsmEnvVO {
	Long gsmKey;
	Integer userInfoId;
	Integer isMine;
	String areaCode;
	String farmNickname;
	String updateDate;
	String ownerUserInfoId;
	String masterSystemHost;
	Integer masterSystemPort;
	String httpSchema = "http";
	String systemHost;
	Integer systemPort;
	String cropsurl;
	String imsurl;
	String mqttBrokerHost;
	Integer mqttBrokerPort;
	Integer serverReadTime;
	Integer productInterval;
	Integer monitoringInterval;
	Integer historySaveDbInterval;
	Integer sensorCurrentRetentionPeriod;
	Integer debugStopEvent;
	Integer debugStopCrops;
	Integer debugStopDeviceAutoControl;
	Integer debugStopIms;
	Integer delayAutoControlStart;
	String packageVersion;
	String schemaVersion;
	String vpnSystemHost;
	String vpnSystemPort;
	Integer nutrientMonitoringInterval;
	String farmNicknameI18n;
	String userId;
	Integer farmDbId;
	Integer categoryId;
	Integer monitoringProcessInterval;
	ThresholdVO threshold;
	Integer device_count;
	Integer deviceCount;
	Long readTime;

	Map<String,Object> properties;
//	String trdPtCompanyName;
//	public void setTrdPtId(String value) {
//		this.trdPtCompanyName = value;
//		makeProperties();
//	}
//	public void makeProperties() {
//		HashMap<String,Object> propertiesMap = new HashMap<>();
//		propertiesMap.put("trdPtCompanyName", trdPtCompanyName);
//		properties = MapUtils.toJson(propertiesMap);
//	}
	List<HouseEnvVO> houseList;

	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
