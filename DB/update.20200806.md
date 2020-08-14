# 상위 그룹 프로퍼티에 대한 링크 추가
``` 
ALTER TABLE `control_logic_property_lnk` 
ADD COLUMN `parent_property_id` BIGINT(20) NULL COMMENT '그룹으로 묶일 상위 ID' 
AFTER `depend_device`, COMMENT='제어로직과 설정 프로퍼티 간의 관계'; 
INSERT INTO `control_properties` (`id`, `ui_class_name`, `description`, `ui_help`, `properties_json`, `min_value`, `max_value`, `display_unit`, `default_value`, `default_min_value`, `default_max_value`, `bootstrap_size`, `dep_class_comp_up`, `dep_class_comp_down`, `last_update`) VALUES ('700100', 'temperature_set', '온도설정', NULL, '{\"type\":\"array\"}', NULL, NULL, NULL, NULL, NULL, NULL, '12', NULL, NULL, '2020-01-17 22:16:57'); 
INSERT INTO `control_properties` (`id`, `ui_class_name`, `description`, `ui_help`, `properties_json`, `min_value`, `max_value`, `display_unit`, `default_value`, `default_min_value`, `default_max_value`, `bootstrap_size`, `dep_class_comp_up`, `dep_class_comp_down`, `last_update`) VALUES ('700110', 'temperature', '온도', NULL, '{\"min\":-40,\"max\":100,\"unit\":\"℃\",\"type\":\"number\", \"default\": 20}', NULL, NULL, NULL, NULL, NULL, NULL, '12', NULL, NULL, '2020-01-17 22:16:57'); 
INSERT INTO `control_properties` (`id`, `ui_class_name`, `description`, `ui_help`, `properties_json`, `min_value`, `max_value`, `display_unit`, `default_value`, `default_min_value`, `default_max_value`, `bootstrap_size`, `dep_class_comp_up`, `dep_class_comp_down`, `last_update`) VALUES ('700120', 'position', '목표위치', NULL, '{\"min\":0,\"max\":100,\"unit\":\"%\",\"type\":\"number\", \"default\": 50}' , NULL, NULL, NULL, NULL, NULL, NULL, '12', NULL, NULL, '2020-01-17 22:16:57'); 

INSERT INTO `control_logic_property_lnk` (`logic_id`, `properties_id`, `is_period`, `priority`, `on_mobile`, `on_panel`) VALUES ('70', '700100', '2', '1', '1', '1'); 
INSERT INTO `control_logic_property_lnk` (`logic_id`, `properties_id`, `is_period`, `priority`, `on_mobile`, `on_panel`) VALUES ('70', '700110', '2', '2', '1', '1'); 
INSERT INTO `control_logic_property_lnk` (`logic_id`, `properties_id`, `is_period`, `priority`, `on_mobile`, `on_panel`) VALUES ('70', '700120', '2', '3', '1', '1'); 
DELETE FROM `control_logic_property_lnk` WHERE `logic_id` = '70' AND `properties_id` = '700010' AND `is_period` = '2' AND `manual_command_num` = '0'; 
DELETE FROM `control_logic_property_lnk` WHERE `logic_id` = '70' AND `properties_id` = '700015' AND `is_period` = '2' AND `manual_command_num` = '0'; 
DELETE FROM `control_logic_property_lnk` WHERE `logic_id` = '70' AND `properties_id` = '700020' AND `is_period` = '2' AND `manual_command_num` = '0'; 
DELETE FROM `control_logic_property_lnk` WHERE `logic_id` = '70' AND `properties_id` = '700025' AND `is_period` = '2' AND `manual_command_num` = '0'; 
DELETE FROM `control_logic_property_lnk` WHERE `logic_id` = '70' AND `properties_id` = '700030' AND `is_period` = '2' AND `manual_command_num` = '0'; 
DELETE FROM `control_logic_property_lnk` WHERE `logic_id` = '70' AND `properties_id` = '700035' AND `is_period` = '2' AND `manual_command_num` = '0'; 
DELETE FROM `control_logic_property_lnk` WHERE `logic_id` = '70' AND `properties_id` = '700040' AND `is_period` = '2' AND `manual_command_num` = '0'; 
DELETE FROM `control_logic_property_lnk` WHERE `logic_id` = '70' AND `properties_id` = '700045' AND `is_period` = '2' AND `manual_command_num` = '0'; 
```

# 생육일지 프로퍼티 추가 
```
INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5010', '1209'); 
INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5011', '1201'); 
INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5013', '0659'); 
INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5012', '1201'); 
INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5014', '1001'); 
INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5015', '0601'); 
INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5016', '1202'); 
INSERT INTO `cd_diary_depend_mclass` (`diary_type_id`, `depend_mclasscode`) VALUES ('5017', '0614'); 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5010' AND `depend_mclasscode` = '1209'; 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5011' AND `depend_mclasscode` = '1201'; 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5012' AND `depend_mclasscode` = '1201'; 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5013' AND `depend_mclasscode` = '0659'; 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5014' AND `depend_mclasscode` = '1001'; 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5015' AND `depend_mclasscode` = '0601'; 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5016' AND `depend_mclasscode` = '1202'; 
UPDATE `cd_diary_depend_mclass` SET `depend_type` = 'ACCESS' WHERE `diary_type_id` = '5017' AND `depend_mclasscode` = '0614'; 

```


# 컴프레셔/밸브 별개 입력
``
UPDATE `control_logic_device` SET `device_param_name` = '컴프레셔밸브/펌프' , `able_array` = '1' WHERE `id` = '67003'; 
```

