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
    public void updateAny() {
        anyQueryTestList(QUERY_TYPE.update.name(),
                //"DELETE FROM otp_detail where id IN ( 878, 877, 876, 876, 874)"
//                "UPDATE cctv ",
//                        "SET properties = JSON_SET( properties, '$.daily_capture[0].exec_sec_on_day',  ( 9 * 60 +0  + 0) * 60 ",
//                        ", '$.daily_capture[1].exec_sec_on_day',  ( 9 * 60 + 0 + 4) * 60

                        "UPDATE cd_diary_properties\n" +
                        "SET ui_class_name = REPLACE(ui_class_name, 'fruit_', 'sugar_')\n" +
                        ", valid_text = REPLACE(valid_text, 'fruit_', 'sugar_')\n" +
                        "WHERE ui_class_name LIKE 'fruit_content%'"

//                "UPDATE house_diary",
//                        "\t\tSET diary_data = REPLACE( diary_data, 'http://apismartfarm.kt.co.kr',  'https://apismartfarm.kt.co.kr')"
//                "UPDATE cctv \n" +
//                        "SET properties = JSON_SET( properties, '$.daily_capture[0].exec_sec_on_day',  ( HOUR(NOW()) * 60 + MINUTE(NOW())  + 2) * 60 \n" +
//                        ",'$.daily_capture[0].pos_z',  0\n" +
//                        ",'$.daily_capture[0].capture_type',  1" +
//                        ",'$.daily_capture[1].exec_sec_on_day',  ( HOUR(NOW()) * 60 + MINUTE(NOW() ) + 4) * 60\n" +
//                        ",'$.daily_capture[1].pos_z',  1.0)"

//            (\n" +
//                        "        167250,175086,146722,147082,160641,162017,164688,164030,163739,161441,169865,163066,161508,163108,175838,151697,158854\n" +
//                        ",167920,161162,157021,173314,163167,160667,146473,171925,173672,173291,172606,170905,172678,173842,173885,173807\n" +
//                        ",173246,174631,175859,151687,175972,146686,169730,153998,156128,175758,173936        )"


                //"DELETE FROM  control_setting WHERE gsm_key = 630015"
                //"UPDATE `control_logic_device` SET `device_param_name` = '컴프레셔밸브/펌프' , `able_array` = '1' WHERE `id` = '67003'; "
   );
        assertTrue(true);
    }


    @Test
    public void selectAny2() {
        anyQueryTestList(QUERY_TYPE.select.name(),
                //"SELECT * FROM SDK_SMS_REPORT ORDER BY MSG_ID DESC LIMIT 20"
//                "SELECT C.farm_nickname, B.house_name, A.* FROM cctv A INNER JOIN green_house B ON A.house_id = B.id " +
//                        "INNER JOIN gsm_info C ON C.gsm_key= B.gsm_key"

                "SELECT COUNT(id) as id from house_diary WHERE diary_type_id = 8"
//                        "SELECT D.gsm_key, farm_nickname,  COUNT(A.id) data_count\n" +
//                                "FROM cctv  A\n" +
//                                "INNER JOIN cctv_record B  ON A.id = B.cctv_id\n" +
//                                "INNER JOIN green_house C ON  C.id = A.house_id\n" +
//                                "INNER JOIN gsm_info D ON D.gsm_key = C.gsm_key\n" +
//                                "WHERE green_house_type_id = 95\n" +
//                                "GROUP BY D.gsm_key"
                //"select * from cd_diary_type "
               //"SELECT * FROM otp_detail ORDER BY id DESC LIMIT 20"

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
                //"SELECT * FROM SDK_SMS_REPORT WHERE NOW_DATE DESC LIMIT 100"\
                //"SELECT *  FROM cctv where id = 245"
//                "SELECT * FROM crops_diary LIMIT 10 "
//"\t\tSELECT\n" +
//        "\t\t\thd.id,\n" +
//        "\t\t\thd.green_house_id,\n" +
//        "\t\t\thdm.green_house_id AS house_id_list,\n" +
//        "\t\t\thd.title,\n" +
//        "\t\t\thd.content,\n" +
//        "\t\t\thd.work,\n" +
//        "\t\t\thd.etc,\n" +
//        "\t\t\thd.start_date,\n" +
//        "\t\t\thd.diary_type_id,\n" +
//        "\t\t\thd.diary_data,\n" +
//        "\t\t\thd.end_date,\n" +
//        "\t\t\thd.crops,\n" +
//        "\t\t\thd.harvest,\n" +
//        "\t\t\thd.income,\n" +
//        "\t\t\thd.spend,\n" +
//        "\t\t\thd.content_type,\n" +
//        "\t\t\thd.work_time,\n" +
//        "\t\t\thd.cctv_image_url,\n" +
//        "\t\t\thd.weather\n" +
//        "\t\tFROM house_diary AS hd\n" +
//        "\t\tINNER JOIN house_diary_map AS hdm ON hdm.diary_id = hd.id\n" +
//        "\t\tINNER JOIN green_house AS gh ON gh.id = hdm.green_house_id\n" +
//        "\t\tWHERE gh.gsm_key = 1401\t\t\n" +
//        "\t\tAND start_date BETWEEN '2020-11-01 00:00:00' AND '2020-11-30 23:59:59'\n" +
//        "\t\tAND end_date BETWEEN '2020-11-01 00:00:00'AND '2020-11-30 23:59:59'",

//                "\n" +
//                        "SELECT A.id,\n" +
//                        "            A.base_date,\n" +
//                        "            A.base_time,\n" +
//                        "            A.fcst_date,\n" +
//                        "            A.fcst_time,\n" +
//                        "            A.nx,\n" +
//                        "            A.ny,\n" +
//                        "\t        (SELECT fcst_value FROM weather_cast WHERE  fcst_date =CURDATE()  AND category = \"TMN\" AND nx=51 AND ny=64 AND house_id = 6300002000000001 GROUP BY nx, ny) AS TMN,\n" +
//                        "            (SELECT fcst_value FROM weather_cast WHERE  fcst_date=CURDATE()  AND category = \"TMX\" AND nx=51 AND ny=64 AND house_id = 6300002000000001 GROUP BY nx, ny) AS TMX\n" +
//                        "        FROM weather_cast A WHERE fcst_date=CURDATE()\n" +
//                        "        AND nx=51 AND ny=64  AND house_id = 6300002000000001 GROUP BY nx, ny"
//                        "SELECT\n" +
//                                "\t\t\tgsm_info.gsm_key as gsmKey, gsm_info.user_info_id as userInfoId, is_mine as isMine, area_code as areaCode,\n" +
//                                " \t\t\tfarm_nickname as farmNickname, gsm_info.update_date as updateDate, /*owner_user_info_id as ownerUserInfoId,*/ master_system_host as masterSystemHost,\n" +
//                                " \t\t\tmaster_system_port as masterSystemPort, system_host as systemHost, system_port as systemPort,\n" +
//                                " \t\t\tmqtt_broker_host as mqttbrokerHost, mqtt_broker_port as mqttBrokerPort, server_read_time as serverReadTime, product_interval as productInterval,\n" +
//                                " \t\t\tmonitoring_interval as monitoringInterval, history_save_db_interval as historySaveDbInterval,\n" +
//                                " \t\t\tdelay_auto_control_start as delayAutoControlStart, package_version as packageVersion, schema_version as schemaVersion, nutrient_monitoring_interval as nutrientMonitoringInterval,\n" +
//                                " \t\t\tfarm_nickname_i18n as farmNicknameI18n, farm_db_id as farmDbId, category_id as categoryId, properties,\n" +
//                                "\t\t\tUNIX_TIMESTAMP(NOW()) AS readTime\n" +
//                                "\t\tFROM gsm_info\n" +
//                                "where gsm_info.gsm_key IN ( SELECT VGA.gsm_key FROM vw_gsm_user_auth VGA WHERE VGA.user_idx = 99615850 )"

        );
        assertTrue(true);
    }
    @Test
    public void copyData() {
        anyQueryTestList(QUERY_TYPE.select.name(),
                "SELECT * FROM monitoring_nighttime WHERE green_house_id = '1401000000001' AND event_date >=  DATE_SUB( NOW(), INTERVAL 7 DAY)         "
                        + " AND event_date <= NOW()",
                "SELECT * FROM monitoring_daytime WHERE green_house_id = '1401000000001' AND event_date >=  DATE_SUB( NOW(), INTERVAL 7 DAY)         "
                        + " AND event_date <= NOW()",
                "SELECT * FROM house_diary WHERE gsm_key = '1401'",
                "SELECT * FROM house_diary_map WHERE green_house_id = '1401000000001'");

    }
    @Test
    public void getPhoneInfo() {
        String number = "7740";
        anyQueryTestList(QUERY_TYPE.select.name(),
                "SELECT * FROM user_info where phone LIKE '%" + number + "'",
                "SELECT * FROM phone_setting where phone_number LIKE '%" + number + "'"
        );
    }


    @Test
    public void selectUser() {
        String userName = "용연" ;
        anyQueryTest(QUERY_TYPE.select.name(), "SELECT * FROM user_info WHERE user_name LIKE '%" + userName + "%'");
        anyQueryTest(QUERY_TYPE.select.name(), "SELECT PS.* FROM user_info UI INNER JOIN phone_setting PS ON PS.user_idx = UI.idx WHERE user_name LIKE '%" + userName + "%'");
        assertTrue(true);
    }

    @Test
    public void updatePhoneInfo() {
        String userIdx = "99616078";
        String phoneNumber = "01089555892";
        anyQueryTestList(QUERY_TYPE.update.name(),
                String.format("UPDATE user_info set phone ='%s' WHERE idx = '%s'", phoneNumber, userIdx),
                String.format("UPDATE phone_setting set phone_number ='%s' WHERE user_idx = '%s' ", phoneNumber, userIdx)
        );

//        anyQueryTestList(QUERY_TYPE.update.name(),
//                String.format("UPDATE user_info set phone ='%s' WHERE idx = '%s'", phoneNumber, userIdx),
//                String.format("INSERT INTO phone_setting (user_idx, imei, token, phone_number)  VALUES        (%s, '', '', '%s' )", userIdx,  phoneNumber)
//        );
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
    public void deleteDuplicateEventList() {
        anyQueryTest(QUERY_TYPE.update.name(),
                "DELETE FROM `event`  WHERE id IN (" +
                " SELECT MIN(id) FROM `event`" +
                " WHERE event_check=0  AND restore_date IS NULL AND event_date > '2020-09-01'" +
                " AND event_sub_type_id IN ( 201, 202 )" +
                " GROUP BY gsm_key, event_type_id, event_sub_type_id, device_id, event_check" +
                " HAVING COUNT(id) > 1\n" +
                " )");
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
        //String apiUrl = "http://127.0.0.1:48801/system/data/";
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