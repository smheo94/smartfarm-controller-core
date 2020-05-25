package com.kt.smartfarm.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ControlSettingOperatorVO {
    public String operatorCode;
    public String operatorGroup;
    public String name;
    public Integer displayOrder;

}
