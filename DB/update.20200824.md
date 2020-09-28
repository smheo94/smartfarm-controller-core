# 사과연구소용 제어로직 추가(관비제어로직)
```sql
INSERT INTO control_logic (
        id, NAME, java_class_name, ui_class_name, description, ui_help, can_multi_logic, default_period_size, view_order, last_update
)
SELECT
        141, '과수관비', java_class_name, ui_class_name, '관수관비 수동제어', ui_help, can_multi_logic, default_period_size, view_order, last_update
FROM
        control_logic WHERE id = '41';
        
INSERT INTO control_logic_device (
        id, logic_id, device_num, device_param_code, device_param_name, device_type, is_main, required, able_array, description, display_order, is_used, relative_device_num, last_update
)
SELECT
        id + 100000, 141, device_num, device_param_code, device_param_name, device_type, is_main, required, able_array, description, display_order, is_used, relative_device_num, last_update
FROM
        control_logic_device WHERE logic_id = '41';
        
INSERT INTO control_logic_property_lnk (
        logic_id, properties_id, is_period, priority, on_pc, on_mobile, on_panel, manual_command_num, last_update, depend_device, parent_property_id
)
SELECT
        141, properties_id, is_period, priority, on_pc, on_mobile, on_panel, manual_command_num, last_update, depend_device, parent_property_id
FROM 
        control_logic_property_lnk  WHERE logic_id = '41';
```      
        
# 테이블 정리(sf_control에서는 필요 없음)
```sql
DROP TABLE `monitoring_current`; 
DROP TABLE `device_status`; 
DROP TABLE `gsm_key_idx`; 
DROP TABLE `service_version`; 
DROP TABLE `sunrise_info`; 
DROP TABLE `user_level_info`;
DROP TABLE `env_temperature`; 

ALTER TABLE `user_info_auth_group`   
  CHANGE `user_idx` `user_idx` INT(11) NOT NULL COMMENT '사용자구분자',
  CHANGE `auth_group_id` `auth_group_id` INT(11) NOT NULL COMMENT '사용자권한그룹구분자', 
  DROP INDEX `FK_user_info_auth_group_1`,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`user_idx`, `auth_group_id`)COMMENT "PK_사용자그룹구분자";

ALTER TABLE `monitoring_nighttime`   
  CHANGE `id` `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '밤통계구분자',
  CHANGE `gsm_key` `gsm_key` BIGINT(20) NULL COMMENT '제어기구분자',
  CHANGE `green_house_id` `green_house_id` BIGINT(20) NOT NULL COMMENT '농장구분자', 
  DROP INDEX `fk_monitoring_history_gsm_key`,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`green_house_id`, `event_date`)COMMENT "PK_환경이력밤통계" ,
  DROP INDEX `id`,
  ADD  KEY `id` (`id`) COMMENT "IDX_환경이력밤통계_시퀀스" ,
  DROP INDEX `monitoring_device_history_eventdate_ix`,
  ADD  KEY `monitoring_device_history_eventdate_ix` (`event_date`) COMMENT "IDX_환경이력밤통계_날짜";

ALTER TABLE monitoring_hour   
  CHANGE `id` `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '시간환경통계구분자',
  CHANGE `gsm_key` `gsm_key` BIGINT(20) NULL COMMENT '제어기구분자',
  CHANGE `green_house_id` `green_house_id` BIGINT(20) NOT NULL COMMENT '농장구분자', 
  DROP INDEX `fk_monitoring_history_gsm_key`,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`green_house_id`, `event_date`)COMMENT "PK_환경이력시간통계" ,
  DROP INDEX `id`,
  ADD  KEY `id` (`id`) COMMENT "IDX_환경이력시간통계_시퀀스" ,
  DROP INDEX `monitoring_device_history_eventdate_ix`,
  ADD  KEY `monitoring_device_history_eventdate_ix` (`event_date`) COMMENT "IDX_환경이력시간통계_날짜";

ALTER TABLE `monitoring_daytime`   
  CHANGE `id` `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '낮환경통계구분자',
  CHANGE `gsm_key` `gsm_key` BIGINT(20) NULL COMMENT '제어기구분자',
  CHANGE `green_house_id` `green_house_id` BIGINT(20) NOT NULL COMMENT '농장구분자', 
  DROP INDEX `fk_monitoring_history_gsm_key`,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`green_house_id`, `event_date`)COMMENT "PK_환경이력낮통계" ,
  DROP INDEX `id`,
  ADD  KEY `id` (`id`) COMMENT "IDX_환경이력낮통계_시퀀스" ,
  DROP INDEX `monitoring_device_history_eventdate_ix`,
  ADD  KEY `monitoring_device_history_eventdate_ix` (`event_date`) COMMENT "IDX_환경이력낮통계_날짜";

ALTER TABLE `house_diary_file`   
  DROP INDEX `NewIndex1`,
  ADD  KEY `FK_house_diary_pic` (`house_diary_idx`) COMMENT "FK_영농생육일지첨부파일_이력구분자";

```        


# 관수유량 표시 이슈에 대한 디바이스 타입 정의
```sql 
UPDATE `cd_device_type` SET `value_mod` = 'total_d' WHERE `id` = '21201';
UPDATE `cd_device_type` SET `value_mod` = 'total_d' WHERE `id` = '21202';
UPDATE `cd_device_type` SET `value_mod` = 'op_time_d' WHERE `id` = '17';
UPDATE `cd_device_type` SET `value_mod` = 'op_time_d' WHERE `id` = '18';
UPDATE `cd_device_type` SET `op_time` = '1' WHERE `id` = '18';
UPDATE `cd_device_type` SET `op_time` = '0' WHERE `id` = '12801';
UPDATE `cd_device_type` SET `op_time` = '0' WHERE `id` = '12802';
UPDATE `cd_device_type` SET `value_mod` = 'op_time_d' WHERE `id` = '23';
UPDATE `cd_device_type` SET `value_mod` = 'op_time_d' WHERE `id` = '24';
UPDATE `cd_device_type` SET `op_time` = '0' WHERE `id` = '21201';
UPDATE `cd_device_type` SET `op_time` = '0' WHERE `id` = '21202';
```

# 페로몬트팹 연동 추가
```sql
INSERT INTO oauth_client_details (
        client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity
)
VALUES
        (
                'agro-jc-app',  '234d6b0038e0ff6a0e68082d4ebbdbcf', 'read', 'client_credentials,implicit,refresh_token',  'ROLE_OPEN_APP', 36000, 2592000
        );
```



# 액비제어 마늘 추가
```sql
INSERT INTO `control_setting_liquid` (`id`, `publish_level`, `owner_user_idx`, `title`, `prd_code`, `liquid_id`, `packing_size`, `mix_type`, `mix_rate`, `watering_amt`, `comment`, `create_dt`, `update_dt`) 
VALUES ('638001', 'OPEN', NULL, '마늘-비료', '1209', 'LIQ', '10', 'RATIO', '1000', '600', '기본 희석비', '0000-00-00 00:00:00', '0000-00-00 00:00:00'); 
```

# 위젯 관리 쿼리 
```sql
INSERT INTO widget_info(signature, usage, name, col_props, class_name, height, params) VALUES 
('AlarmOccurredList','MONITOR','장애현황', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x2","height":"320px"}}'),
('AlarmSummaryCount','MONITOR','알람요약정보', '{"xs":"12"}', NULL, NULL, NULL),
('HouseStatusOverview','MONITOR','스마트팜현황', '{"xs":"12"}', NULL, NULL, '{"sizeProps":{"type":"12x3","height":"490px"}}'),
('SummaryInfoOfCategory','MONITOR','카테고리요약', '{"md":"12","lg":"7"}', NULL, NULL, NULL),
('ExternalInfoDayTimeHeader','MONITOR','외부환경시간', '{"xs":"12"}', NULL, NULL, NULL),
('HouseSensorStatusOverview','MONITOR','스마트팜현황(센서)', '{"xs":"12"}', NULL, NULL, '{"sizeProps":{"type":"12x6","height":"1000px"}}'),
('SummaryAlarmCategoryCount','MONITOR','종합현황', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"12x1","height":"150px"}}'),
('AlarmOccurredRepairedVerticalList','MONITOR','알람목록', '{"md":"12","lg":"5"}', NULL, NULL, NULL),

('ActuatorStatus','FARM','구동기상태', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x2","height":"320px"}}'),
('ActuatorStatusSummary','FARM','장치상태 현황', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x1","height":"150px"}}'),
('AlarmSummaryCount','FARM','알람요약정보', '{"xs":"12"}', NULL, NULL, NULL),
('AlarmSummaryStatus','FARM','알람요약정보', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x1","height":"150px"}}'),
('CCTVPlayer','FARM','CCTV', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x2","height":"320px"}}'),
('ExternalEnvInfo','FARM','외부환경정보', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x1","height":"150px"}}'),
('ExternalInfoDayTimeHeader','FARM','외부환경시간', '{"xs":"12"}', NULL, NULL, NULL),
('FarmDiary','FARM','영농일지', '{"md":"6","lg":"4"}', NULL, NULL, NULL),
('InternalEnvInfo','FARM','내부환경정보', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x2","height":"320px"}}'),
('InternalEnvTrendChart','FARM','환경이력', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x2","height":"320px"}}'),
('LeafTemperatureStatus','FARM','옆온센서 상태', '{"md":"12","lg":"4"}', NULL, NULL, NULL),
('MobileDashboardCover','FARM','모바일네비게이터', '{"xs":"12"}', NULL, NULL, NULL),
('MushroomEnvInfo','FARM','내부환경정보', '{"md":"12","lg":"4"}', NULL, NULL, NULL),
('NutrientStatus','FARM','양액기 상태', '{"md":"12","lg":"3"}', NULL, NULL, NULL),
('ReusableSensorStatus','FARM','배지함수율센서상태', '{"sm":"12", "md":"6","lg":"4"}', NULL, NULL, NULL),
('SoilEnvInfo','FARM','토지센서정보', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x2","height":"320px"}}'),
('WaterStatus','FARM','관수현황', '{"xs":"6"}', NULL, NULL, '{"sizeProps":{"type":"6x2","height":"320px"}}'),
('WeatherForecast','FARM','날씨예보', '{"md":"6","lg":"4"}', NULL, NULL, NULL),
('WeatherForecastDay','FARM','일기예보', '{"xs":"3"}', NULL, NULL, '{"sizeProps":{"type":"3x2","height":"320px"}}'),
('WholesalePriceInfo','FARM','도매시장정보', '{"md":"12","lg":"6"}', NULL, NULL, NULL);
``` 


# 제어 추가
```sql
INSERT INTO `cd_device_type_v_device_dep_device` 
(`id`, `device_type_id`, `device_num`, `device_type_param_name`, `able_array`) 
VALUES ('35', '21301', '1', '광량(Watt)\r\n', '0'); 
```


# 생육 추가
```sql
INSERT INTO `cd_diary_type` (`id`, `diary_type`, `type_name`, `base_diary_type_id`) 
VALUES ('5018', 'growth_peach', '생육(복숭아)', '5'); 
INSERT INTO `cd_diary_type` (`id`, `diary_type`, `type_name`, `base_diary_type_id`) 
VALUES ('5019', 'growth_bean', '생육(콩)', '5'); 


INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5018', '0604'); 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5017' AND `depend_mclasscode` = '0614'; 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5018' AND `depend_mclasscode` = '0604'; 
INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5019', '0301'); 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5019' AND `depend_mclasscode` = '0301'; 
```


# 액비 전자 뱊브
```sql
UPDATE `control_properties` 
SET `properties_json` = '{\"unit\":\"\",\"type\":\"valve\",  \"max_section\": 16 }' 
WHERE `id` = '238'; 
```


# 제어로직의 우창닫힘시간 속성
```sql
DELETE FROM control_logic_property_lnk WHERE logic_id = '3' AND properties_id = '72';


INSERT INTO control_logic_property_lnk (logic_id, properties_id, is_period, priority, on_pc, on_mobile, on_panel, manual_command_num  )
VALUES
          ( '60', '340', '0', 88, 1,1,1, 0)
         ,( '60', '68', '0',  91, 1, 1, 1, 0 ) 
        , ( '60', '151',  '0', 90, 1, 1, 1, 0 )
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":100,\"unit\":\"%\",\"type\":\"number\", \"default\" : 10}' WHERE `id` = '145'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":600,\"unit\":\"초\",\"type\":\"number\", \"default\": 60}' WHERE `id` = '3324'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":6000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '151'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '68'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '192'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '193'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '72'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '194'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '155'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '195'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '112'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '113'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '71'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '114'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '115'; 
UPDATE `control_properties` SET `properties_json` = '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\" : 100 }' WHERE `id` = '154'; 


```

# 사진일지 안나오는 부분 수정
```sql
UPDATE cd_diary_properties SET properties_json = '{ "type" : "attach_photos", "title" :"사진 첨부", "item":4 }' WHERE id = '7';
```

