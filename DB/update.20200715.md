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
```