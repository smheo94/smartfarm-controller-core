package egovframework.customize.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import egovframework.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class ControlLogicSettingVO {

	public Integer logicSettingId;
	public Integer greenHouseId;
	public Integer defLogicId;
	public String defLogicName;
	public String javaClassName;
	public String logicName;
	public String logicEnv;
	public String logicPeriodEnv;
	public String logicPeriodSize;
	public String autoManualMode;
	public Date createDt;
	public Date updateDt;
	public Integer preOrderSettingId;
	public Object sensorData;
	public List<ControlLogicSettingCheckConditionVO> checkConditionList;
	public List<ControlLogicSettingDeviceVO> deviceList;

	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
