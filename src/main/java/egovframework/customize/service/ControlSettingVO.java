package egovframework.customize.service;

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
public class ControlSettingVO {
	Integer id;
	Integer greenHouseId;
	String logicName;
	Integer logicId;
	Integer logicPeriodSize;
	String logicEnv;
	String logicPeriodEnv;
	String createDt;
	String updateDt;
	String autoManualMode;
	String javaClassName;
		

	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
