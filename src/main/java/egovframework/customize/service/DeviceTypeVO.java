package egovframework.customize.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DeviceTypeVO {	
	
	Integer id;
	String name;
	String description;
	String last_update;
//	String device_type;
//	String device_type_name;
//	String device_type_group;
	String kind;
//	Integer is_physical_device; 
	String manufacturer;
//	String model_name;
//	String model_spec;
//	String model_version;
//	Integer is_master;
//	Integer use_house_type;
//	String default_address1;
//	String default_address2;
//	String default_address3;
	String dcac;
//	double min_value;
//	double max_value;
//	String description_i18n;
//	String device_type_name_i18n;
	String unit;
	
	
	String lastUpdate;
	String deviceType;
	String deviceTypeName;
	String deviceTypeGroup;
	Integer isPhysicalDevice; 
	String modelName;
	String modelSpec;
	String modelVersion;
	Integer isMaster;
	Integer useHouseType;
	String defaultAddress1;
	String defaultAddress2;
	String defaultAddress3;
	double minValue;
	double maxValue;
	String descriptionI18n;
	String deviceTypeNameI18n;
	
	Integer roundNumber;
	String topicGroup;
	Double resetPoint;
	Integer accumM;
	Integer accumH;
	Integer accumD;
	Integer opTime;
	Integer valueType;	
}
