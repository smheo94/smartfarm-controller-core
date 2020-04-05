package com.kt.smartfarm.lamplog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kt.smartfarm.lamplog.models.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LampLog {

    @JsonIgnore
    Long realTimeStamp;
    //1.로그생성시간
    @Builder.Default
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    //2.서비스코드
    String service;
    //3.오퍼레이션
    String operation;
    //4.비지니스거래번호
    String bizTransactionId;
    //5.거래번호
    String transactionId;
    //6.로그유형
    LOG_TYPE logType;
    //7.시퀀스 번호
    String seq;
    //8.요청/응답전문
    String payload;
    //9.요청정보
    LampCaller caller;
    //10.호스트정보
    LampHost host;
    //11.응답정보
    @Builder.Default
    LampResponse response ;
    //12.사용자정보
    LampUser user;
    //13.단말정보
    String device;
    //14.목적지정보
    LampHost destination;
    //15.URL 정보
    String url;
    //16.보안침해     탐지 정보             (※Biz. 트랜젝션 감시 전용은                     해당사항                     없음)"
    LampSecurity security;
    public void addSecurity(LOG_SECURITY_TYPE type, LOG_SECURITY_EVENT _event, String target) {
        security = LampSecurity.builder().type(type).event(_event).target(target).build();
    }
    public void addSecurity(LOG_SECURITY_TYPE type, LOG_SECURITY_EVENT _event) {
        security = LampSecurity.builder().type(type).event(_event).build();
    }
    public void addResponse(LOG_RES_TYPE type, String responseCode, String responseDesc) {
        long duration = System.currentTimeMillis() - realTimeStamp;
        response = LampResponse.builder().type(type).code(responseCode).desc(responseDesc)
                .duration(String.valueOf(duration)).build();
    }
    public void success() {
        addResponse(LOG_RES_TYPE.I, "200", "OK");
    }
    public void forbidden() {
        addResponse(LOG_RES_TYPE.E, "403", "Not Allowed");
    }
    public void exception(String reason) {
        addResponse(LOG_RES_TYPE.S, "500", reason);
    }
    public void error(String reason) {
        addResponse(LOG_RES_TYPE.E, "401", reason);
    }
    public void error(String code, String reason) {
        addResponse(LOG_RES_TYPE.E, code, reason);
    }
}
