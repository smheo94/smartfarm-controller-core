package com.kt.smartfarm.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * 당도데이터
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class SweetContentVO {
	Date startDate;
	Integer signNumber;
	Double sugarContent;
}
