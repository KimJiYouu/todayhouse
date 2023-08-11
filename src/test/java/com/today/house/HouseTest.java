package com.today.house;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.today.house.command.HouseopenVO;
import com.today.house.houseopen.service.HouseopenMapper;

@SpringBootTest
public class HouseTest {

	@Autowired
	private HouseopenMapper houseOpenMapper;
	
	@Test
	public void datainsert() {
		String arr[] = {"원룸/오피스텔", "아파트", "빌라", "단독주택", "사무공간", "상업공간", "기타"};
		String arr2[] = {"서울특별시", "부산광역시", "대구광역시","인천광역시","광주광역시","대전광역시","울산광역시", "강원도", "경기도", "경상남도", "전라도", "세종특별자치시", "제주특별자치도"};
		String arr3[] = {"10평미만","10평대", "20평대", "30평대","40평대","50평대","60평대","70평대이상"};
		String arr4[] = {"싱글라이프", "신혼부부", "아기랑함께","취학자녀와함께","부모님과함께"};
		String arr5[] = {"리모델링", "홈스타일링","부분공사","건축"};
 		for(int i = 1; i <= 50; i++) {
			Random ran = new Random();
			int num = ran.nextInt(arr.length);
			int num2 = ran.nextInt(arr2.length);
			int num3 = ran.nextInt(arr3.length);
			int num4 = ran.nextInt(arr4.length);
			int num5 = ran.nextInt(arr5.length);
			HouseopenVO vo = HouseopenVO.builder()
										.house_type(arr[num])
										.house_size(arr3[num3])
										.house_adr(arr2[num2])
										.house_fam(arr4[num4])
										.house_remo(arr5[num5])
										.house_title("한 눈에 반했어요 따스한 숲속의 전원주택")
										.house_content("높은 층고의 거실, 체리색 주방, 3개의 방 그리고 숨겨진 다락까지 있는 공간이에요. 오직 휴식 만을 위한 안방, 일을 할 수 있는 서재 겸 다이닝룸, 멀리 오는 손님을 위한 게스트룸으로 구성했습니다.전원주택이다 보니 사방에 창문이 있어서 시시각각 변화하는 날씨와 계절을 감상하는 재미가 있어요. 주변이 산으로 둘러 쌓여 있어 어느 창문에서 보더라도 초록 초록한 풍경이 펼쳐진답니다.")
										.user_id("ENFJ" + i*100)
										.build();
			
			houseOpenMapper.houseopenWrite(vo);
		}
	}
}
