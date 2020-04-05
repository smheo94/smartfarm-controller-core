package com.kt.smartfarm.lamplog.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LampCaller {
    //채널키 :서비스요청자를 구분하는 고유키 //SHUB의 경우 CP 인증키
    String channel;
    //채널IP : "유입 요청 트래픽을 보낸 클라이언트 IP
    String channelIp;
}
