package com.kt.smartfarm.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class EDeviceEnvVO {
    Integer id;
    Integer parentDeviceId;
    Integer deviceId;
}
