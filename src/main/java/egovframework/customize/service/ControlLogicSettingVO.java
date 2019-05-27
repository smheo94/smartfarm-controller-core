package egovframework.customize.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	public Integer controlSettingId;
	public Integer greenHouseId;
	public Integer logicId;
	public String defLogicName;
	public String javaClassName;
	public String logicName;
	public String logicEnv;
	public String logicPeriodEnv;
	public Integer logicPeriodSize;
	public String autoManualMode;
	public Date createDt;
	public Date updateDt;
	public Integer preOrderSettingId;
	public Object sensorData;
	public Integer defaultPeriodSize;
	public List<ControlLogicSettingCheckConditionVO> checkConditionList;
	public List<ControlLogicSettingDeviceVO> deviceList;

	@JsonIgnore
	public Integer tmpGsmKey;

	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
