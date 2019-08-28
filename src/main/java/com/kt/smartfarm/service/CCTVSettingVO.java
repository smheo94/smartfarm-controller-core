package com.kt.smartfarm.service;

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
public class CCTVSettingVO {
	Integer id;
	Integer houseId;
	String cctvName;
	String cctvUid;
	String cctvPwd;
	String cctvWsUrl;
	String cctvRtspUrl;
	String isRtspPlay;
	String enablePtz;
	String wowzaId;
	String wowzaStreamUrl;
	String wowzaRestUrl;
}
