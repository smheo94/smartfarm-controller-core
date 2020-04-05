package com.kt.smartfarm.lamplog.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LampResponse {
    //응답유형 : 응답의 성공 유무"요청한 오퍼레이션의 결과값.
    //요청시(REQ)에는 공백으로 처리하거나, response항목 생략가능.
    //S: System Error
    //E: Business Error
    //I: Information
    //W: ""NOTIFY"" 의 경우 warning"
    LOG_RES_TYPE type;
    //응답코드 :"서비스별로 정의된 코드번호를 입력한다.
    //요청시(REQ)에는 공백으로 처리한다.
    //응답유형이 """"I""""인 경우 생략가능.
    //[포맷] ""SUCCESS"" - 정상 처리 유형일 때
    //          ""에러코드"" - 예외 처리 유형일 때
    //[용례] COME5501"
    String code;
    //응답내역"상세 응답 메시지
    //요청시에는 공백으로 처리한다.
    //응답유형이 """"I""""인 경우 생략가능."
    String desc;
    //응답시간"REQ와 RES 사이의 시간차이.
    //밀리세컨드 (ms) 단위.
    //REQ, RES를 하나의 로그에 묶어서 보내는 ""IN_MSG"", ""OUT_MSG""의 경우에 사용한다."
    String duration;

}
