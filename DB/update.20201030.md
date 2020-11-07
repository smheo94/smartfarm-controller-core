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