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
	Long logicId;
	Integer deviceNum;
	String deviceParamName;
	String deviceType;
	Integer isMain;
	Integer ableArray;
	Integer description;	
	Integer displayOrder;
	Integer isUsed;
	Integer required;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
