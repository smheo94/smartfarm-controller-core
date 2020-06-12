package com.kt.smartfarm.service.impl;

import com.kt.cmmn.util.JasyptUtil;
import com.kt.cmmn.util.MapUtils;
import com.kt.cmmn.util.RestClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.assertTrue;

@Slf4j
public class SystemServiceImplTest {

    public enum QUERY_TYPE {
        select, update
    }
    @Test
    public void hashTest() {
        assertTrue(("adb" + "def").hashCode() == ("adb" + "def").hashCode());
    }
    @Test
    public void encrypt(){

        //"SELECT TABLE_NAME, update, COLUMN_TYPE FROM  information_schema.COLUMNS  WHERE TABLE_SCHEMA = 'sf_main_2'"
        String queryType = QUERY_TYPE.update.name();
        String [] anyQueryList = {
//                "ALTER TABLE `user_info`   " +
//                        "  ADD COLUMN `old_pwd1` LONGTEXT NULL AFTER `is_deleted`," +
//                        "  ADD COLUMN `old_pwd2` LONGTEXT NULL AFTER `old_pwd1`," +
//                        "  ADD COLUMN `old_pwd_update1` DATETIME NULL AFTER `old_pwd2`," +
//                        "  ADD COLUMN `old_pwd_update2` DATETIME NULL AFTER `old_pwd_update1`"
                //"UPDATE gsm_info SET system_host = 'epistest.vivans.net' where gsm_key = 338001"
                //"UPDATE gsm_info SET system_host = 'epis0001.ddns.net' where gsm_key = 338001"
                //"UPDATE control_setting SET logic_period_env = '[]' WHERE logic_period_env = '{}'"
        //"ALTER TABLE `control_setting_device` DROP FOREIGN KEY `fx_control_setting_device`;"
        };
        String  anyQueryList2 =
                "UPDATE `gsm_info` SET `system_host` = 'sf-005452.iptime.org', system_port = 30005   WHERE `gsm_key` = '5452'; ";
        List<String> queryList = new ArrayList<>();
        if( anyQueryList2.length() > 0 ) {
            queryList.addAll(Arrays.asList(anyQueryList2.split(";")));
        }
        if( anyQueryList.length > 0 ) {
            queryList.addAll(Arrays.asList(anyQueryList));
        }
        //"select * from phone_setting where phone_number = '010-7237-1525'";
        for( String anyQuery : queryList) {
            anyQueryTest(queryType, anyQuery);
        }

        assertTrue(true);
////        client.oauth2exchange("https://apismartfarm.kt.co.kr/system/data/",  )

        //        Map
//        client.oauth2exchange("https://apismartfarm.kt.co.kr/system/data/",  )

    }

    private void anyQueryTest(String queryType, String anyQuery) {
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
        //String oAuthserver = "http://test.oauthsmartfarm.kt.co.kr";

        //String apiUrl = "http://dev1705.vivans.net:47100/env/system/data/";
        String apiUrl = "https://apismartfarm.kt.co.kr/env/system/data/";
        //String apiUrl = "http://test.apismartfarm.kt.co.kr/env/system/data/";


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
            List<Map<String,Object>> dataList = (List<Map<String,Object>>)hashMapResponseEntity.getBody().get("data");
            if( dataList != null ) {
                printData(dataList);
            }
        }
    }

    private void printData(List<Map<String, Object>> dataList) {
        Map<String,Object> headerLine = dataList.get(0);
        StringBuilder sb = new StringBuilder();
        headerLine.keySet().stream().forEachOrdered(k -> sb.append(k).append(",") );
        dataList.stream().forEachOrdered(d -> {
            sb.append("\n");
            d.values().stream().forEachOrdered(v -> sb.append(v).append(","));
        });
        sb.append("\n");
        log.info("DATA : \n {}", sb.toString());
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