# 기상대 테이블 변경(추후 사용)

```sql
CREATE TABLE `weather_cast_v2` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `house_id` BIGINT(20) DEFAULT NULL,
  `base_date` DATETIME DEFAULT NULL,
  `base_time` VARCHAR(4) DEFAULT NULL,
  `fcst_date` DATETIME DEFAULT NULL,
  `fcst_time` VARCHAR(4) DEFAULT NULL,
  `nx` VARCHAR(4) DEFAULT NULL,
  `ny` VARCHAR(4) DEFAULT NULL,
  `pop` VARCHAR(20) DEFAULT NULL,
  `pty` VARCHAR(20) DEFAULT NULL,
  `r06` VARCHAR(20) DEFAULT NULL,
  `reh` VARCHAR(20) DEFAULT NULL,
  `s06` VARCHAR(20) DEFAULT NULL,
  `sky` VARCHAR(20) DEFAULT NULL,
  `t3h` VARCHAR(20) DEFAULT NULL,
  `tmn` VARCHAR(20) DEFAULT NULL,
  `vec` VARCHAR(20) DEFAULT NULL,
  `vvv` VARCHAR(20) DEFAULT NULL,
  `wsd` VARCHAR(20) DEFAULT NULL,
  `tmx` VARCHAR(20) DEFAULT NULL,
  `wav` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_house_id_date` (`house_id`,`fcst_date`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8;

ALTER TABLE `ultra_weather_cast` ADD KEY `idx_utra_weather_key` (`base_date`, `base_time`, `nx`, `ny`); 

# 관수 개선 - 펌프 개수 증설
```sql 
INSERT INTO `control_logic_device` (`id`, `logic_id`, `device_num`, `device_param_code`, `device_param_name`, `device_type`, `is_main`, `required`, `able_array`, `description`, `display_order`, `is_used`, `relative_device_num`, `last_update`) 
VALUES (730044, '44', '16', 'pump3', '펌프2', 'AC', '0', '1', '1', NULL, '1', '1', NULL, '2020-06-06 14:33:41'); 
UPDATE `control_logic_device` SET `device_param_name` = '펌프1' WHERE `id` = '44001'; 
UPDATE `control_logic_device` SET `device_param_name` = '펌프3(흡입)' WHERE `id` = '44012'; 
INSERT INTO `control_logic_device` (`id`, `logic_id`, `device_num`, `device_param_code`, `device_param_name`, `device_type`, `is_main`, `required`, `able_array`, `description`, `display_order`, `is_used`, `relative_device_num`, `last_update`) 
VALUES (730049, '41', '14', 'pump3', '펌프2', 'AC', '0', '0', '0', NULL, '8', '1', NULL, '2020-06-06 11:43:06'); 
UPDATE `control_logic_device` SET `device_param_name` = '펌프1' WHERE `id` = '410060'; 
UPDATE `control_logic_device` SET `device_param_name` = '펌프3(흡입)' WHERE `id` = '410050';
``` 