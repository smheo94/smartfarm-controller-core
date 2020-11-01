# 풀무원강화공장
```sql
INSERT INTO `cd_controller_info` (`id`, `model`, `name`, `homepage`, `email`, `phone`, `last_update`, `name_i18n`) VALUES ('51', 'pulmu_bean', '콩나물제어기', NULL, NULL, NULL, '2020-01-17 22:16:24', '__common.controller_info.pulmu_bean');
```


# 앱검증 관련 추가
```sql
ALTER TABLE `app_history` ADD COLUMN `fcm_key` VARCHAR(4096) NULL COMMENT 'GoogleFCMName' AFTER `download_url`; 


INSERT INTO app_history (     id, app_name, app_type, app_version, update_date, download_url, fcm_key)
VALUES    (
                '3', 'com.kt.android.app.smartfarm', 'android_app', '1.1', NOW(), 'https://smartfarm.kt.co.kr/download/com_kt_smartfarm_v4.apk', 'AAAAJPlHnrY:APA91bE31bpYSqAI9diqFw3QAbbDvcPkfIo-Dv8BqPjxDM2pY905q4x4xNTAlrYOMWWBx-2HGBLcYh9oafdflGSlsb0HDrjjhYckewFuMv-cUjH7k-dj0EfiQKX9AlNep3g3LwiXvg70'
        );        
        
ALTER TABLE `phone_setting` 
ADD COLUMN `package_name` VARCHAR(4096) NULL COMMENT '설치된패키지이름' AFTER `privacy_policy_agree_date`; 

```

# VPD 추가
```sql
INSERT INTO `cd_device_type` (`id`, `name`, `description`, `last_update`, `device_type`, `device_type_name`, `device_type_group`, `kind`, `is_physical_device`, `manufacturer`, `model_name`, `model_spec`, `model_version`, `is_master`, `use_house_type`, `default_address1`, `default_address2`, `default_address3`, `dcac`, `min_value`, `max_value`, `description_i18n`, `device_type_name_i18n`, `unit`, `round_number`, `topic_group`, `reset_point`, `accum_m`, `accum_h`, `accum_d`, `value_mod`, `op_time`, `value_type`, `opmode_error`, `value_exchange`, `sensing_timeout`, `control_unit`) 
VALUES (21007, 'VVPD', 'VPD', '2020-01-17 22:16:28', 'VVPD', 'VPD', 'v_vapor_pressure_deficit', 'v_device', '1', 'KT', '1', '1', '1', '0', '19', '-', NULL, NULL, 'DC', '0', '10', '__common.device_type.name.sensor_leaf_temp', '__common.device_type.name.sensor_leaf_temp', 'kPa', '1', NULL, '0', '0', '0', '0', '', '0', '00000000000', '0', '0', '300', 'Ratio'); 
```
```sql
UPDATE control_logic_device SET `able_array` = '1' WHERE `id` = '410080'; 
```

# 열림추가 시간 추가
INSERT INTO `control_properties`
(`id`, `ui_class_name`, `description`, `ui_help`, `properties_json`, `min_value`, `max_value`, `display_unit`, `default_value`, `default_min_value`, `default_max_value`, `bootstrap_size`, `dep_class_comp_up`, `dep_class_comp_down`, `last_update`) 
VALUES ('100001', 'add_open_time', '열림추가시간', NULL, '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\": 100}', NULL, NULL, NULL, NULL, NULL, NULL, '12', NULL, NULL, '2020-09-04 20:02:54'); 
INSERT INTO `control_properties`
(`id`, `ui_class_name`, `description`, `ui_help`, `properties_json`, `min_value`, `max_value`, `display_unit`, `default_value`, `default_min_value`, `default_max_value`, `bootstrap_size`, `dep_class_comp_up`, `dep_class_comp_down`, `last_update`) 
VALUES ('100002', 'add_close_time', '닫힘추가시간', NULL, '{\"min\":0,\"max\":2000,\"unit\":\"초\",\"type\":\"number\", \"default\": 100}', NULL, NULL, NULL, NULL, NULL, NULL, '12', NULL, NULL, '2020-09-04 20:02:54'); 

INSERT INTO control_logic_property_lnk
SELECT
        logic_id, 100001, is_period, priority + 1 , on_pc, on_mobile, on_panel, manual_command_num, last_update, depend_device, parent_property_id
FROM
        control_logic_property_lnk
WHERE properties_id = 342;
INSERT INTO control_logic_property_lnk
SELECT
        logic_id, 100002, is_period, priority + 1 , on_pc, on_mobile, on_panel, manual_command_num, last_update, depend_device, parent_property_id
FROM
        control_logic_property_lnk
WHERE properties_id = 342;


INSERT INTO `control_properties` (`id`, `ui_class_name`, `description`, `ui_help`, `properties_json`, `min_value`, `max_value`, `display_unit`, `default_value`, `default_min_value`, `default_max_value`, `bootstrap_size`, `dep_class_comp_up`, `dep_class_comp_down`, `last_update`)
 VALUES (480001, 'condition', '작동조건', NULL, '{\"unit\":\"\",\"default\":\"3\",\"type\":\"select\",\"options\":[{\"value\":3,\"label\":\"사용\"} ,{\"value\":6,\"label\":\"사용안함\"}]}', NULL, NULL, NULL, NULL, NULL, NULL, '12', NULL, NULL, '2020-09-04 20:02:54'); 

 INSERT INTO `control_logic_property_lnk` (`logic_id`, `properties_id`, `is_period`, `priority`, `on_pc`, `on_mobile`, `on_panel`, `manual_command_num`, `last_update`, `depend_device`, `parent_property_id`) 
 VALUES ('48', '480001', '1', '0', '1', '1', '0', '0', '2020-09-04 20:39:22', NULL, NULL); 

UPDATE `control_logic_property_lnk` SET `is_period` = '1' WHERE `logic_id` = '11' AND `properties_id` = '205' AND `is_period` = '0' AND `manual_command_num` = '0'; 
UPDATE `control_logic_property_lnk` SET `is_period` = '1' WHERE `logic_id` = '11' AND `properties_id` = '162' AND `is_period` = '0' AND `manual_command_num` = '0'; 


# 토큰 추가
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) 
VALUES ('kt-gsm-fp-web', NULL, 'a6172bac69d07397e096891a6c884426', 'read,write', 'authorization_code,password,client_credentials,implicit,refresh_token', NULL, 'ROLE_ARGOS', '36000', '2592000', NULL, NULL);  
 
 
# 제주 스마트팜 토큰 추가
	INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) 
	VALUES ('jeju-sfram-api', NULL, 'dcb33de0d5986dde786f5abaf1ee3703', 'read,write', 'client_credentials,implicit,refresh_token', NULL, 'ROLE_JEJU', '36000', '2592000', NULL, NULL); 
			
# SELECT MD5(MD5('제주스파트팜12#')), MD5('제주스파트팜12#')
# 제주 스마트팜 당지도 연동 항목 추가
		INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`,
		 `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) 
		 VALUES ('800009', 'sign_number', '{\"type\":\"number\", \"title\":\"나무번호\"}', '나무번호', '0', NULL, '5', NULL, '3', '0', NULL, 'text', NULL); 
		 INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `is_array`, `data_level`, `priority`) VALUES ('8', '800009', '0', '0', '3.1');
		 
# 영농일지 사진일지 테스트

 INSERT INTO `sf_main`.`cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) 
VALUES ('10', 'imageUrl', '{\"type\": \"image_list\", \"title\":\"농장사진\"}', '농장사진', '0', NULL, '5', NULL, '3', '0', NULL, 'text', NULL); 
INSERT INTO `sf_main`.`cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`)
 VALUES ('11', 'comment', '{\"type\":\"textbox\", \"lines\":5, \"placeholder\":\"입력해 주세요.\", \"title\": \"설명\"}', '설명', '0', NULL, '5', NULL, '3', '0', NULL, 'text', '4000'); 
 
  INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `is_array`, `data_level`, `priority`) VALUES ('4', '10', '0', '0', '1'); 
  INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `is_array`, `data_level`, `priority`) VALUES ('4', '11', '0', '0', '1'); 
  
  INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `is_array`, `data_level`, `priority`) VALUES ('3', '7', '0', '0', '1'); 
  INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `is_array`, `data_level`, `priority`) VALUES ('3', '11', '0', '0', '1');
  
  
# 트랩 하우스 맵핑 테이블

CREATE TABLE `user_info_auth_trap_house` (
  `user_idx` INT(11) NOT NULL COMMENT '사용자구분자',
  `house_id` BIGINT NOT NULL COMMENT '스마트팜ID',
  PRIMARY KEY (`user_idx`,`house_id`),
  CONSTRAINT `FK_user_info_auth_trap_house_user` FOREIGN KEY (`user_idx`) REFERENCES `user_info` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_user_info_auth_trap_house_house` FOREIGN KEY (`house_id`) REFERENCES `green_house` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='사용자별 권한 그룹 설정';

CREATE TABLE `user_info_auth_trap_gsm` (
  `user_idx` INT(11) NOT NULL COMMENT '사용자구분자',
  `gsm_key` BIGINT(20) NOT NULL COMMENT '제어기구분키',
  PRIMARY KEY (`user_idx`,`gsm_key`),  
  CONSTRAINT `FK_user_info_auth_trap_gsm_gsm` FOREIGN KEY (`gsm_key`) REFERENCES `gsm_info` (`gsm_key`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_user_info_auth_trap_gsm_user` FOREIGN KEY (`user_idx`) REFERENCES `user_info` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='사용자별 권한 그룹 설정';

# 하우스에 센서 순서를 지정하도록 함
ALTER TABLE `map_green_house_device` 
ADD COLUMN `mapping_num` INT DEFAULT 1 NOT NULL COMMENT '스마트팜에구분순서' AFTER `update_dt`; 

    
#   SELECT MD5('팜에이트8'), MD5(MD5('팜에이트8'))
# Farm8 연동
 INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) 
 VALUES ('farm8-api', NULL, '1120aad91a7051f39e900df66cff3823', 'read', 'client_credentials,implicit,refresh_token', NULL, 'ROLE_OPEN_APP', '36000', '2592000', NULL, NULL); 
  INSERT INTO `user_info` (`idx`, `user_id`, `user_name`, `pwd`, `level`, `email`, `phone`, `create_date`, `update_date`, `pwd_update_date`, `zip`, `addr_1`, `addr_2`, `keep_session`, `encrypt_pwd`, `pin_code`, `pin_update_date`, `is_deleted`, `old_pwd1`, `old_pwd2`, `old_pwd_update1`, `old_pwd_update2`, `rsa_key`) VALUES (NULL, 'farm8-api', 'Test', 'b6d9bcbf2306ca9a03d8bba7938e5ed6d8a20a4388a2b070c24d8239a023956816f48bb72e2142a3d6422ee02c22caec7e4e543005c994a08ceb5d198155fb1e', '0', NULL, '01072371525', NULL, '2020-10-22 16:01:43', '2019-02-28 16:30:52', NULL, NULL, NULL, '0', NULL, '5c0ef02ffec77b651e9c2cd1ee15613af401cc69e35daa3bb5f280668844b256a034be7b55674b1db3ff3162f1f24db249c95a0ea4e455549cb63e1e1626e557', '2019-02-28 20:42:02', NULL, NULL, NULL, NULL, NULL, NULL); 

