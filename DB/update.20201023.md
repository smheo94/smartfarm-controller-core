# 팜에이트 연동

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_update_monitoring_house_columns`$$

CREATE PROCEDURE `sp_update_monitoring_house_columns`(IN in_house_id BIGINT)
proc_start: BEGIN
 
DECLARE EXIT HANDLER FOR SQLEXCEPTION 
BEGIN
    ROLLBACK;
END;


SET @IN_HOUSE_ID = NULL;
SELECT GH.id INTO @IN_HOUSE_ID
FROM green_house GH 
INNER JOIN monitoring_house_type_outkey OK ON GH.green_house_type_id = OK.house_type_id AND  house_type_id = 95 AND GH.id = in_house_id
LIMIT 1;

IF @IN_HOUSE_ID  IS NULL  THEN
    LEAVE proc_start;
END IF;
START TRANSACTION;
INSERT IGNORE INTO monitoring_house_columns( house_id, output_key ) 
SELECT id, output_key
FROM green_house GH 
INNER JOIN monitoring_house_type_outkey OK ON GH.green_house_type_id = OK.house_type_id AND  house_type_id = 95 AND ( in_house_id IS NULL OR  GH.id  = in_house_id );


UPDATE monitoring_house_columns M
INNER JOIN green_house GH ON M.house_id = GH.id  AND GH.id = @IN_HOUSE_ID
INNER JOIN monitoring_house_type_outkey OK ON  OK.house_type_id = 95
SET  M.device_type_key = NULL
WHERE OK.output_key = M.output_key;


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
    COMMIT;
	END$$

DELIMITER ;





DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_update_monitoring_house_columns_greenhouse`$$

CREATE  PROCEDURE `sp_update_monitoring_house_columns_greenhouse`(IN in_house_id BIGINT)
proc_start: BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION 
BEGIN
    ROLLBACK;
END;


SET @IN_HOUSE_ID = NULL;
SELECT GH.id INTO @IN_HOUSE_ID
FROM green_house GH 
INNER JOIN monitoring_house_type_outkey OK ON GH.green_house_type_id = OK.house_type_id AND  house_type_id = 11 AND GH.id = in_house_id
LIMIT 1;

IF @IN_HOUSE_ID  IS NULL  THEN
    LEAVE proc_start;
END IF;
    START TRANSACTION;
INSERT IGNORE INTO monitoring_house_columns( house_id, output_key ) 
SELECT id, output_key
FROM green_house GH 
INNER JOIN monitoring_house_type_outkey OK ON GH.green_house_type_id = OK.house_type_id AND  house_type_id = 11 AND GH.id = @IN_HOUSE_ID;

UPDATE monitoring_house_columns M
INNER JOIN green_house GH ON M.house_id = GH.id   AND GH.id = @IN_HOUSE_ID
INNER JOIN monitoring_house_type_outkey OK ON  OK.house_type_id = 11
SET  M.device_type_key = NULL
WHERE OK.output_key = M.output_key;

/*sensorOuterHum*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 905
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'outerHum';


/*sensorOuterTemperature*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 902
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'outerTemperature';


/*sensorOuterLight*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 903
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'outerLight';

/*sensorWindSpeed*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 901
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'windSpeed';

/*sensorWindDirection*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 900
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'windDirection';

/*sensorRain*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 904
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'rain';


SET @WINDOW_SETTING_ID = NULL;
SELECT id  INTO @WINDOW_SETTING_ID
FROM control_setting WHERE green_house_id = @IN_HOUSE_ID AND logic_id IN ( 1, 2, 3, 4, 5, 6, 54, 55, 56 )
ORDER BY view_order ASC LIMIT 0,1;

/*settingTemp*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'settingTemp'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'settingTemp';

/*ventTemp*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code = 'ventTemp'
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'ventTemp';




/*window1LPos*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code IN (  'lightWindow', 'leftSideWindow', 'topWindow', 'frontSideWindow' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'window1LPos';


/*window1RPos*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code IN (  'rightWindow', 'rightSideWindow', 'rearSideWindow' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'window1RPos';


SET @WINDOW_SETTING_ID = NULL;
SELECT id  INTO @WINDOW_SETTING_ID
FROM control_setting WHERE green_house_id = @IN_HOUSE_ID AND logic_id IN ( 1, 2, 3, 4, 5, 6, 54, 55, 56 )
ORDER BY view_order ASC LIMIT 1,1;
/*window2LPos*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code IN (  'lightWindow', 'leftSideWindow', 'topWindow', 'frontSideWindow' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'window2LPos';


/*window2RPos*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code  IN (  'rightWindow', 'rightSideWindow', 'rearSideWindow' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'window2RPos';


SET @WINDOW_SETTING_ID = NULL;
SELECT id  INTO @WINDOW_SETTING_ID
FROM control_setting WHERE green_house_id = @IN_HOUSE_ID AND logic_id IN ( 1, 2, 3, 4, 5, 6, 54, 55, 56 )
ORDER BY view_order ASC LIMIT 2,1;
/*window3LPos*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code IN (  'lightWindow', 'leftSideWindow', 'topWindow', 'frontSideWindow' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'window3LPos';


/*window3RPos*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code  IN (  'rightWindow', 'rightSideWindow', 'rearSideWindow' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'window3RPos';





SELECT id  INTO @WINDOW_SETTING_ID
FROM control_setting WHERE green_house_id = @IN_HOUSE_ID AND logic_id IN ( 36 )
ORDER BY view_order ASC LIMIT 0,1;
/*verticalCurtainPos*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code IN (  'sideCurtain' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'verticalCurtainPos';


SELECT id  INTO @WINDOW_SETTING_ID
FROM control_setting WHERE green_house_id = @IN_HOUSE_ID AND logic_id IN ( 20 )
ORDER BY view_order ASC LIMIT 0,1;
/*horizontal1CurtainPos*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code IN (  'curtain' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'horizontal1CurtainPos';


SELECT id  INTO @WINDOW_SETTING_ID
FROM control_setting WHERE green_house_id = @IN_HOUSE_ID AND logic_id IN ( 20 )
ORDER BY view_order ASC LIMIT 1,1;
/*horizontal2CurtainPos*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code IN (  'curtain' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'horizontal2CurtainPos';


SELECT id  INTO @WINDOW_SETTING_ID
FROM control_setting WHERE green_house_id = @IN_HOUSE_ID AND logic_id IN ( 14 )
ORDER BY view_order ASC LIMIT 0,1;
/*floatFanOn*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code IN (  'floatFan' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'floatFanOn';


SELECT id  INTO @WINDOW_SETTING_ID
FROM control_setting WHERE green_house_id = @IN_HOUSE_ID AND logic_id IN ( 11 )
ORDER BY view_order ASC LIMIT 0,1;
/*ventFanOn*/
UPDATE monitoring_house_columns M
INNER JOIN control_setting MG ON MG.green_house_id = M.house_id AND MG.id = @WINDOW_SETTING_ID
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN control_logic_device CLD ON  MG.logic_id  =  CLD.logic_id  AND CLD.device_param_code IN (  'ventFan' )
INNER JOIN control_setting_device CSD ON MG.id = CSD.control_setting_id AND CLD.device_num = CSD.device_num  AND CSD.device_insert_order = 1
INNER JOIN device D ON CSD.device_id  =  D.id 
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'ventFanOn';



/*sensor_gr_ec1*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grec1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'grEc';


/*sensor_gr_moisture1*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grmoisture1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'grMoisture';


/*sensor_gr_temp1*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grtemp1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'grTemperature';

/*sensor_gr_temp1*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'grPh1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'grPh';


/*innerTemp*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'temp1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'innerTemp';


/*innerHum*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'hum1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'innerHum';

/*innerCo2*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'co2'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'innerCo2';


/*innerPh*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'ph1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'innerPh';

/*innerEc*/
UPDATE monitoring_house_columns M
INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
INNER JOIN green_house G ON G.id = MG.green_house_id AND G.green_house_type_id = 11
INNER JOIN device D ON MG.device_id  =  D.id AND D.modbus_address1 = 'ec1'
INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id = @IN_HOUSE_ID AND output_key = 'innerEc';



DELETE FROM monitoring_house_columns WHERE house_id IN ( SELECT id FROM green_house WHERE green_house_type_id = 11 AND ( in_house_id IS NULL OR  green_house.id  = in_house_id ) ) AND device_type_key IS NULL;
COMMIT;

	END$$

DELIMITER ;







DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_update_monitoring_house_columns_nutrient`$$

CREATE  PROCEDURE `sp_update_monitoring_house_columns_nutrient`(IN in_house_id BIGINT)
proc_start: BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION 
BEGIN
    ROLLBACK;
END;


    SET @IN_HOUSE_ID = NULL;
    SELECT GH.id INTO @IN_HOUSE_ID 
    FROM green_house GH 
    INNER JOIN control_setting CS ON CS.green_house_id = GH.id AND CS.logic_id = 45  AND GH.id = in_house_id ;
    IF @IN_HOUSE_ID  IS NULL  THEN
        LEAVE proc_start;
    END IF;
        START TRANSACTION;
    INSERT IGNORE INTO monitoring_house_columns( house_id, output_key ) 
    SELECT @IN_HOUSE_ID, output_key
    FROM  monitoring_house_type_outkey OK WHERE  house_type_id = 100;


UPDATE monitoring_house_columns M
INNER JOIN green_house GH ON M.house_id = GH.id   AND GH.id = @IN_HOUSE_ID
INNER JOIN monitoring_house_type_outkey OK ON  OK.house_type_id = 100
SET  M.device_type_key = NULL
WHERE OK.output_key = M.output_key;

    /*supplyEC*/
    UPDATE monitoring_house_columns M
    #SELECT * FROM monitoring_house_columns M
    INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
    INNER JOIN green_house G ON G.id = MG.green_house_id 
    INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 30005
    INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
    SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) 
    WHERE G.id  = @IN_HOUSE_ID  AND output_key = 'supplyEC';

    /*supplyPH*/
    UPDATE monitoring_house_columns M
    INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
    INNER JOIN green_house G ON G.id = MG.green_house_id 
    INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 30006
    INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
    SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id  = @IN_HOUSE_ID AND output_key = 'supplyPH';

    /*supplyFlow*/
    UPDATE monitoring_house_columns M
    INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
    INNER JOIN green_house G ON G.id = MG.green_house_id 
    INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 30007
    INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
    SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id  = @IN_HOUSE_ID AND output_key = 'supplyFlow';

    /*supplyCount*/
    UPDATE monitoring_house_columns M
    INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
    INNER JOIN green_house G ON G.id = MG.green_house_id 
    INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 30008
    INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
    SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id  = @IN_HOUSE_ID   AND output_key = 'supplyCount';


    /*supplyCount*/
    UPDATE monitoring_house_columns M
    INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
    INNER JOIN green_house G ON G.id = MG.green_house_id 
    INNER JOIN device D ON MG.device_id  =  D.id AND D.device_type_id = 30008
    INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
    SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx ) WHERE G.id  = @IN_HOUSE_ID AND output_key = 'supplyCount';

    /*supplyArea*/
    UPDATE monitoring_house_columns M    
    INNER JOIN map_green_house_device MG ON MG.green_house_id = M.house_id
    INNER JOIN green_house G ON G.id = MG.green_house_id 
    INNER JOIN device D ON MG.device_id  =  D.id  AND D.device_type_id = 30004
    INNER JOIN cd_device_type DT ON DT.id = D.device_type_id
    SET M.device_type_key = CONCAT( DT.device_type, "$", D.device_type_idx) WHERE G.id  = @IN_HOUSE_ID  AND output_key = CONCAT( 'supplyArea', D.device_type_idx ); 


    DELETE FROM monitoring_house_columns WHERE house_id = @IN_HOUSE_ID  AND device_type_key IS NULL;
    COMMIT;
END;
$$

DELIMITER ;
