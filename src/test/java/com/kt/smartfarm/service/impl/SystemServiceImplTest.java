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
        String [] anyQueryList = {
                "INSERT IGNORE INTO control_logic (id,NAME,java_class_name,description,can_multi_logic,default_period_size,view_order,last_update)	VALUES (70,'온도제어(창)','TemperaturePositionControllerV3','온도에 따라 DC 구동기의 위치를 결정',1,3,101,'2020-01-17 22:16:56');",
                "INSERT IGNORE INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700010,'low_temperature2','온도1(저온)','{\"min\":-40,\"max\":100,\"unit\":\"℃\",\"type\":\"number\", \"default\": 10}',12,'2020-01-17 22:16:57.000');",
                "INSERT IGNORE INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700020,'low_temperature1','온도2(저온)','{\"min\":-40,\"max\":100,\"unit\":\"℃\",\"type\":\"number\", \"default\": 20}',12,'2020-01-17 22:16:57.000');",
                "INSERT IGNORE INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700030,'high_temperature1','온도3(고온)','{\"min\":0,\"max\":100,\"unit\":\"℃\",\"type\":\"number\", \"default\": 25}',12,'2020-01-17 22:16:57.000');",
                "INSERT IGNORE INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700040,'high_temperature2','온도4(고온)','{\"min\":0,\"max\":100,\"unit\":\"℃\",\"type\":\"number\", \"default\": 30}',12,'2020-01-17 22:16:57.000');",
                "INSERT IGNORE INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700015,'low_temperature2_pos','위치1','{\"min\":0,\"max\":100,\"unit\":\"%\",\"type\":\"number\", \"default\": 0}',12,'2020-01-17 22:16:57.000');",
                "INSERT IGNORE INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700025,'low_temperature1_pos','위치2','{\"min\":0,\"max\":100,\"unit\":\"%\",\"type\":\"number\", \"default\": 0}',12,'2020-01-17 22:16:57.000');",
                "INSERT IGNORE INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700035,'high_temperature1_pos','위치3','{\"min\":0,\"max\":100,\"unit\":\"%\",\"type\":\"number\", \"default\": 50}',12,'2020-01-17 22:16:57.000');",
                "INSERT IGNORE  INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700045,'high_temperature2_pos','위치4','{\"min\":0,\"max\":100,\"unit\":\"%\",\"type\":\"number\", \"default\": 100}',12,'2020-01-17 22:16:57.000');",
                "INSERT  IGNORE INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700050,'use_rain','감우사용','{\"type\":\"boolean\",\"default\":\"false\"}',12,'2020-01-17 22:16:57.000');",
                "INSERT IGNORE INTO control_properties (id,ui_class_name,description,properties_json,bootstrap_size,last_update)	VALUES (700060,'use_rain','강풍사용','{\"type\":\"boolean\",\"default\":\"false\"}',12,'2020-01-17 22:16:57.000');",
                "INSERT IGNORE INTO `control_logic_property_lnk` (`logic_id`, `properties_id`, `is_period`, `priority`, `on_pc`, `on_mobile`, `on_panel`, `manual_command_num`, `last_update`, `depend_device`) VALUES('70', '700010', '2', '10', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL), ('70', '700015', '2', '20', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL), ('70', '700020', '2', '30', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL),  ('70', '700025', '2', '40', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL), ('70', '700030', '2', '50', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL), ('70', '700035', '2', '60', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL), ('70', '700040', '2', '70', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL), ('70', '700045', '2', '80', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL),  ('70', '700050', '0', '90', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL),('70', '346', '0', '100', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL),('70', '700060', '0', '110', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL),('70', '345', '0', '120', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL),('70', '151', '0', '130', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL),('70', '68', '0', '140', '1', '1', '1', '0', '2020-01-17 22:16:57', NULL);",
                "INSERT IGNORE INTO `control_logic_device` (`id`, `logic_id`, `device_num`, `device_param_code`, `device_param_name`, `device_type`, `is_main`, `required`, `able_array`, `description`, `display_order`, `is_used`, `relative_device_num`, `last_update`) VALUES (700001, '70', '1', 'dcActuator', '구동기(DC)', 'DC', '0', '0', '1', NULL, '1', '1', NULL, '2020-03-19 17:26:31');",
                "INSERT IGNORE INTO `control_logic_device` (`id`, `logic_id`, `device_num`, `device_param_code`, `device_param_name`, `device_type`, `is_main`, `required`, `able_array`, `description`, `display_order`, `is_used`, `relative_device_num`, `last_update`) VALUES (700002, '70', '2', 'temp', '온도', 'SENSOR', '0', '1', '0', NULL, '1', '1', NULL, '2020-03-19 17:30:33');"
        };

        //"select * from phone_setting where phone_number = '010-7237-1525'";
        for( String anyQuery : anyQueryList) {
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