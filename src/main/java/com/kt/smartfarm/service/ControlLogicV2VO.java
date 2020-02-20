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
public class ControlLogicV2VO extends ControlLogicVOOrigin {
	public List<ControlLogicPropertiesVO> controlPropertyList;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
