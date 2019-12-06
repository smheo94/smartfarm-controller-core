package com.kt.smartfarm.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 외부기상대, 임계치 설정 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class VDeviceEnvVO {
	Integer id;
	Integer parentDeviceId;
	Integer deviceNum;
	Integer deviceInsertOrder;
	Integer deviceId;
	DeviceEnvVO deviceInfo;
}
