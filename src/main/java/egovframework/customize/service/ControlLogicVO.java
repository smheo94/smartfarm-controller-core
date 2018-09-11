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
public class ControlLogicVO {
	Integer id;
	String name;
	String javaClassName;
	String uiClassName;
	String description;
	String uiHelp;
	Integer canMultiLogic;
	Integer defaultPeriodSize;	

	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
