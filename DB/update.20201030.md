# 액비기 관련 수정
```sql
UPDATE `control_properties` 
SET `properties_json` = '{\"type\":\"number\",\"min\":0,\"max\":2000,\"unit\":\"L\"}' 
WHERE `id` = '410013';

UPDATE cd_diary_type SET is_base_diary = 1 where id = 4;

INSERT INTO cd_menu_roles (menu_id, roles_id)
VALUES ('11000', '120');
```


# 데이터 시간 업데이트
```sql
ALTER TABLE green_house ADD COLUMN `data_time` TIMESTAMP NULL COMMENT '최종데이터시간' AFTER `trap_device_id`; 
```
 
# 감리 데이터 수정

```sql
ALTER TABLE `user_info_roles` ADD PRIMARY KEY (`user_idx`, `roles_id`);
DELETE FROM `tmp_i18n` WHERE `i18n` = 'Drain liquid Flowgage' AND `i18n_code` = '__ui.environment.plantNutrient.Drain_liquid_Flowgage'; 
 DELETE FROM `tmp_i18n` WHERE `i18n` = '3 way valve(2)' AND `i18n_code` = '__ui.environment.actuator.3WAY_VALVE_2'; 
  ALTER TABLE `tmp_i18n` CHANGE `i18n_code` `i18n_code` VARCHAR(256) CHARSET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '시스템메시지코드', ADD PRIMARY KEY (`i18n_code`);
ALTER TABLE `gsm_category_view` CHANGE `category_id` `category_id` INT(11) NOT NULL COMMENT '제어기분류구분자', CHANGE `child_category_id` `child_category_id` INT(11) NOT NULL COMMENT '제어기하위분류구분자', ADD PRIMARY KEY (`category_id`, `child_category_id`);
ALTER TABLE `cd_liquid` ADD PRIMARY KEY (`liquid_id`);
  
 
```


# 생육관리
```sql
UPDATE cd_diary_properties
SET properties_json = JSON_INSERT( properties_json, '$.crops_data',  1) 
WHERE property_data_type = 'number' AND id NOT IN ( 501010, 501020, 501071,  501072,  501073, 200030, 500100, 500110, 501412, 600020);

UPDATE cd_diary_properties SET display_text = '작물' WHERE  ui_class_name = 'crops'; 
``` 

# 프로퍼티 누락분 작성
```sql
UPDATE `control_properties` SET `description` = '열림시간조건' WHERE `id` = '146'; 
UPDATE `control_properties` SET `description` = '열림시간' WHERE `id` = '147'; 
UPDATE `control_properties` SET `description` = '닫힘시간조건' WHERE `id` = '62'; 
UPDATE `control_properties` SET `description` = '닫힘시간' WHERE `id` = '64'; 


```



```sql
UPDATE `cd_device_type` SET `control_unit` = 'Minute' WHERE `id` = '17'; 
UPDATE `cd_device_type` SET `control_unit` = 'Minute' WHERE `id` = '18'; 
UPDATE `cd_device_type` SET `control_unit` = 'Minute' WHERE `id` = '23'; 
UPDATE `cd_device_type` SET `control_unit` = 'Minute' WHERE `id` = '24'; 
ALTER TABLE `cd_diary_type` 
ADD COLUMN `diary_view_order` DOUBLE DEFAULT 1 NOT NULL AFTER `base_diary_type_id`; 
UPDATE `cd_diary_type` SET `diary_view_order` = '10' WHERE `id` = '8'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '9' WHERE `id` = '7'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '15' WHERE `id` = '6'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '4' WHERE `id` = '3'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '5' WHERE `id` = '4'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '3' WHERE `id` = '2'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5010'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5011'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5012'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5013'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5014'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5016'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5015'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5017'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5018'; 
UPDATE `cd_diary_type` SET `diary_view_order` = '2' WHERE `id` = '5019'; 
```