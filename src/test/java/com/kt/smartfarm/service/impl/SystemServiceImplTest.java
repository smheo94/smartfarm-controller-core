package com.kt.smartfarm.service.impl;

import com.kt.cmmn.util.JasyptUtil;
import com.kt.cmmn.util.MapUtils;
import com.kt.cmmn.util.RestClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class SystemServiceImplTest {

    @Test
    public void encrypt(){
        //String anyQuery = "select * from event where gsm_key = 1110 order by id desc  limit 20";
        String anyQuery = "UPDATE `device_v_dep_device` SET `device_num` = '2' WHERE id = 82";

        //String anyQuery = "SELECT * FROM  device_v_dep_device WHERE p_device_id = '637100053'";

        //SELECT * FROM `sf_main`.`oauth_client_details` LIMIT 0, 1000;
        String key = "**" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + "123";
        String result = JasyptUtil.encrypt(key, anyQuery);
        System.out.println(result);
        String oAuthserver = "http://dev1705.vivans.net:47900";
        //String oAuthserver = "https://oauth-smartfarm.argos-labs.com";

        String apiUrl ="http://dev1705.vivans.net:47100/env/system/data/";
        //String apiUrl = "https://apismartfarm.kt.co.kr/env/system/data/";


//        RestClientUtil client = new RestClientUtil(oAuthserver, "kt-gsm-controller", "05dc8fbd5e01a6625e8a6eb1ddf482c9");//"51724690439c3e4e89a9efc5e2ca567d5e4b09e6fa69a06bd65e7597418737cc");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("any_query", anyQuery );
        param.put("enc", result);
        param.put("query_type", "select");
        log.info(MapUtils.toJson(param));

        log.info(JasyptUtil.decrypt(key, result));
//        ResponseEntity<HashMap<String, Object>> hashMapResponseEntity = client.oauth2exchange(apiUrl, HttpMethod.GET, param, false);
//        if( hashMapResponseEntity != null && hashMapResponseEntity.getStatusCode().is2xxSuccessful()) {
//            log.info( "Result :  {}", hashMapResponseEntity.getBody());
//        }

        assertTrue(true);
////        client.oauth2exchange("https://apismartfarm.kt.co.kr/system/data/",  )

        //        Map
//        client.oauth2exchange("https://apismartfarm.kt.co.kr/system/data/",  )

    }

}