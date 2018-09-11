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
public class ControlLogicDeviceVO {
	Integer id;
	Integer logicId;
	Integer deviceNum;
	String deviceParamName;
	String deviceType;
	Integer isMain;
	Integer ableArray;
	Integer description;	
	Integer diplayOrder;
	Integer isUsed;
	
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
