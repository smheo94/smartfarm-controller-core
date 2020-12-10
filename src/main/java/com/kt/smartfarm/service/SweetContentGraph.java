package com.kt.smartfarm.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * 당도데이터
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class SweetContentGraph {
	List<SweetContentVO> lastSweetContentList;
	List<SweetContentVO> comareSweetContentList;
}
