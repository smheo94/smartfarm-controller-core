package com.kt.smartfarm.service.impl;

import com.kt.cmmn.util.*;
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
                "\n" +
                        "INSERT INTO `SDK_SMS_SEND` (`MSG_ID`, `USER_ID`, `SCHEDULE_TYPE`, `SUBJECT`, `SMS_MSG`, `CALLBACK_URL`, `NOW_DATE`, `SEND_DATE`, `CALLBACK`, \n" +
                        "`DEST_TYPE`, `DEST_COUNT`, `DEST_INFO`, `KT_OFFICE_CODE`, `CDR_ID`, `RESERVED1`, `RESERVED2`,\n" +
                        " `RESERVED3`, `RESERVED4`, `RESERVED5`, `RESERVED6`, `RESERVED7`, `RESERVED8`, `RESERVED9`, `SEND_STATUS`, `SEND_COUNT`, `SEND_RESULT`, `SEND_PROC_TIME`, `STD_ID`) VALUES('1','saup2farm01','0',NULL,'[임계치초과 발생] 메인테스트(무안 테스트 농가1) 온도1(31.0) 기준(20) 초과','\n" +
                        " ','202006301731441','202006301731441','070-7450-5274','0','0','^01034496214',\n" +
                        "NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','0','0',NULL,NULL)";
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


    @Test
    public void selectAny() {
        anyQueryTestList(QUERY_TYPE.select.name(),"SELECT * FROM SDK_SMS_REPORT ORDER BY  MSG_ID DESC LIMIT 10",
                "SELECT * FROM SDK_SMS_REPORT_DETAIL ORDER BY MSG_ID DESC LIMIT 10"
        //"SELECT * FROM alarm_setting where phone_number = '01034496214'"
        );
        assertTrue(true);
    }
    @Test
    public void updateAny() {
        anyQueryTest(QUERY_TYPE.update.name(),
                "");
        assertTrue(true);
    }


    @Test
    public void getUserInfo() {
        anyQueryTest(QUERY_TYPE.select.name(),
                "SELECT C.phone_number, G.house_name, GI.farm_nickname, C.sms_push, C.event_type, C.event_sub_type, C.on_off, C.user_idx AS user_info_id, PS.token\n" +
                        "\t\tFROM alarm_setting C\n" +
                        "\t    LEFT JOIN phone_setting PS ON PS.user_idx = C.user_idx\n" +
                        "\t\tLEFT JOIN (\n" +
                        "\t\t\tSELECT DISTINCT gsm_key, user_idx\n" +
                        "\t\t\tFROM\n" +
                        "\t\t\t(\n" +
                        "\t\t\tSELECT DISTINCT gi.gsm_key, ua.user_idx\n" +
                        "\t\t\tFROM gsm_category_view gc\n" +
                        "\t\t\tINNER JOIN gsm_info gi ON gi.category_id = gc.child_category_id\n" +
                        "\t\t\tINNER JOIN auth_group_detail ad ON ad.category_id = gc.category_id\n" +
                        "\t\t\tINNER JOIN user_info_auth_group ua ON ua.auth_group_id = ad.group_id\n" +
                        "\t\t\tUNION ALL\n" +
                        "\t\t\tSELECT DISTINCT ad.gsm_key,  ua.user_idx\n" +
                        "\t\t\tFROM auth_group_detail ad\n" +
                        "\t\t\tINNER JOIN user_info_auth_group ua ON ua.auth_group_id = ad.group_id\n" +
                        "\t\t\tWHERE ad.category_id = '0'\n" +
                        "\t\t\tUNION ALL\n" +
                        "\t\t\tSELECT gsm_key , user_info_id AS user_idx\n" +
                        "\t\t\tFROM gsm_info\n" +
                        "\t\t\t) A\n" +
                        "\t\t) UAUTH ON UAUTH.user_idx = C.user_idx \n" +
                        "\t\t\tAND UAUTH.gsm_key = 6370\n" +
                        "\t\tLEFT JOIN gsm_info GI ON GI.gsm_key = GI.gsm_key AND GI.gsm_key = UAUTH.gsm_key\n" +
                        "\t\tLEFT JOIN green_house G ON G.gsm_key = GI.gsm_key\n" +
                        "\t\tWHERE  C.on_off  = '1' AND ( G.id = 637000033 OR ( 6370 != null AND  G.id IS NULL ))"
        );

        assertTrue(true);
    }
    @Test
    public void updateGSMHost() {
        Integer gsm_key = 618316;
        Integer port = 30005;
        String baseDomain ="vivans.net";
        String qText = String.format("UPDATE `gsm_info` SET `system_host` = 'sf-%1$06d.%3$s', system_port = %2$s, master_system_port =%2$s," +
                "master_system_host = 'sf-%1$06d.%3$s'    WHERE `gsm_key` = '%1$s'; ",
                gsm_key, port, baseDomain);
        log.info(qText);
        anyQueryTest(QUERY_TYPE.update.name(), qText);
        assertTrue(true);
    }

    @Test
    public void selectUser() {
        String userName = "화수" ;

        anyQueryTest(QUERY_TYPE.select.name(), "SELECT * FROM user_info WHERE user_name LIKE '%" + userName + "%'");

        //anyQueryTest(QUERY_TYPE.select.name(), "SELECT * FROM user_info WHERE user_id = 'taebaek012'");

        assertTrue(true);
    }
    @Test
    public void updateUserPassword() {
        String userId = "taebaek013" ;
        String newP = "ghktn12#";
        SHA512PasswordEncoder encoder = new SHA512PasswordEncoder();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MILLISECOND, 0);
        String salt = String.format("%1$s%1$s", c.getTimeInMillis());
        String encodedPwd = encoder.encode(newP+salt);
        Date pwdUpdateDate = c.getTime();
        String dateStr = DateUtil.getDateTimeStr(pwdUpdateDate);
        anyQueryTest(QUERY_TYPE.update.name(), String.format("  UPDATE user_info  SET update_date = NOW() , pwd = '%s', pwd_update_date = '%s'" +
                " Where user_id = '%s'",encodedPwd, dateStr, userId));
        assertTrue(true);
    }

    @Test
    public void insertNewCropDiary() {
        anyQueryTest(QUERY_TYPE.update.name(), "INSERT INTO `control_setting_liquid` " +
                "(`id`, `publish_level`, `title`, `prd_code`, `liquid_id`, `packing_size`, `mix_type`, `mix_rate`, `watering_amt`, `comment`) " +
                "VALUES ('601', 'OPEN', '블루베리-비료', '0601', 'LIQ', '20', 'RATIO', '200', '600', '기본 희석비'); ");
//        anyQueryTest(QUERY_TYPE.update.name(), "UPDATE `control_setting_liquid` " +
//                "SET title ='사과-비료' WHERE id = 601;");

    }
    @Test
    public void insertCDLLIQUID() {
        anyQueryTest(QUERY_TYPE.update.name(), "INSERT INTO `cd_liquid` (`liquid_id`, `liquid_name`, `default_mix_rate`) VALUES ('ENZ', '효소', '200'); ");
        assertTrue(true);
    }
    @Test
    public void deletePhoneSetting() {
        anyQueryTest(QUERY_TYPE.update.name(), "DELETE  FROM phone_setting WHERE phone_number='010-7237-1525'");
        assertTrue(true);
    }
    @Test
    public void getPhoneSetting() {
        anyQueryTest(QUERY_TYPE.select.name(), "SELECT * FROM phone_setting " +
                " WHERE phone_number='01072371525'");
        assertTrue(true);
    }
    @Test
    public void deleteNOTINDEVICEList() {
        anyQueryTest(QUERY_TYPE.update.name(), "DELETE FROM `event` WHERE ( device_id != 0 AND device_id NOT IN ( SELECT device_id FROM map_green_house_device ) )");
        assertTrue(true);
    }
    @Test
    public void getGSMStatus() {
        anyQueryTest(QUERY_TYPE.select.name(), "SELECT gsm_key, system_version, FROM_UNIXTIME(update_date/1000000) AS onTime FROM gsm_status ");
        assertTrue(true);
    }
    @Test
    public void updatepdTest() {

        String s = "";
        SHA512PasswordEncoder encoder = new SHA512PasswordEncoder();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MILLISECOND, 0);
        String salt = String.format("%1$s%1$s", c.getTimeInMillis());
        String encodedPwd = encoder.encode(s+salt);
        log.info( "'{}', '{}', '{}', '{}'", s, encodedPwd, c.getTime(), salt );


        assertTrue(true);
    }
    private void anyQueryTestList(String queryType, String ... anyQuery ) {
        for(String q : anyQuery) {
            anyQueryTest(queryType, q);
        }
    }
    private void anyQueryTest(String queryType, String  anyQuery ) {
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
            d.values().stream().forEachOrdered(v -> sb.append( String.valueOf(v).replaceAll("\n","") ).append(","));
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