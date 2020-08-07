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

# 액비펌프도 여러개가 들어갈 수 있도록 수정
```
UPDATE `control_logic_device` SET `able_array` = '1' WHERE `id` = '410060'; 
UPDATE `control_logic_device` SET `able_array` = '1' WHERE `id` = '730049'; 
UPDATE `control_logic_device` SET `able_array` = '1' WHERE `id` = '410050';
``` 

# 감귤 액비제어 추가
INSERT INTO `control_setting_liquid` (`id`, `publish_level`, `owner_user_idx`, `title`, `prd_code`, `liquid_id`, `packing_size`, `mix_type`, `mix_rate`, `watering_amt`, `comment`, `create_dt`, `update_dt`) VALUES ('614001', 'OPEN', NULL, '감귤-비료', '0614', 'LIQ', '20', 'PER', '100', '600', '기본 희석비', '2020-03-18 14:21:55', '2020-07-14 15:19:41');
  
  
# 액비 제어기의 최대 유량 확인

```
UPDATE control_properties SET properties_json = '{"type":"number","min":0,"max":600,"unit":"L"}' WHERE id = '410015';
```

# 사용자 스마트폰 정보 업데이트
```
INSERT INTO phone_setting (
         user_idx, imei, token, phone_number, privacy_policy_agree, privacy_policy_agree_date
)
SELECT UI.idx, UL.imei, NULL AS token, INSERT(INSERT(REPLACE(UI.phone, '-', ''), 4, 0, '-'), 9,0, '-')  AS phone, 
NULL AS privacy_policy_agree, NULL AS privacy_policy_agree_date
FROM user_info UI 
INNER JOIN 
(
    SELECT ULL.* FROM user_login_log ULL
    INNER JOIN (     
     SELECT  MAX(login_time) login_time, user_id FROM user_login_log WHERE  imei IS NOT NULL AND LENGTH(imei) > 0 AND login_success = 1 
     GROUP BY user_id 
     ) ULL2 ON ULL.user_id = ULL2.user_id AND ULL.login_time = ULL2.login_time
) UL ON UI.user_id = UL.user_id
LEFT JOIN phone_setting PS ON UI.idx = PS.user_idx 
WHERE PS.user_idx IS NULL;
```


ALTER TABLE `weather_cast_v2` ADD COLUMN `uuu` VARCHAR(20) NULL COMMENT '풍속(동서)' AFTER `vvv`; 

#번호 확인 필요 목록 
```
kimji,,김종인,353524095448827,99615978,
nohys,010-36**-*457,노연*,353524091642886,99616033,
kangkk,010-46**-*395,강경*,354908081323343,99616031,
parkis,010-36**-*269,박일*,356794100333085,99616036,
kimbm,010-6602,김병만,358306094116620,99615975,
baekhj,,백형제,d0b0d1cdb48c66e0,99615990,
andong051,010-41**-*893,김기*,5f247eefcc0b0686,99615865,
andong060,010-63**-*432,홍금*,353551090780532,99615876,
leejus,,이주식,4801b2bc04235ef5,99616021,
leesh,,이상호,e9222d48f2f668d6,99615997,
kimjm,,김주만,354317083513176,99615994,

```   