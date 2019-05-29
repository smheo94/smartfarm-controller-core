package com.kt.smartfarm.service;

import java.util.List;

public interface ControllerInfoService {	
	ControllerInfoVO get(Integer controllerInfoId);
	List<ControllerInfoVO> list();
}
