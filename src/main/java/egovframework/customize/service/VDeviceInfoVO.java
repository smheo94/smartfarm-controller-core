package egovframework.customize.service;

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
public class VDeviceInfoVO {
	Integer id;
	Integer deviceTypeId;
	Integer deviceNum; // device 개수
	String deviceTypeParamName; 
	Integer required; // 필수 여부
	Integer ableArray; // 여러개 등록 여부
	String description; 
	Integer displayOrder; // displayOrder 넘버링
	Integer isUsed;
}
