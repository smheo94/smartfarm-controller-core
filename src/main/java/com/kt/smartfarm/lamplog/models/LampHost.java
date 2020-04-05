package com.kt.smartfarm.lamplog.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LampHost {
    //호스트명:로그를 남기는 해당 서버의 Hostname
    String name;
    //호스트IP"로그를 생성하는 서버의 IP
    //[포맷] 1) IPv4 - nnn.nnn.nnn.nnn (n: 10진수 0-9)    "
    String ip;
}
