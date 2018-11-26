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
	// 수정일자
	String updateDate;
	// 외부고온
	Double outsideHighTemp;	
	// 외부저온
	Double outsideLowTemp;
	// 외부고습
	Double outsideHighHum;
	// 외부저습
	Double outsideLowHum;
	// 풍속
	Integer windSpeed;
	// 감우감지
	Integer rainDetector;
	// 내부고온
	Double insideHighTemp;
	// 내부저온
	Double insideLowTemp;
	// 내부고습
	Double insideHighHum;
	// 내부저습
	Double insideLowHum;
	// 외부고온사용
	Integer outsideHighTempUse;
	// 외부저온사용
	Integer outsideLowTempUse;
	// 외부고습사용
	Integer outsideHighHumUse;
	// 외부저습사용
	Integer outsideLowHumUse;
	//풍속사용
	Integer windSpeedUse;
	// 감우갖미 사용
	Integer rainDetectorUse;
	// 내부고온사용
	Integer insideHighTempUse;
	// 내부저온사용
	Integer insideLowTempUse;
	// 내부고습사용
	Integer insideHighHumUse;
	// 내부저습사용
	Integer insideLowHumUse;
}
