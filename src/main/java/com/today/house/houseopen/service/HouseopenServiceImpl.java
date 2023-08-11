package com.today.house.houseopen.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.today.house.command.HouseopenUploadVO;
import com.today.house.command.HouseopenVO;

@Service("houseOpenService")
public class HouseopenServiceImpl implements HouseopenService{
	
	@Autowired
	private HouseopenMapper houseOpenMapper;
	@Value("${project.upload.path}")
	private String uploadPath;
	
	//폴더 생성 함수
	public String makeFolder() {
		String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		File file = new File(uploadPath + "/" + path);
		
		if(file.exists() == false) {
			file.mkdirs();
		}
		return path;
	}
	
	//글등록
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int houseopenWrite(HouseopenVO vo , List<MultipartFile> list) {
		
		//업로드 처리
		for(MultipartFile file : list) {
			System.out.println(file.isEmpty());
			
			//파일 이름을 받음
			String originname = file.getOriginalFilename();
			
			//브라우저별로 파일의 경로가 다를 수 있기 때문에 \\기준으로 파일명만 잘라서 다시 저장
			String filename = originname.substring(originname.lastIndexOf("\\") + 1);
			
			//동일한 파일을 재업로드 시 기존 파일을 덮어버리기 때문에 난수이름으로 파일명을 바꿔서 올림
			String uuid = UUID.randomUUID().toString();
			
			//날짜벼롤 폴더 생성
			String filepath = makeFolder();
			
			//save할 경로
			String savepath = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;
			
			File saveFile = new File(savepath);
			try {
				file.transferTo(saveFile);
			} catch (Exception e) {
				System.out.println("파일업로드 중 error발생");
				e.printStackTrace();
				return 0;
			}
			
			houseOpenMapper.houseFileRegist(HouseopenUploadVO.builder()
															  .filename(filename)
															  .filepath(filepath)
															  .uuid(uuid)
															  .build());
					
		  System.out.println("서비스통과");
		}
		
		return houseOpenMapper.houseopenWrite(vo);
	}

	
	
	
	@Override
	public ArrayList<HouseopenVO> getHouseopenList() { //전체리스트
		return houseOpenMapper.getHouseopenList();
	}
	@Override
	public ArrayList<HouseopenVO> getFilterList(HouseopenVO vo) {//조회리스트
		return houseOpenMapper.getFilterList(vo);
	}


	@Override
	public HouseopenVO getContent(Integer house_num) { //상세화면
		return houseOpenMapper.getContent(house_num);
	}

	@Override
	public ArrayList<HouseopenUploadVO> getAjaxImg(Integer house_num) {
		return null;
	}

	//사진 전체 리스트 가져오기
	@Override
	public ArrayList<HouseopenUploadVO> getFullFileList() {
		
		return houseOpenMapper.getFullFileList();
	}

	@Override
	public void houseopenDelete(Integer house_num) { //삭제
		houseOpenMapper.houseopenDelete(house_num);
	}

	////글수정////
	@Override
	public int houseopenModify(HouseopenVO vo, List<MultipartFile> list) {
		
		//업로드 처리
		for(MultipartFile file : list) {
			System.out.println(file.isEmpty());
			
			//파일 이름을 받음
			String originname = file.getOriginalFilename();
			
			//브라우저별로 파일의 경로가 다를 수 있기 때문에 \\기준으로 파일명만 잘라서 다시 저장
			String filename = originname.substring(originname.lastIndexOf("\\") + 1);
			
			//동일한 파일을 재업로드 시 기존 파일을 덮어버리기 때문에 난수이름으로 파일명을 바꿔서 올림
			String uuid = UUID.randomUUID().toString();
			
			//날짜벼롤 폴더 생성
			String filepath = makeFolder();
			
			//save할 경로
			String savepath = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;
			
			File saveFile = new File(savepath);
			try {
				file.transferTo(saveFile);
			} catch (Exception e) {
				System.out.println("파일업로드 중 error발생");
				e.printStackTrace();
				return 0;
			}
			
			houseOpenMapper.houseFileModify(HouseopenUploadVO.builder()
															  .filename(filename)
															  .filepath(filepath)
															  .uuid(uuid)
															  .house_num(vo.getHouse_num())
															  .build());
					
		  System.out.println("서비스통과");
		}
		return houseOpenMapper.houseopenModify(vo);
	}


}
