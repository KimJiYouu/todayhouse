package com.today.house.houseopen.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.today.house.command.HouseopenUploadVO;
import com.today.house.command.HouseopenVO;

@Mapper
public interface HouseopenMapper {
	public ArrayList<HouseopenVO> getHouseopenList(); //전체 글 조회
	public ArrayList<HouseopenVO> getFilterList(HouseopenVO vo);//글등록
	public void houseFileRegist(HouseopenUploadVO vo); //파일등록
	public int houseopenWrite(HouseopenVO vo); //등록
	public ArrayList<HouseopenUploadVO> getFullFileList(); // 사진파일 전체가져오기
	public HouseopenVO getContent(Integer house_num); //상세
	public void houseopenDelete(Integer house_num);//삭제
	public int houseopenModify(HouseopenVO vo); //글수정
	public int houseFileModify(HouseopenUploadVO vo); //사진수정

}
