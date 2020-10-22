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
			
		SELECT MD5(MD5('제주스파트팜12#')), MD5('제주스파트팜12#')
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