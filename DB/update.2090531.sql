ALTER TABLE `event` 
DROP INDEX `ifk_rel_77`, DROP INDEX `ifk_rel_78`
, ADD KEY `idx_gsm_green_house_deviceid` (`gsm_key`, `green_house_id`, `device_id`), 
DROP FOREIGN KEY `event_device_id_fkey`, 
DROP FOREIGN KEY `event_green_house_id_fkey`;


/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

USE `sf_main`;

/* Foreign Keys must be dropped in the target to ensure that requires changes can be done*/

ALTER TABLE `gsm_config`
    DROP FOREIGN KEY `ifx_fam_config_gsm_key`  ;


/* Create table in target */
CREATE TABLE `cd_info`(
                          `cd_id` VARCHAR(100) COLLATE utf8_general_ci NOT NULL  ,
                          `cd_nm` VARCHAR(100) COLLATE utf8_general_ci NOT NULL  ,
                          `cd_val` VARCHAR(4000) COLLATE utf8_general_ci NOT NULL  ,
                          `cd_grp_id` VARCHAR(10) COLLATE utf8_general_ci NOT NULL  ,
                          `upr_cd_grp_id` VARCHAR(10) COLLATE utf8_general_ci NULL  ,
                          `upr_cd_id` VARCHAR(10) COLLATE utf8_general_ci NULL  ,
                          `cd_dscr` VARCHAR(1000) COLLATE utf8_general_ci NULL  ,
                          `use_yn` VARCHAR(1) COLLATE utf8_general_ci NOT NULL  ,
                          `scrn_ordr` DECIMAL(3,0) NULL  ,
                          `reg_date` TIMESTAMP NOT NULL  DEFAULT 'current_timestamp()' ,
                          `chng_date` TIMESTAMP NOT NULL  DEFAULT '0000-00-00 00:00:00' ,
                          PRIMARY KEY (`cd_grp_id`,`cd_id`)
) ENGINE=INNODB DEFAULT CHARSET='utf8' COLLATE='utf8_general_ci';


/* Alter table in target */
ALTER TABLE `control_setting_device`
    DROP FOREIGN KEY `fx_control_setting_logic_device_id`  ;

/* Alter table in target */
ALTER TABLE `event`
    DROP KEY `idx` ,
    ADD KEY `idx_gsm_green_house_deviceid`(`gsm_key`,`green_house_id`,`device_id`) ;

/* Alter table in target */
ALTER TABLE `gsm_config`
    CHANGE `storm_limit` `storm_limit` DOUBLE   NOT NULL DEFAULT 20 AFTER `update_date` ,
    CHANGE `storm_sensing_delay` `storm_sensing_delay` DOUBLE   NOT NULL DEFAULT 20 AFTER `storm_limit` ,
    CHANGE `storm_terminate_delay` `storm_terminate_delay` DOUBLE   NOT NULL DEFAULT 20 AFTER `storm_sensing_delay` ,
    CHANGE `wind_direction_waiting_time` `wind_direction_waiting_time` DOUBLE   NOT NULL DEFAULT 20 COMMENT '풍향 대기 시간' AFTER `storm_terminate_delay` ,
    CHANGE `wind_direction_degree` `wind_direction_degree` DOUBLE   NOT NULL DEFAULT 0 COMMENT '풍향 각도' AFTER `wind_direction_waiting_time` ,
    CHANGE `rain_sensing_delay` `rain_sensing_delay` DOUBLE   NOT NULL DEFAULT 20 COMMENT '감우 감지 딜레이' AFTER `wind_direction_degree` ,
    CHANGE `rain_terminate_delay` `rain_terminate_delay` DOUBLE   NOT NULL DEFAULT 20 COMMENT '감우 종료 딜레이' AFTER `rain_sensing_delay` ,
    CHANGE `top_window_left_degree` `top_window_left_degree` DOUBLE   NOT NULL DEFAULT 0 COMMENT '좌천창 각도' AFTER `rain_terminate_delay` ,
    CHANGE `top_window_right_degree` `top_window_right_degree` DOUBLE   NOT NULL DEFAULT 0 COMMENT '우천창 각도' AFTER `top_window_left_degree` ;

/* Alter table in target */
ALTER TABLE `push_history`
    ADD COLUMN `sms_push` INT(11)   NULL COMMENT '1 = sms/ 2 = push / 3 = sms,push ' AFTER `id` ,
    CHANGE `push_date` `push_date` DATETIME   NULL DEFAULT 'current_timestamp()' COMMENT 'push  날짜' AFTER `sms_push` ;

/* Alter table in target */
ALTER TABLE `user_favorite_market`
    ADD COLUMN `marketnm` VARCHAR(256)  COLLATE utf8_general_ci NULL AFTER `marketco` ,
    CHANGE `lcode` `lcode` VARCHAR(2)  COLLATE utf8_general_ci NULL AFTER `marketnm` ;

/* The foreign keys that were dropped are now re-created*/

ALTER TABLE `gsm_config`
    ADD CONSTRAINT `ifx_fam_config_gsm_key`
        FOREIGN KEY (`gsm_key`) REFERENCES `gsm_info` (`gsm_key`) ON DELETE CASCADE ON UPDATE CASCADE ;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;


