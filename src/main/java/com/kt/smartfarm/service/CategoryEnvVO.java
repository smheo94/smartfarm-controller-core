package com.kt.smartfarm.service;

import java.util.Map;

import com.kt.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryEnvVO {
	Integer id;
	String name;	
	Integer level;
	Integer pid;
	Integer order;	
	
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
