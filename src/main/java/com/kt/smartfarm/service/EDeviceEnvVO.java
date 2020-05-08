package com.kt.smartfarm.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EDeviceEnvVO {
    Long id;
    Long parentDeviceId;
    Long deviceId;
}
