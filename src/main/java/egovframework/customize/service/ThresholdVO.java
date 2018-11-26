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
	Double stormLimit;	
	// 외부저온
	Double stormSensingDelay;
	// 
	Double stormTerminateDelay;
	// 풍향 대기 시간
	Double windDirectionWaitingTime;
	// 풍향 각도
	Double windDirectionDegree;
	// 감우 감지 딜레이
	Double rainSensingDelay;
	// 감우 종료 딜레이
	Double rainTerminateDelay;
	// 좌천창 각도
	Double topWindowLeftDegree;
	// 우천창 각도
	Double topWindowRightDegree;
	// 센서 선택
	Double sensorSelect;
	// 바람 측정 여부
	Integer useWindDetector;
	// 감우 측정 여부
	Integer useRainDetector;
	// 환기창 바람 측정 여부
	Integer exWindowUseWindDetector;
	// 환기창 감우 측정 여부
	Integer exWindowUseRainDetector;
	// 측창 바람 측정 여부
	Integer sideWindowUseWindDetector;
	// 측창감우측정여부
	Integer sideWindowUseRainDetector;
	//천창
	Integer baseForwardWindow;
	// 구동기 초기화 온도
	Integer actuatorInitTemp;
	// 기상대 mqtt host
	String weatherMqttHost;
	// 기상대 mqtt port
	Integer weatherMqttPort;

}
