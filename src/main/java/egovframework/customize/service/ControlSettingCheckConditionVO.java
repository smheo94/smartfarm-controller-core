package egovframework.customize.service;

import lombok.Data;

import java.util.List;
@Data
public class ControlSettingCheckConditionVO {
    public Integer id;
    public String operatorCode;
    public String operatorCondition;
    public String action;
    public String value;
    public List<Integer> deviceIdList;
}
