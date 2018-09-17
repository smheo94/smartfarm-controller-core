package egovframework.customize.service;

import java.util.Map;

import egovframework.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class GsmEnvVO {
	Integer gsmKey;
	Integer userInfoId;
	Integer isMine;
	String areaCode;
	String farmNickname;
	String updateDate;
	String ownerUserInfoId;
	String masterSystemHost;
	Integer masterSystemPort;
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
	Integer nutrientMonitoringInterval;
	String farmNicknameI18n;
	String userId;
	Integer farmDbId;
	Integer categoryId;

	ThresholdVO threshold;
	Integer device_count;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
