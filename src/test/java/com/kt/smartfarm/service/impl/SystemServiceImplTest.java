package com.kt.smartfarm.service.impl;

import com.kt.cmmn.util.JasyptUtil;
import com.kt.cmmn.util.MapUtils;
import com.kt.cmmn.util.RestClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@Slf4j
public class SystemServiceImplTest {

    public enum QUERY_TYPE {
        select, update
    }
    @Test
    public void encrypt(){

        String queryType = QUERY_TYPE.update.name();
        String anyQuery = "INSERT INTO sf_main_2.alarm_setting (\n" +
                "        id, user_idx, user_name, green_house_id, event_type, event_sub_type, on_off, phone_number, sms_push\n" +
                ")\n" +
                "SELECT id, user_idx, user_name, green_house_id, event_type, event_sub_type, on_off, phone_number, sms_push FROM sf_main.alarm_setting;";

        //String anyQuery = "select * from event where gsm_key = 1110 order by id desc  limit 20";
        //String anyQuery = "UPDATE `device_v_dep_device` SET `device_num` = '1' WHERE id = 82";

        //String anyQuery = /*[오후 1:45:53][3 ms]*/ "SELECT * FROM push_history LIMIT 1000";
        //String anyQuery = /*[오후 1:45:53][1 ms]*/ "SHOW KEYS FROM `sf_main_kt`.`control_logic_property_lnk`;";
        //String anyQuery = /*[오후 1:45:53][1 ms]*/ "SHOW CREATE TABLE `sf_main_kt`.`control_logic_property_lnk`;";
        //String anyQuery = "SELECT * FROM`1  device_v_dep_device WHERE p_device_id = '637100053'";

        //SELECT * FROM `sf_main`.`oauth_client_details` LIMIT 0, 1000;
        String key = "**" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + "123";
        String result = JasyptUtil.encrypt(key, anyQuery);
        System.out.println(key);
        System.out.println(result);

        //String oAuthserver = "http://dev1705.vivans.net:47900";
        //String oAuthserver = "https://oauth-smartfarm.argos-labs.com";
        String oAuthserver = "https://oauthsmartfarm.kt.co.kr";

        //String apiUrl ="http://dev1705.vivans.net:47100/env/system/data/";
        String apiUrl = "https://apismartfarm.kt.co.kr/env/system/data/";


        //RestClientUtil client = new RestClientUtil(oAuthserver, "kt-gsm-controller", "05dc8fbd5e01a6625e8a6eb1ddf482c9");//"51724690439c3e4e89a9efc5e2ca567d5e4b09e6fa69a06bd65e7597418737cc");
        RestClientUtil client = new RestClientUtil(oAuthserver, "kt-gsm-controller", "05dc8fbd5e01a6625e8a6eb1ddf482c9");//"51724690439c3e4e89a9efc5e2ca567d5e4b09e6fa69a06bd65e7597418737cc");

        client.getClient().setRequestFactory(new HttpComponentsClientHttpRequestWithBodyFactory());

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("any_query", anyQuery );
        param.put("enc", result);
        param.put("query_type", queryType);
        log.info(MapUtils.toJson(param));

        log.info(JasyptUtil.decrypt(key, result));

        ResponseEntity<HashMap<String, Object>> hashMapResponseEntity = client.oauth2exchange(apiUrl, HttpMethod.GET, param, false);
        if( hashMapResponseEntity != null && hashMapResponseEntity.getStatusCode().is2xxSuccessful()) {
            log.info( "Result :  {}", hashMapResponseEntity.getBody());
        }

        assertTrue(true);
////        client.oauth2exchange("https://apismartfarm.kt.co.kr/system/data/",  )

        //        Map
//        client.oauth2exchange("https://apismartfarm.kt.co.kr/system/data/",  )

    }


    private static final class HttpComponentsClientHttpRequestWithBodyFactory extends HttpComponentsClientHttpRequestFactory {
        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (httpMethod == HttpMethod.GET) {
                return new HttpGetRequestWithEntity(uri);
            }
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

    private static final class HttpGetRequestWithEntity extends HttpEntityEnclosingRequestBase {
        public HttpGetRequestWithEntity(final URI uri) {
            super.setURI(uri);
        }

        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }


}