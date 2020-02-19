package com.kt.smartfarm.service;

import com.kt.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class DiaryTypePropertiesVO {
	Long diaryTypeId;
	Long propertiesId;
	String uiClassName;
	String displayText;
	Integer supportStt;
	String inputHelpText;
	Integer inputHelpWaiting;
	String validText;
	Integer validWaiting;
	Integer validButtonType;
	String detailHelpText;
	Integer isArray;
	Integer dataLevel;
	String propertyDataType;
	Integer propertyDataLength;
	Map<String,Object> propertiesJson;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
