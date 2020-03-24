package com.kt.smartfarm.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LiquidVO {
//	Field                 Type         Collation        Null    Key     Default  Extra   Privileges                       Comment
//--------------------  -----------  ---------------  ------  ------  -------  ------  -------------------------------  ---------------------------------------------
//	liquid_id          varchar(32)  utf8_general_ci  NO              (NULL)           select,insert,update,references  액체 비료 코드
//	liquid_name           varchar(32)  utf8_general_ci  NO              (NULL)           select,insert,update,references  액체 비료 이름
//	mix_type              varchar(10)  utf8_general_ci  NO              PER              select,insert,update,references  "RATIO" , "PER" , 희석비 기준 (%, 1/n)
//	default_mix_rate      int(11)      (NULL)           NO              1000             select,insert,update,references  기본 희석비
//	default_packing_size  int(11)      (NULL)           YES             20               select,insert,update,references  비료 용량(L/Kg)
//	packing_unit          varchar(4)   utf8_general_ci  YES             L                select,insert,update,references  포장 단위 ( L / Kg )
	String liquidId;
	String liquidName;
	String mixType;
	Integer defaultMixRate;
	Integer defaultPackingSize;
	String packingUnit;
}
