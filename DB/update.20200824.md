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

