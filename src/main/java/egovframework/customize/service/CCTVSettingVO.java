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
public class CCTVSettingVO {
	Integer id;
	Integer houseId;
	String cctvName;
	String cctvIp;
	String cctvPort;
	String cctvId;
	String cctvPwd;
	String wowzaId;
	String wowzaPwd;
}
