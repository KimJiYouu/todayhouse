package com.today.house.cp.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.today.house.command.ConsultantVO;
import com.today.house.command.CpExUploadVO;
import com.today.house.command.CpExVO;
import com.today.house.command.CpUploadVO;
import com.today.house.command.CpVO;
import com.today.house.command.HouseopenUploadVO;
import com.today.house.util.CpCriteria;

@Mapper
public interface CpMapper {

	
	public CpVO cp_login_form(CpVO vo); //로그인
	public CpVO cp_mypage_check_form(CpVO vo); //마이페이지 비밀번호확인
	public int cp_mypage_change(CpVO vo); //마이페이지 정보수정
	public void cp_FileRegist(CpUploadVO vo); //파일등록
	public int cp_FileModify(CpUploadVO vo); //사진수정
	public ArrayList<CpUploadVO> getFullFileList(); // 사진파일 전체가져오기
	public ArrayList<CpExUploadVO> getFullExFileList(); // 시공사례사진파일 전체가져오기
	public int cp_regist_form(CpVO vo); //회원가입
	public ArrayList<CpVO> cp_getList(@Param("cp_major") String cp_major, 
									  @Param("cri") CpCriteria cri); //시공사 목록 갖고오기
	public int getTotal(@Param("cp_major") String cp_major, 
						@Param("cri") CpCriteria cri); //총합 구하기
	public CpVO cp_getdetail(String cp_id); //시공사 상세정보
	public ArrayList<CpExVO> cp_getExdetail(String cp_id); //시공사례 정보 리스트
	public int cp_content_ex_regist (CpExVO vo); //시공사례등록
	public int cpEx_FileRegist(CpExUploadVO vo); // 시공사진등록
	public CpExVO cp_content_ex_detail (CpExVO vo); //시공사 사례 상세
	
	public int count_Cp_id(String cp_id); //파일테이블 아이디 1개 제한
	
	public int user_consultant_regist_form(ConsultantVO vo); //사용자 상담신청
	public ArrayList<ConsultantVO> cp_getConsultantList(String cp_id); //시공사 my 신청 리스트
	public int cp_consultant_accept (ConsultantVO vo); //시공사 신청리스트 승낙
	public int cp_consultant_decline (ConsultantVO vo); //시공사 신청리스트 거절
	public CpExVO cp_content_ex_modify (CpExVO vo); //시공 사례 수정
	public int cp_ex_change (CpExVO vo); //시공사례수정 form
	public int cp_ex_FileModify(CpExUploadVO vo); //사진수정
	public int cp_ex_count (String cp_id); //시공사례건수 구하기
}
