package egovframework.customize.service;

import java.util.HashMap;
import java.util.List;

public interface ControllerInfoService {	
	ControllerInfoVO get(Integer controllerInfoId);
	List<ControllerInfoVO> list();
}
