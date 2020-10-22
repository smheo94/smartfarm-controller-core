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
        String queryType = QUERY_TYPE.update.name();
        String [] anyQueryList = {};
        String  anyQueryList2 =
               "INSERT INTO `control_properties`\n" +
                       "(`id`, `ui_class_name`, `description`, `ui_help`, `properties_json`, `min_value`, `max_value`, `display_unit`, `default_value`, `default_min_value`, `default_max_value`, `bootstrap_size`, `dep_class_comp_up`, `dep_class_comp_down`, `last_update`) \n" +
                       "VALUES ('100001', 'add_open_time', '열림추가시간', NULL, '{\\\"min\\\":0,\\\"max\\\":2000,\\\"unit\\\":\\\"초\\\",\\\"type\\\":\\\"number\\\", \\\"default\\\": 100}', NULL, NULL, NULL, NULL, NULL, NULL, '12', NULL, NULL, '2020-09-04 20:02:54'); \n" +
                       "INSERT INTO `control_properties`\n" +
                       "(`id`, `ui_class_name`, `description`, `ui_help`, `properties_json`, `min_value`, `max_value`, `display_unit`, `default_value`, `default_min_value`, `default_max_value`, `bootstrap_size`, `dep_class_comp_up`, `dep_class_comp_down`, `last_update`) \n" +
                       "VALUES ('100002', 'add_close_time', '닫힘추가시간', NULL, '{\\\"min\\\":0,\\\"max\\\":2000,\\\"unit\\\":\\\"초\\\",\\\"type\\\":\\\"number\\\", \\\"default\\\": 100}', NULL, NULL, NULL, NULL, NULL, NULL, '12', NULL, NULL, '2020-09-04 20:02:54'); \n" +
                       "\n" +
                       "INSERT INTO control_logic_property_lnk\n" +
                       "SELECT\n" +
                       "        logic_id, 100001, is_period, priority + 1 , on_pc, on_mobile, on_panel, manual_command_num, last_update, depend_device, parent_property_id\n" +
                       "FROM\n" +
                       "        control_logic_property_lnk\n" +
                       "WHERE properties_id = 342;\n" +
                       "INSERT INTO control_logic_property_lnk\n" +
                       "SELECT\n" +
                       "        logic_id, 100002, is_period, priority + 1 , on_pc, on_mobile, on_panel, manual_command_num, last_update, depend_device, parent_property_id\n" +
                       "FROM\n" +
                       "        control_logic_property_lnk\n" +
                       "WHERE properties_id = 342;";
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
        anyQueryTestList(QUERY_TYPE.select.name(),
//"SELECT * FROM event WHERE green_house_id = '630005000000001'"
//"SELECT * From control_setting_device WHERE gsm_key = 630015 ORDER BY logic_id, device_num, device_insert_order",
//"SELECT * From control_setting_device WHERE gsm_key = 630003 ORDER BY logic_id, device_num, device_insert_order"
//"SELECT * From controller WHERE gsm_key = 630015",
//"SELECT * From control_setting WHERE gsm_key = 630015",
//"SELECT * From device WHERE gsm_key = 630015",
//"SELECT * From green_house WHERE gsm_key = 630015"
            //"SELECT * FROM weather_cast ORDER BY id DESC LIMIT 100"
        //        "SELECT * FROM user_info where phone LIKE '%819'",
           //     "SELECT * FROM phone_setting where phone_number LIKE '%819'"
                //"SELECT * FROM user_info WHERE user_id IN ( 'smheo94', 'dhback', 'syhwang3')"
//                "SELECT * FROM SDK_SMS_SEND   ORDER BY MSG_ID DESC LIMIT 10\n",
//                        "SELECT * FROM SDK_SMS_REPORT_DETAIL ORDER BY MSG_ID DESC LIMIT 10" );
                //"SELECT * FROM user_loging ORDER BY login_time DESC LIMIT 100" );
                //"SELECT * FROM control_properties"
                "\t\tSELECT \n" +
                        "\t\t* FROM \n" +
                        "\t\tuser\n" +
                        "\t\tORDER BY id DESC LIMIT 100\n" +
                        "\t\t"
        //"SELECT * FROM alarm_setting where phone_number = '01034496214'"
        );
        assertTrue(true);
    }
    @Test
    public void updateAny() {
        anyQueryTestList(QUERY_TYPE.update.name(),
        "INSERT INTO `user_info` (`idx`, `user_id`, `user_name`, `pwd`, `level`, `email`, `phone`, `create_date`, `update_date`, `pwd_update_date`, `zip`, `addr_1`, `addr_2`, `keep_session`, `encrypt_pwd`, `pin_code`, `pin_update_date`, `is_deleted`, `old_pwd1`, `old_pwd2`, `old_pwd_update1`, `old_pwd_update2`, `rsa_key`) \n" +
        "VALUES (NULL, 'jeju-sfarm-api', 'Test', 'b6d9bcbf2306ca9a03d8bba7938e5ed6d8a20a4388a2b070c24d8239a023956816f48bb72e2142a3d6422ee02c22caec7e4e543005c994a08ceb5d198155fb1e', '0', NULL, '01072371525', NULL, '2020-09-17 20:52:49', '2019-02-28 16:30:52', NULL, NULL, NULL, '0', NULL, '5c0ef02ffec77b651e9c2cd1ee15613af401cc69e35daa3bb5f280668844b256a034be7b55674b1db3ff3162f1f24db249c95a0ea4e455549cb63e1e1626e557', '2019-02-28 20:42:02', NULL, NULL, NULL, NULL, NULL, NULL);"

                //"DELETE FROM  control_setting WHERE gsm_key = 630015"
                //"UPDATE `control_logic_device` SET `device_param_name` = '컴프레셔밸브/펌프' , `able_array` = '1' WHERE `id` = '67003'; "
   );
        assertTrue(true);
    }

    @Test
    public void getPhoneInfo() {
        String number = "965";
        anyQueryTestList(QUERY_TYPE.select.name(),
                //"SELECT * FROM weather_cast ORDER BY id DESC LIMIT 100"
                "SELECT * FROM user_info where phone LIKE '%" + number + "'",
                "SELECT * FROM phone_setting where phone_number LIKE '%" + number + "'"
        );
    }


    @Test
    public void selectUser() {
        String userName = "김상" ;
        String phone_num = "9961" ;
        anyQueryTest(QUERY_TYPE.select.name(), "SELECT * FROM user_info WHERE user_name LIKE '%" + userName + "%'");
        anyQueryTest(QUERY_TYPE.select.name(), "SELECT PS.* FROM user_info UI INNER JOIN phone_setting PS ON PS.user_idx = UI.idx WHERE user_name LIKE '%" + userName + "%'");
        //anyQueryTest(QUERY_TYPE.select.name(), "SELECT * FROM user_info WHERE user_id = 'parkos'");
        assertTrue(true);
    }

    @Test
    public void updatePhoneInfo() {
        String userIdx = "99615994";
        String phoneNumber = "010-5485-6929";
//        anyQueryTestList(QUERY_TYPE.update.name(),
//                String.format("UPDATE user_info set phone ='%s' WHERE idx = '%s'", phoneNumber, userIdx),
//                String.format("UPDATE phone_setting set phone_number ='%s' WHERE user_idx = '%s' ", phoneNumber, userIdx)
//        );

        anyQueryTestList(QUERY_TYPE.update.name(),
                String.format("UPDATE user_info set phone ='%s' WHERE idx = '%s'", phoneNumber, userIdx),
                String.format("INSERT INTO phone_setting (user_idx, imei, token, phone_number)  VALUES        (%s, '', '', '%s' )", userIdx,  phoneNumber)
        );
    }
    @Test
    public void updatePhoneInfo2() {
        Integer [] userIdx = {
        };
        String [] phoneNumber = {
        };
        for( int i = 0; i < userIdx.length ; i++) {
            anyQueryTestList(QUERY_TYPE.update.name(),
                    String.format("UPDATE user_info set phone ='%s' WHERE idx = '%s'", phoneNumber[i], userIdx[i]),
                    String.format("UPDATE phone_setting set phone_number ='%s' WHERE user_idx = '%s' ", phoneNumber[i], userIdx[i])
            );
        }
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
        Integer gsm_key = 648007;
        Integer port = 30005;
        String baseDomain ="iptime.org";
        String qText = String.format("UPDATE `gsm_info` SET `system_host` = 'sf-%1$06d.%3$s', system_port = %2$s, master_system_port =%2$s," +
                "master_system_host = 'sf-%1$06d.%3$s'    WHERE `gsm_key` = '%1$s'; ",
                gsm_key, port, baseDomain);
        log.info(qText);
        anyQueryTest(QUERY_TYPE.update.name(), qText);
        assertTrue(true);
    }

    @Test
    public void selectGSM() {
        String gsmKey = "630003" ;

        //anyQueryTest(QUERY_TYPE.select.name(), "SELECT * FROM user_info WHERE user_name LIKE '%" + userName + "%'");

        anyQueryTest(QUERY_TYPE.select.name(), "SELECT * FROM gsm_info where gsm_key = '%" + gsmKey + "%'");

        assertTrue(true);
    }
    @Test
    public void updateUserPassword() {
        String userId = "muan103" ;
        String newP = "tmdska12#";
        SHA512PasswordEncoder encoder = new SHA512PasswordEncoder();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MILLISECOND, 0);
        String salt = String.format("%1$s%1$s", c.getTimeInMillis());
        String encodedPwd = encoder.encode(newP+salt);
        Date pwdUpdateDate = c.getTime();
        String dateStr = DateUtil.getDateTimeStr(pwdUpdateDate);
        anyQueryTest(QUERY_TYPE.update.name(), String.format("UPDATE user_info  SET update_date = NOW() , pwd = '%s', pwd_update_date = '%s'" +
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
        anyQueryTest(QUERY_TYPE.update.name(), "DELETE  FROM phone_setting WHERE phone_number='010-3086-8580'");
        assertTrue(true);
    }
    @Test
    public void getPhoneSetting() {
        anyQueryTest(QUERY_TYPE.select.name(), "SELECT * FROM phone_setting " +
                " WHERE phone_number='010-3086-8580'");
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
            //log.info( "Result :  {}", hashMapResponseEntity.getBody());
            String json = MapUtils.toJson(hashMapResponseEntity.getBody());
            log.info("Json :  {}", json);

            List<Map<String,Object>> dataList = (List<Map<String,Object>>)hashMapResponseEntity.getBody().get("data");
            if( dataList != null ) {
                printData2(dataList);
            }
        }
    }

    private void printData2(List<Map<String, Object>> dataList) {
        if( dataList == null || dataList.size() == 0) {
            log.info("DATA : \n {}", "NULL");
            return;
        }
        List<String> keyList = new ArrayList<>();

        for( int i = 0; i < dataList.size(); i++){
            Map<String,Object> headerLine = dataList.get(i);
            if(headerLine.keySet().size() > keyList.size() ) {
                keyList.clear();
                keyList.addAll(headerLine.keySet());
            }
        }
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < keyList.size() ; i++) {
            sb.append(keyList.get(i));
            if( i < keyList.size() -1) {
                sb.append(",");
            }
        }
        sb.append("\n");
        dataList.stream().forEachOrdered(d -> {
            sb.append(",(");
            for( int i = 0; i < keyList.size() ; i++) {
                Object o = d.get(keyList.get(i));
                if( o == null ) {
                    sb.append( "NULL" );
                } else if( o instanceof  String) {
                    sb.append( "'"  + o +"'");
                } else {
                    sb.append(o);
                }
                if( i < keyList.size() -1) {
                    sb.append(",");
                }
            }
            sb.append(")\n");
        }
        );

//
//
//        headerLine.keySet().stream().forEachOrdered(k -> sb.append(k).append(",") );
//        dataList.stream().forEachOrdered(d -> {
//            sb.append("\n");
//            d.values().stream().forEachOrdered(v -> sb.append("'").append( String.valueOf(v).replaceAll("\n","") ).append("',"));
//        });
        sb.append("\n");
        log.info("DATA : \n {}", sb.toString());
    }


    private void printData(List<Map<String, Object>> dataList) {
        if( dataList == null || dataList.size() == 0) {
            log.info("DATA : \n {}", "NULL");
            return;
        }
        Map<String,Object> headerLine = dataList.get(0);
        StringBuilder sb = new StringBuilder();
        headerLine.keySet().stream().forEachOrdered(k -> sb.append(k).append(",") );
        dataList.stream().forEachOrdered(d -> {
            sb.append("\n");
            d.values().stream().forEachOrdered(v -> sb.append("'").append( String.valueOf(v).replaceAll("\n","") ).append("',"));
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