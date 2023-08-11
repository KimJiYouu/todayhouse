package com.today.house.cp.service;

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

import com.today.house.command.ConsultantVO;
import com.today.house.command.CpExUploadVO;
import com.today.house.command.CpExVO;
import com.today.house.command.CpUploadVO;
import com.today.house.command.CpVO;
import com.today.house.util.CpCriteria;

@Service("cpService")
public class CpServiceImpl implements CpService {

	@Autowired
	private CpMapper cpMapper;	
	@Value("${project.upload.path}")
	private String uploadPath;


	@Override
	public CpVO cp_login_form(CpVO vo) {
		return cpMapper.cp_login_form(vo);
	}

	@Override
	public int cp_regist_form(CpVO vo) {
		return cpMapper.cp_regist_form(vo);
	}

	@Override
	public ArrayList<CpVO> cp_getList(String cp_major, CpCriteria cri) {
		return cpMapper.cp_getList(cp_major, cri);
	}

	@Override
	public int getTotal(String cp_major, CpCriteria cri) {
		return cpMapper.getTotal(cp_major, cri);
	}

	@Override
	public CpVO cp_getdetail(String cp_id) {
		return cpMapper.cp_getdetail(cp_id);
	}

	@Override
	public ArrayList<CpExVO> cp_getExdetail(String cp_id) {
		return cpMapper.cp_getExdetail(cp_id);
	}

	@Override
	public int cp_content_ex_regist(CpExVO vo, List<MultipartFile> list) {
		
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
			
				
				cpMapper.cpEx_FileRegist(CpExUploadVO.builder()
						.filename(filename)
						.filepath(filepath)
						.uuid(uuid)
						.cp_ex_num(vo.getCp_ex_num())
						.build());
						
			
					
		  System.out.println("서비스통과");
		}
		
		
		return cpMapper.cp_content_ex_regist(vo);
	}

	@Override
	public CpVO cp_mypage_check_form(CpVO vo) {
		return cpMapper.cp_mypage_check_form(vo);
	}

	
	//폴더 생성 함수
	public String makeFolder() {
		String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		File file = new File(uploadPath + "/" + path);
		
		if(file.exists() == false) {
			file.mkdirs();
		}
		return path;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int cp_mypage_change(CpVO vo, List<MultipartFile> list) {
		
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
			if(cpMapper.count_Cp_id(vo.getCp_id()) == 0) {
				
				cpMapper.cp_FileRegist(CpUploadVO.builder()
						.filename(filename)
						.filepath(filepath)
						.uuid(uuid)
						.cp_id(vo.getCp_id())
						.build());
			} else {
				cpMapper.cp_FileModify(CpUploadVO.builder()
												  .filename(filename)
												  .filepath(filepath)
												  .uuid(uuid)
												  .cp_id(vo.getCp_id())
												  .build());
			}
							
				  System.out.println("서비스통과");
				}
				
				
		return cpMapper.cp_mypage_change(vo);
	}

	@Override
	public CpExVO cp_content_ex_detail(CpExVO vo) {
		return cpMapper.cp_content_ex_detail(vo);
	}

	@Override
	public ArrayList<CpUploadVO> getFullFileList() {
		
		return cpMapper.getFullFileList();
	}

	@Override
	public int user_consultant_regist_form(ConsultantVO vo) {
		return cpMapper.user_consultant_regist_form(vo);
	}

	@Override
	public ArrayList<ConsultantVO> cp_getConsultantList(String cp_id) {
		return cpMapper.cp_getConsultantList(cp_id);
	}

	@Override
	public int cp_consultant_accept(ConsultantVO vo) {
		return cpMapper.cp_consultant_accept(vo);
	}

	@Override
	public int cp_consultant_decline(ConsultantVO vo) {
		return cpMapper.cp_consultant_decline(vo);
	}

	@Override
	public CpExVO cp_content_ex_modify(CpExVO vo) {
		return cpMapper.cp_content_ex_modify(vo);
	}

	@Override
	public int cp_ex_change(CpExVO vo, List<MultipartFile> list) {
		
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
			
				
				cpMapper.cp_ex_FileModify(CpExUploadVO.builder()
						.filename(filename)
						.filepath(filepath)
						.uuid(uuid)
						.cp_ex_num(vo.getCp_ex_num())
						.build());
						
			
					
		  System.out.println("서비스통과");
		}
		
		
		return cpMapper.cp_ex_change(vo);
	}

	@Override
	public int cp_ex_count(String cp_id) {
		return cpMapper.cp_ex_count(cp_id);
	}

	@Override
	public ArrayList<CpExUploadVO> getFullExFileList() {
		return cpMapper.getFullExFileList();
	}















}
