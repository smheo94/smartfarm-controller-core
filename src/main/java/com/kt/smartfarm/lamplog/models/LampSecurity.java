package com.kt.smartfarm.lamplog.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LampSecurity {
    //보안침해 탐지 로그유형"
    LOG_SECURITY_TYPE type;
    //실행이벤트
    LOG_SECURITY_EVENT event;
    //대상자
    String target;
    //다량대상자
    String multiTarget;
    //상세내역
    String detail;
    //사유
    String reason;
    //개인정보리스트
    List<LOG_SECURITY_PERSONAL_INFO> personalInfoList;
}
