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