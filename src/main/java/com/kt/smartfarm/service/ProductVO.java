package com.kt.smartfarm.service;

import java.util.HashMap;
import java.util.List;

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
public class ProductVO {
	Integer id;
	String product;
	String productSpecies;
	String productCategory;
	String description;
	String inputDate;
	String updateData;
	String author;
	String version;
	String productI18n;
	String productSpeciesI18n;
	String productCategoryI18n;
//	List<ProductMethodVO> productMethod;
	List<HashMap<String,Object>> ProductMethod;
}
