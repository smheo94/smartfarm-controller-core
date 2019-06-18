package com.kt.smartfarm.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 외부기상대, 임계치 설정 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class ProductMethodVO {
	Integer id;
	Integer productId;
	String growthLevel;
	String growthLevelDescription;
	String growthType;
	String startCond;
	String endCond;
	String description;
	String inputDate;
	String updateDate;
	String author;
	Integer editable;
	String growthLevelI18n;
	String growthLevelDescriptionI18n;
	String growthTypeI18n;
}
