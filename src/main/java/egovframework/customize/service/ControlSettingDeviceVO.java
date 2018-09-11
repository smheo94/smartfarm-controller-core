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
public class ControlSettingDeviceVO {
	Integer id;
	Integer  controllerSettingId;	
	Integer logicId;
	Integer deviceNum;
	String deviceInsertOrder;
	String deviceId;
	
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
