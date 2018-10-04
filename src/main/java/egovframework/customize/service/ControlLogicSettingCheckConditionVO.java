package egovframework.customize.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
public class ControlLogicSettingCheckConditionVO {
    Integer id;
    Integer  controllerSettingId;
    Integer logicId;
    String operatorCode;
    String operatorCondition;
    String action;
    String value;
    List<Integer> deviceIdList;
}
