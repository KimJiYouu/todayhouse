package com.today.house.cp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.today.house.command.ConsultantVO;
import com.today.house.command.CpExUploadVO;
import com.today.house.command.CpExVO;
import com.today.house.command.CpUploadVO;
import com.today.house.command.CpVO;
import com.today.house.util.CpCriteria;

public interface CpService {
	

	public CpVO cp_login_form(CpVO vo); //로그인
	public CpVO cp_mypage_check_form(CpVO vo); //마이페이지 비밀번호확인
	public int cp_mypage_change(CpVO vo, List<MultipartFile> list); //마이페이지 정보수정
	public ArrayList<CpUploadVO> getFullFileList(); //시공사 사진 전체가져오기
	public ArrayList<CpExUploadVO> getFullExFileList(); // 시공사례사진파일 전체가져오기
	public int cp_regist_form(CpVO vo); //회원가입
	public ArrayList<CpVO> cp_getList(String cp_major, CpCriteria cri); //시공사 목록 갖고오기
	public int getTotal(String cp_major, CpCriteria cri); //총합구하기
	public CpVO cp_getdetail(String cp_id); //시공사 상세정보
	public ArrayList<CpExVO> cp_getExdetail(String cp_name); //시공사례 정보리스트
	public int cp_content_ex_regist (CpExVO vo, List<MultipartFile> list ); //시공사례등록
	public CpExVO cp_content_ex_detail (CpExVO vo); //시공사 사례 상세
	public int user_consultant_regist_form(ConsultantVO vo); //사용자 상담신청
	public ArrayList<ConsultantVO> cp_getConsultantList(String cp_id); //시공사 my 신청 리스트
	public int cp_consultant_accept (ConsultantVO vo); //시공사 신청리스트 승낙
	public int cp_consultant_decline (ConsultantVO vo); //시공사 신청리스트 거절
	public CpExVO cp_content_ex_modify (CpExVO vo); //시공 사례 수정
	public int cp_ex_change (CpExVO vo, List<MultipartFile> list); //시공사례수정 form
	public int cp_ex_count (String cp_id); //시공사례건수 구하기
}
