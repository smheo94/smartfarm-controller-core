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
public class CommonEnvVO {
	Integer gsmKey;
	String envName;
	String envJsonValue;
	String envJsonDefault;
	String updateTime;	
}
