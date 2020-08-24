# 사과연구소용 제어로직 추가(관비제어로직)

INSERT INTO control_logic (
        id, NAME, java_class_name, ui_class_name, description, ui_help, can_multi_logic, default_period_size, view_order, last_update
)
SELECT
        141, '과수관비', java_class_name, ui_class_name, '관수관비 수동제어', ui_help, can_multi_logic, default_period_size, view_order, last_update
FROM
        control_logic WHERE id = '41';
        
INSERT INTO control_logic_device (
        id, logic_id, device_num, device_param_code, device_param_name, device_type, is_main, required, able_array, description, display_order, is_used, relative_device_num, last_update
)
SELECT
        id + 100000, 141, device_num, device_param_code, device_param_name, device_type, is_main, required, able_array, description, display_order, is_used, relative_device_num, last_update
FROM
        control_logic_device WHERE logic_id = '41';
        
INSERT INTO control_logic_property_lnk (
        logic_id, properties_id, is_period, priority, on_pc, on_mobile, on_panel, manual_command_num, last_update, depend_device, parent_property_id
)
SELECT
        141, properties_id, is_period, priority, on_pc, on_mobile, on_panel, manual_command_num, last_update, depend_device, parent_property_id
FROM 
        control_logic_property_lnk  WHERE logic_id = '41';
