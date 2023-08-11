package com.today.house.command;

import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpExVO {

	private Integer cp_ex_num;
	private String cp_ex_title;
	private String cp_ex_content;
	private LocalDateTime cp_ex_regdate;
	
	@Pattern(regexp = "^(?!.*[ㄱ-ㅎㅏ-ㅣ가-힣])(?=.*\\d).{8,}$", 
			message = "아이디는 영어대소문자 8글자이상이며, 특수문자는 포함하지 않습니다.")
	private String cp_id;
	
}
