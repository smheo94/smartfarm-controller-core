package com.kt.smartfarm.service.impl;

import com.kt.cmmn.util.JasyptUtil;
import com.kt.cmmn.util.RestClientUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SystemServiceImplTest {

    @Test
    public void encrypt(){
        String anyQuery = "query";
        String result = JasyptUtil.encrypt("**" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + "123", anyQuery);
        System.out.println(result);
        RestClientUtil client = new RestClientUtil("https://oauth-smartfarm.argos-labs.com", "kt-gsm-controller", "51724690439c3e4e89a9efc5e2ca567d5e4b09e6fa69a06bd65e7597418737cc");

        Map<String, Object> param = new HashMap<String, Object>();
//        param.
////        client.oauth2exchange("https://apismartfarm.kt.co.kr/system/data/",  )

        //        Map
//        client.oauth2exchange("https://apismartfarm.kt.co.kr/system/data/",  )

    }

}