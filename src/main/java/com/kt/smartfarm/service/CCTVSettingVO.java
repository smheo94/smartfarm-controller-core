package com.kt.smartfarm.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	Long houseId;
	String cctvName;
	String cctvUid;
	String cctvPwd;
	String cctvWsUrl;
	String cctvRtspUrl;
	String miniVmsUrl;
	Integer miniVmsIdx;
	String rtcStreamHash;
	String isRtspPlay;
	String isMiniVms;
	String enablePtz;
	String wowzaId;
	String wowzaStreamUrl;
	String wowzaRestUrl;
//	@JsonIgnore
//	String gsmSystemHost;
//	@JsonIgnore
//	String gsmSystemPort;
}
