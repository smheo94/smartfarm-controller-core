package egovframework.customize.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 외부기상대 설정 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class ThresholdVO {
	// gsmKey
	Integer gsmKey;
	
	Integer greenHouseId;
	// 수정일자
	String updateDate;

	String deviceType;
	Integer deviceTypeIdx;
	// 외부저습
	Integer lowValue;
	// 풍속
	Integer highValue;
	
	public String getDeviceTypeKey() {
		return deviceType + "$" + deviceTypeIdx;
	}
}
