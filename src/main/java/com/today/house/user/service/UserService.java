package com.today.house.user.service;

import java.util.ArrayList;

import com.today.house.command.ConsultantVO;
import com.today.house.command.UserVO;

public interface UserService {

	public UserVO user_login_form(UserVO vo); //로그인
	public UserVO user_mypage_check_form(UserVO vo); //마이페이지 비밀번호확인
	public int user_mypage_change(UserVO vo); //마이페이지 정보수정
	public int user_regist_form(UserVO vo); //회원가입
	public ArrayList<ConsultantVO> user_consultant_list(String user_id); //유저상담목록
	public ConsultantVO user_consultant_info (String user_id, int consultant_num); //유서 상담 상세
}
