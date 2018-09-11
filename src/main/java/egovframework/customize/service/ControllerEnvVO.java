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
public class ControllerEnvVO {
	Integer id;
	String gsmKey;	
	String controllerInfoId;
	String ip;
	String controllerType;
	String controllerStatus;
	String description;
	Integer port;
	ControllerInfoVO controllerInfo;
	
//	List<ArrayDeviceVO> arrayDeviceData;
	List<DeviceEnvVO> deviceList;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
