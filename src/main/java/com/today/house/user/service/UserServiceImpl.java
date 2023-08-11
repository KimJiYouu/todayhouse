package com.today.house.user.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.today.house.command.ConsultantVO;
import com.today.house.command.UserVO;


@Service("userService") 
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;

	//로그인
	@Override
	public UserVO user_login_form(UserVO vo) {
		return userMapper.user_login_form(vo);
	}

	//회원가입
	@Override
	public int user_regist_form(UserVO vo) {
		return userMapper.user_regist_form(vo);
	}

	@Override
	public UserVO user_mypage_check_form(UserVO vo) {
		return userMapper.user_mypage_check_form(vo);
	}

	@Override
	public int user_mypage_change(UserVO vo) {
		return userMapper.user_mypage_change(vo);
	}

	@Override
	public ArrayList<ConsultantVO> user_consultant_list(String user_id) {
		return userMapper.user_consultant_list(user_id);
	}

	@Override
	public ConsultantVO user_consultant_info(String user_id, int consultant_num) {
		return userMapper.user_consultant_info(user_id, consultant_num);
	}

}
