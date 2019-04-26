package egovframework.customize.service;

import com.mysql.jdbc.Blob;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HousePictureDiaryVO {

	Integer id;
	Integer greenHouseId; // 
	String title; // 작업일지 명 ( 농장이름 + 작물 + 종류 )
	String content; // 내용
	String work; // 종류
	String etc; // ?
	String startDate; // 시작날짜 // account의 경우 startDate 와 endDate 동일하게
	String endDate; // 종료날짜
	String crops; // 작물
	Double harvest; // 수확량
	Integer income; // 수입일떄만
	Integer spend; // 지출일때만
	String contentType; // farming || account
	String workTime; //  종일 오전 오후
	Blob image;
	String forecastDate;
	String weather;
	Double temperature;
	Integer rainChance;
	Integer rainfall;
	Integer windSpeed;
}
