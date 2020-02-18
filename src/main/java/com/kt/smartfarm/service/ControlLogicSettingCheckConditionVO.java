package com.kt.smartfarm.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
public class ControlLogicSettingCheckConditionVO {
    Long id;
    Long  controlSettingId;
    Integer logicId;
    String operatorCode;
    String operatorCondition;
    String action;
    Double value;
    List<Long> deviceIdList;


    @JsonIgnore
    public Long tmpGsmKey;
}
