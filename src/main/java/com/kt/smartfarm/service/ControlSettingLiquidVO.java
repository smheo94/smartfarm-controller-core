package com.kt.smartfarm.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ControlSettingLiquidVO {
//	Field          Type           Collation        Null    Key     Default  Extra                          Privileges                       Comment
//-------------  -------------  ---------------  ------  ------  -------  -----------------------------  -------------------------------  ----------------------------------------------------------------------------------------
//	id             bigint(20)     (NULL)           NO      PRI     (NULL)   auto_increment                 select,insert,update,references  제어로직 템플릿 ID
//	publish_level  varchar(20)    utf8_general_ci  NO              (NULL)                                  select,insert,update,references  공개여부, 누구나 사용가능, 관리자만, 생성자만, OPEN /  OWNER
//	owner_user_id  varchar(40)    utf8_general_ci  YES             (NULL)                                  select,insert,update,references  저장한 사용자 ID
//	title          varchar(1000)  utf8_general_ci  YES             (NULL)                                  select,insert,update,references  템플릿이름
//	prd_code       char(6)        utf8_general_ci  NO              (NULL)                                  select,insert,update,references  표준 품목 코드/ 나중에 품종으로 바뀔 수도 있으니 일단 6자리로
//	liquid_id      varchar(32)    utf8_general_ci  YES             (NULL)                                  select,insert,update,references  cd_liquid 의 비료코드, 없는 경우 null
//	packing_size   int(11)        (NULL)           NO              (NULL)                                  select,insert,update,references
//	mix_type       varchar(10)    utf8_general_ci  NO              PER                                     select,insert,update,references  PER/RATIO 희석비율 표기방식  1/ n , %
//	mix_rate       int(11)        (NULL)           NO              1000                                    select,insert,update,references  희석 비율
//	watering_amt   int(11)        (NULL)           NO              200                                     select,insert,update,references  원수량
//	comment        varchar(4096)  utf8_general_ci  YES             (NULL)                                  select,insert,update,references  사용자 메모
//	create_dt      datetime       (NULL)           YES             (NULL)                                  select,insert,update,references  입력 날짜
//	update_dt      datetime       (NULL)           YES             (NULL)   on update current_timestamp()  select,insert,update,references  수정날짜
	Long	id;
	String	publishLevel;
	@JsonIgnore
	Integer	ownerUserIdx;
	String	title;
	String	prdCode;
	String	liquidId;
	Integer	packingSize;
	String	mixType;
	Integer	mixRate;
	Integer	wateringAmt;
	String	comment;
	LiquidVO liquidDefault;
}
