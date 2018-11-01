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
public class ControlLogicSettingDeviceVO {
	Integer id;
	Integer  controlSettingId;
	Integer logicId;
	Integer deviceNum;
	Integer deviceInsertOrder;
	Integer deviceId;
	Integer required;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
