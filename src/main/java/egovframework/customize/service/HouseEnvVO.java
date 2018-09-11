package egovframework.customize.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 외부기상대, 임계치 설정 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class HouseEnvVO {

	Integer id;
	Integer productMethodId;
	Integer greenHouseTypeId;
	String houseName;
	String latitude;
	String longitude;
	String description;
	String buildDate;
	String lastUpdate;
	String addr1;
	String addr2;
	String zip;
	Integer nutrientCommonId;
	Integer changeGh;
	Integer changeTemperature;
	Double floorSpace;
	Double houseHorizontal;
	Double houseVertical;
	Double houseHeight;
	Double plantSpacing;
	Integer cropCnt;
	Double houseDirection;
	Double selectTempSensor;
	Double selectHumSensor;
	Integer gsmKey;
	String houseNameI18n;
	
}
