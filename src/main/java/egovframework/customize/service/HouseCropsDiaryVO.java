package egovframework.customize.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 작업일지 , 경영일지
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class HouseCropsDiaryVO {

	Integer id;
	Integer greenHouseId;
	Double leafWidth; 	// 잎 길이
	Double stem;		// 줄기굵기
	Double harvest;		// 수확량(kg)
	Double fruitSet;	// 착과군
	Double getherSet;	// 수확군
	Double fruitHeight;	// 화방높이
	Double bloom;		// 개화군
	Double fruitNumber;	// 열매 수
	Double averWeight;	// 평균 과중(g)
	Double growLength;	// 생장길이
	Double leafSize;	// 잎길이
	Double leafNum;		// 잎 수
	String startDate;	//입력날짜
	String uploadFile;
	String content;		// 사용안함
	String title;
	String crops;		// 작물

	
}
