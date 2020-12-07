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


```sql
ALTER TABLE `cd_device_type` ADD COLUMN `graph_color` VARCHAR(40) NULL COMMENT '그래프컬러' AFTER `control_unit`;
UPDATE `cd_device_type` SET `graph_color` = '#b2df8a' WHERE `id` = '12401'; 
UPDATE `cd_device_type` SET `graph_color` = '#b2df8a' WHERE `id` = '12402'; 
UPDATE `cd_device_type` SET `graph_color` = '#b2df8a' WHERE `id` = '12403'; 
UPDATE `cd_device_type` SET `graph_color` = '#b2df8a' WHERE `id` = '12404'; 
UPDATE `cd_device_type` SET `graph_color` = '#b2df8a' WHERE `id` = '20501'; 
UPDATE `cd_device_type` SET `graph_color` = '#e41a1c' WHERE `id` = '902'; 
UPDATE `cd_device_type` SET `graph_color` = '#e41a1c' WHERE `id` = '10001'; 
UPDATE `cd_device_type` SET `graph_color` = '#e41a1c' WHERE `id` = '20201'; 
UPDATE `cd_device_type` SET `graph_color` = '#984ea3' WHERE `id` = '905'; 
UPDATE `cd_device_type` SET `graph_color` = '#984ea3' WHERE `id` = '10101'; 
UPDATE `cd_device_type` SET `graph_color` = '#984ea3' WHERE `id` = '20301'; 
UPDATE `cd_device_type` SET `graph_color` = '#1f78b4' WHERE `id` = '20601'; 
UPDATE `cd_device_type` SET `graph_color` = '#1f78b4' WHERE `id` = '10401'; 
UPDATE `cd_device_type` SET `graph_color` = '#fc8d62' WHERE `id` = '1021'; 
UPDATE `cd_device_type` SET `graph_color` = '#fc8d62' WHERE `id` = '10501'; 
UPDATE `cd_device_type` SET `graph_color` = '#fc8d62' WHERE `id` = '10801'; 
UPDATE `cd_device_type` SET `graph_color` = '#fc8d62' WHERE `id` = '10802'; 
UPDATE `cd_device_type` SET `graph_color` = '#fc8d62' WHERE `id` = '12601'; 
UPDATE `cd_device_type` SET `graph_color` = '#fc8d62' WHERE `id` = '12602'; 
UPDATE `cd_device_type` SET `graph_color` = '#fc8d62' WHERE `id` = '12603'; 
UPDATE `cd_device_type` SET `graph_color` = '#fc8d62' WHERE `id` = '12604'; 
UPDATE `cd_device_type` SET `graph_color` = '#fc8d62' WHERE `id` = '22201'; 
UPDATE `cd_device_type` SET `graph_color` = '#377eb8' WHERE `id` = '906'; 
UPDATE `cd_device_type` SET `graph_color` = '#a6cee3' WHERE `id` = '21201'; 
UPDATE `cd_device_type` SET `graph_color` = '#66c2a5' WHERE `id` = '21202'; 
UPDATE `cd_device_type` SET `graph_color` = '#4daf4a' WHERE `id` = '15'; 
UPDATE `cd_device_type` SET `graph_color` = '#4daf4a' WHERE `id` = '903'; 
UPDATE `cd_device_type` SET `graph_color` = '#4daf4a' WHERE `id` = '11701'; 
UPDATE `cd_device_type` SET `graph_color` = '#4daf4a' WHERE `id` = '21301'; 
UPDATE `cd_device_type` SET `graph_color` = '#70794c' WHERE `id` = '12801'; 
UPDATE `cd_device_type` SET `graph_color` = '#89674a' WHERE `id` = '12701';  
```

```sql
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501510', 'flower_num', '{\"type\": \"number\", \"unit\": \"개\", \"title\": \"개화수\", \"crops_data\": 1}', '개화수', '1', '개화 수를 입력해 주세요', '5', '개화수 수는 ${flower_num} 개 입니까?', '3', '0', NULL, 'number', NULL); 
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501511', 'fruit_num', '{\"type\": \"number\", \"unit\": \"개\", \"title\": \"착과수\", \"crops_data\": 1}', '착과수', '1', '착과  수를 입력해주세요', '5', '착과 수는  ${fruit_num} 개 입니까?', '3', '0', NULL, 'number', NULL); 
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501512', 'expect_harvest', '{\"type\": \"number\",\"unit\" :\"㎏\", \"title\": \"예상수량\" }', '예상수량', '1', '예상수량 킬로그램을 입력해주세요', '5', '수확량은 ${expect_harvest} 킬로그램 입니까?', '3', '0', NULL, 'number', NULL); 
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501514', 'pest_type', '{\"type\":\"combo_box\", \"title\":\"해충 구분\", \"data\":[\"순나방\",\"심식나방\",\"애모무늬잎말이나방\", \"기타\"]}', '해충 구분', '0', NULL, '5', NULL, '3', '0', NULL, 'text', NULL); 
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501515', 'pest_level', '{\"type\":\"label\", \"unit\":\"%\", \"title\":\"피해정도\", \"data_content\":\"\"}', '피해량', '1', '피해량을 퍼센트로 입력해주세요', '5', '피해량은 ${pest_level} % 입니까?', '3', '0', NULL, 'number', NULL);
INSERT INTO`cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501610', 'plant_length', '{\"type\": \"number\", \"unit\": \"㎝\", \"title\": \"초장 길이\", \"crops_data\": 1}', '초장 길이', '1', '초장 길이는 몇 센치미터 입니끼?', '5', '${plant_length} 센치미터 입니까?', '3', '0', '지면에서부터 주중 최장엽 끝까지의 길이(생엽장)', 'number', NULL); 
INSERT INTO`cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501100', 'pest_type', '{\"type\":\"combo_box\", \"title\":\"해충 구분\", \"data\":[\"노균병\",\"잎마름병\",\"흑색썩음균행병\",\"고자리파리\", \"기타\"]}', '해충 구분', '0', NULL, '5', NULL, '3', '0', NULL, 'text', NULL); 
INSERT INTO`cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501107', 'pest_type', '{\"type\":\"combo_box\", \"title\":\"해충 구분\", \"data\":[\"더뎅이병\", \"궤양병\", \"역병\", \"볼록총채벌레\", \"화살깍지벌레\", \"기타\"]}', '해충 구분', '0', NULL, '5', NULL, '3', '0', NULL, 'text', NULL); 
INSERT INTO`cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501109', 'pest_type', '{\"type\":\"combo_box\", \"title\":\"해충 구분\", \"data\":[\"탄저병\", \"바이러스병\", \"점무늬병\", \"세균병\", \"톱다리개미허리노린재\", \"기타\"]}', '해충 구분', '0', NULL, '5', NULL, '3', '0', NULL, 'text', NULL); 
INSERT INTO`cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501104', 'pest_type', '{\"type\":\"combo_box\", \"title\":\"해충 구분\", \"data\":[\"무름병\", \"뿌리혹병\", \"바이러스병\", \"검은무늬병\", \"노균병\", \"기타\"]}', '해충 구분', '0', NULL, '5', NULL, '3', '0', NULL, 'text', NULL); 
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501912', 'object_num', '{\"type\": \"number\", \"unit\": \"개\", \"title\": \"개체수\", \"crops_data\": 1}', '개체수', '1', '미터제곱당 개체수를 입력해주세요', '5', '개체수는 ${object_num} 개 입니까?', '3', '0', '미터제곱당 개체수를 입력', 'number', NULL); 
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501913', 'branch_num', '{\"type\": \"number\", \"unit\": \"개\", \"title\": \"가지수\", \"crops_data\": 1}', '가지수', '1', '개체당 가지수를 입력해주세요', '5', '가지수는 ${branch_num} 개 입니까?', '3', '0', '개체당 가지수를 입력', 'number', NULL); 
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501914', 'pod_num', '{\"type\": \"number\", \"unit\": \"개\", \"title\": \"꼬투리수\", \"crops_data\": 1}', '꼬투리수', '1', '개체당 꼬투리수를 입력해주세요', '5', '꼬투리수는 ${pod_num} 개 입니까?', '3', '0', '개체당 꼬투리수를 입력', 'number', NULL); 
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501915', 'bean_num', '{\"type\": \"number\", \"unit\": \"개\", \"title\": \"알수\", \"crops_data\": 1}', '알수', '1', '꼬투리당 알수를 입력해주세요 ', '5', '알수는 ${bean_num} 개 입니까?', '3', '0', '꼬투리당 콩알의 수를 입력', 'number', NULL); 
INSERT INTO `cd_diary_properties` (`id`, `ui_class_name`, `properties_json`, `display_text`, `support_stt`, `input_help_text`, `input_help_waiting`, `valid_text`, `valid_waiting`, `valid_button_type`, `detail_help_text`, `property_data_type`, `property_data_length`) VALUES ('501916', 'cumlNum', '{\"type\": \"number\", \"unit\": \"㎝\", \"title\": \"경장\", \"crops_data\": 1}', '경장', '1', '경장을 입력해주세요', '5', '경장의 길이는 ${bean_num} 센티미터 입니까?', '3', '0', '지면으로부터 이삭목 까지의 길이', 'number', NULL); 

```

 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5015', '1', '5', '1'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5015', '7', '5', '18'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5015', '8', '5', '2'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5015', '9', '5', '3'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`,`data_level`,  `priority`) VALUES ('5015', '501510', '5','10'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5015', '501511', '5', '11'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5015', '501512', '5', '12'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5015', '501514', '5', '13'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5015', '501515', '5', '14'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5018', '1', '5', '1'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5018', '7', '5', '18'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5018', '8', '5', '2'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5018', '9', '5', '3'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`,`data_level`,  `priority`) VALUES ('5018', '501510', '5','10'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5018', '501511', '5', '11'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5018', '501512', '5', '12'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5018', '501514', '5', '13'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5018', '501515', '5', '14'); 

INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5017', '1', '5', '1'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5017', '7', '5', '18'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5017', '8', '5', '2'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5017', '9', '5', '3'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`,`data_level`,  `priority`) VALUES ('5017', '501510', '5','10'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5017', '501511', '5', '11'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5017', '501512', '5', '12'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5017', '501107', '5', '13'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5017', '501515', '5', '14');

INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '1', '5', '1'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '7', '5', '18'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '8', '5', '2'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '9', '5', '3'); 

INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '500020', '5', '10'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '501512', '5', '11'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '501030', '5', '12'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '501020', '5', '13'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '501010', '5', '14'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '501100', '5', '15'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5016', '501515', '5', '16'); 


INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '1', '5', '1'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '7', '5', '18'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '8', '5', '2'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '9', '5', '3'); 

INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '501910', '5', '10'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '500050', '5', '11'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '501912', '5', '12'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '501913', '5', '13'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '501914', '5', '14'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '501915', '5', '15'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '501916', '5', '15'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '501109', '5', '17'); 
INSERT INTO `cd_diary_properties_lnk` (`diary_type_id`, `properties_id`, `data_level`, `priority`) VALUES ('5019', '501515', '5', '18'); 
UPDATE `cd_diary_properties` SET `ui_class_name` = 'cuml_num' WHERE `id` = '501916';

UPDATE cd_diary_properties
SET ui_class_name = REPLACE(ui_class_name, 'fruit_', 'sugar_')
, valid_text = REPLACE(valid_text, 'fruit_', 'sugar_')
WHERE ui_class_name LIKE 'fruit_content%';
