package com.today.house.command;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserVO {

	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{3,}$", 
			message = "아이디는 영어대소문자 8글자이상이며, 특수문자는 포함하지 않습니다.")
	private String user_id;
	
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{3,}$", 
			message = "비밀번호는 영어대소문자 8글자 이상이며, 특수문자는 최소 한 개 이상 포함합니다.")
	private String user_pw;
	
	@Email(message = "올바른 이메일 주소 형식이 아닙니다.")
	private String user_email;
}
