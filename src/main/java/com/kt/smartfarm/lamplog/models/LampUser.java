package com.kt.smartfarm.lamplog.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LampUser {

    //사용자아이디"사용자아이디
    //IAMUI :  사용자별 ID 사용
    //CP/SP :  사용자 ID 사용
    //- 플랫폼 서비스 내용에 따라, 사용자를 구분할 수 있는 정보를 입력한다."
    String id;
    //사용자IP"사용자  IP
    //[포맷] 1) IPv4 - nnn.nnn.nnn.nnn (n: 10진수 0-9)    "
    String ip;
    //사용자유형 : "사용자의 유형, 생략될 경우 user
    //""user"" : 서비스 사용자
    //""admin"" : 서비스 운영자
    //""manager"" : 서비스 운영자(운영자 승인 및 권한 부여가능)
    //admin/manager 는 IN_MSG 보안 로그에서 만 사용"
    String type;
    //브라우저 정보
    String agent;


}
