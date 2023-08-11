package com.today.house.houseopen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.today.house.command.HouseopenUploadVO;
import com.today.house.command.HouseopenVO;

public interface HouseopenService {
	public ArrayList<HouseopenVO> getHouseopenList(); //전체 글 조회
	public ArrayList<HouseopenVO> getFilterList(HouseopenVO vo);
	public int houseopenWrite(HouseopenVO vo , List<MultipartFile> list); //등록
	public HouseopenVO getContent(Integer house_num); //상세
	public ArrayList<HouseopenUploadVO> getFullFileList(); // 사진파일 전체가져오기
	public ArrayList<HouseopenUploadVO> getAjaxImg(Integer house_num); //이미지데이터조회
	public void houseopenDelete(Integer house_num);//삭제
	public int houseopenModify(HouseopenVO vo, List<MultipartFile> list); //수정

}
