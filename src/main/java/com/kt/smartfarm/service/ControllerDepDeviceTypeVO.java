package com.kt.smartfarm.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 외부기상대, 임계치 설정
 *
 * @author LEE
 */

@Data
@Getter
@Setter
public class ControllerDepDeviceTypeVO {
    Integer id;
    Integer controllerInfoId;
    Integer deviceTypeId;
    Integer deviceTypeNum;
    Integer autoCreate;

    String description;

    /* from cd_device_type or cd_controllerinfo_dep_device_type*/ String defaultName;
    String defaultAddress1;
    String defaultAddress2;
    String defaultAddress3;

    /* from cd_device_type */ String deviceType;
    String deviceTypeName;
}
