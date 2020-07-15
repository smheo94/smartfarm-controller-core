# #모니터링 데이터 연동 테이블 작성

## #관련 테이블 생성
~~~~sql
CREATE TABLE `monitoring_house_type_key` (
  `house_type_id` int(11) NOT NULL,
  `output_key` varchar(64) NOT NULL,
  `update_type` varchar(20) DEFAULT NULL,
  `logic_id` int(11) DEFAULT NULL,
  `device_type_id` int(11) DEFAULT NULL,
  `device_prarm_name` varchar(64) DEFAULT NULL,
  `modbus_address` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`house_type_id`,`output_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `monitoring_house_columns` (
  `house_id` bigint(20) NOT NULL,
  `output_key` varchar(64) NOT NULL,
  `device_type_key` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`house_id`,`output_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
~~~~

## #데이터 입력
```sql
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'outerHum');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'outerTemperature');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'outerLight');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'windSpeed');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'windDirection');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'rainfall');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'levelDetectorHigh');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'levelDetectorMiddle');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'levelDetectorLow');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grEc1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grMoisture1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grTemp1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grEc2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grMoisture2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grTemp2');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grEc3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grMoisture3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grTemp3');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grEc4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grMoisture4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'grTemp4');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlow');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlow');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpH1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpD1');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringH');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringD');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaH1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaD1');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpH2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpD2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaH2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaD2');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpH3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpD3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaH3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaD3');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpH4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpD4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaH4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaD4');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpH5');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpD5');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaH5');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaD5');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpH6');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpD6');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaH6');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaD6');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpH7');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpD7');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaH7');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaD7');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpH8');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringValveOpD8');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaH8');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringFlowAreaD8');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringSupplyPumpOpH');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringSupplyPumpOpD');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringSuctionPumpOpH');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'wateringSuctionPumpOpD');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpH');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpD');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValvePosition');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', ' liquidWateringH');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', ' liquidWateringD');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', ' liquidFlowH');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', ' liquidFlowD');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaH1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaD1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterH1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterD1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpH1');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpD1');


INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpH2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpD2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaH2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaD2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterH2');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterD2');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpH3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpD3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaH3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaD3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterH3');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterD3');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpH4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpD4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaH4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaD4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterH4');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterD4');

INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpH5');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidValveOpD5');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaH5');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowAreaD5');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterH5');
INSERT INTO monitoring_house_type_outkey(house_type_id, output_key) VALUES ( '95', 'liquidFlowWaterD5');

```


## #SP 생성
```sql
DELIMITER $$
DROP PROCEDURE IF EXISTS `sp_update_monitoring_house_columns`$$

CREATE  PROCEDURE `sp_update_monitoring_house_columns`(IN in_house_id INT)
BEGIN

INSERT IGNORE INTO monitoring_house_columns( house_id, output_key ) SELECT id, 'outerHum' FROM green_house GH INNER JOIN monitoring_house_type_outkey OK ON GH.house_type_id = OK.house_type_id AND  house_type_id = 95 AND ( in_house_id IS NULL OR  GH.id  = in_house_id );

/*sensorOuterHum*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 905
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR G.id  = in_house_id ) AND output_key = 'outerHum';


/*sensorOuterTemperature*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 902
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'outerTemperature';


/*sensorOuterLight*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 903
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'outerLight';

/*sensorWindSpeed*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 901
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'windSpeed';

/*sensorWindDirection*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 900
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'windDirection';

/*sensorRainfall*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 906
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'rainfall';

/*levelDetectorHigh*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 71
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'lvDetectorH'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'levelDetectorHigh';

/*level_detector_middle*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 71
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'lvDetectorM'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'levelDetectorMiddle';

/*level_detector_low*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 71
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'lvDetectorL'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'levelDetectorLow';

/*sensor_gr_ec1*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grec1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grEc1';
/*sensor_gr_ec2*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grec2'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grEc2';
/*sensor_gr_ec3*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grec3'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grEc3';
/*sensor_gr_ec4*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grec4'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grEc4';

/*sensor_gr_moisture1*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grmoisture1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grMoisture1';
/*sensor_gr_moisture2*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grmoisture2'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grMoisture2';
/*sensor_gr_moisture3*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grmoisture3'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grMoisture3';
/*sensor_gr_moisture4*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grmoisture4'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grMoisture4';

/*sensor_gr_temp1*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grtemp1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grTemp1';
/*sensor_gr_temp2*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grtemp2'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grTemp2';
/*sensor_gr_temp3*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grtemp3'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grTemp3';
/*sensor_gr_temp4*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grtemp4'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'grTemp4';

/*sensor_watering_flow*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 12801
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'wateringFlow';

/*sensor_liquid_flow*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 12802
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'liquidFlow';



/*watering_total_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'flowTotal'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'wateringH';

UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'flowTotal'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'wateringD';


/*watering_valve_op_time_h1*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'valve'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = CONCAT( 'wateringValveOpH', CSD.device_insert_order );

/*watering_valve_op_time_h1*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'valve'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = CONCAT( 'wateringValveOpD', CSD.device_insert_order );




/*watering_flow_area_total_h1*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'flowArea'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = CONCAT( 'wateringFlowAreaH', CSD.device_insert_order );


/*watering_flow_area_total_h1*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'flowArea'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num 
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = CONCAT( 'wateringFlowAreaD', CSD.device_insert_order );


/*watering_supply_pump_op_time_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'mainPump'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'wateringSupplyPumpOpH';

/*watering_suction_pump_op_time_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'suctionPump'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'wateringSuctionPumpOpH';


/*watering_supply_pump_op_time_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'mainPump'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'wateringSupplyPumpOpD';

/*watering_suction_pump_op_time_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 44
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'suctionPump'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'wateringSuctionPumpOpD';

/*liquid_valve_op_time_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'liquidValve'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'liquidValveOpH';

UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'liquidValve'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'liquidValveOpD';

/*liquid_valve_op_time_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'liquidValve'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num 
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'liquidValvePosition';


/*liquid_watering_total_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'wateringTotal'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'liquidWateringH';

/*liquid_watering_total_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'wateringTotal'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'liquidWateringD';

/*liquid_flow_total_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'liquidTotal'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'liquidFlowH';

/*liquid_flow_total_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'liquidTotal'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = 'liquidFlowD';



/*watering_valve_op_time_h1*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'valve'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key =  CONCAT( 'liquidValveOpH', CSD.device_insert_order ); 

/*watering_valve_op_time_h1*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'valve'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$op_time_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key =  CONCAT( 'liquidValveOpD', CSD.device_insert_order ); 



/*liquid_flow_area_total_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'liquidArea'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key =CONCAT( 'liquidFlowAreaH', CSD.device_insert_order ); 



/*liquid_flow_area_total_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'liquidArea'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num 
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key =CONCAT( 'liquidFlowAreaD', CSD.device_insert_order ); 



/*liquid_flow_watering_total_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'wateringArea'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_h") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = CONCAT( 'liquidFlowWaterH', CSD.device_insert_order ); 

/*liquid_flow_watering_total_h*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.logic_id = 41
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 95
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'wateringArea'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx, "$total_d") WHERE ( in_house_id IS NULL OR  G.id  = in_house_id ) AND output_key = CONCAT( 'liquidFlowWaterD', CSD.device_insert_order ); 

DELETE FROM monitoring_house_columns WHERE house_id IN ( SELECT id FROM green_house WHERE green_house_type_id = 95 AND ( in_house_id IS NULL OR  green_house.id  = in_house_id ) ) AND device_type_key IS NULL;

	END$$

DELIMITER ;

```


#  DB 점검
## 파티션 테이블 생성 - 4.6GB에 15분이상 걸림
```sql
ALTER TABLE monitoring_history 
PARTITION BY RANGE (YEAR(`event_date_min`))
SUBPARTITION BY HASH (MONTH(`event_date_min`))
SUBPARTITIONS 12 (
    PARTITION c0 VALUES LESS THAN (2020),
    PARTITION c2021 VALUES LESS THAN (2021),
    PARTITION c2022 VALUES LESS THAN (2022),
    PARTITION c2023 VALUES LESS THAN (2023),
    PARTITION c2024 VALUES LESS THAN (2024),
    PARTITION c2025 VALUES LESS THAN (2025),
    PARTITION c2026 VALUES LESS THAN (2026),
    PARTITION c2027 VALUES LESS THAN (2027),
    PARTITION c2028 VALUES LESS THAN (2028),
    PARTITION c2029 VALUES LESS THAN (2029),
    PARTITION cn VALUES LESS THAN (3000)
);
```


