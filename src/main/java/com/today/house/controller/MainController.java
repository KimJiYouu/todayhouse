package com.today.house.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.today.house.command.ConsultantVO;
import com.today.house.command.CpExUploadVO;
import com.today.house.command.CpExVO;
import com.today.house.command.CpUploadVO;
import com.today.house.command.CpVO;
import com.today.house.command.HouseopenUploadVO;
import com.today.house.command.UserVO;
import com.today.house.cp.service.CpService;
import com.today.house.user.service.UserService;

@Controller
public class MainController {

	@Autowired
	@Qualifier("cpService")
	private CpService cpSercvice;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	//업로드경로
	@Value("${project.upload.path}") //application.properties의 값 가져오기
	private String uploadPath;


	//메인화면
	@RequestMapping("/")
	public String main() {
		return "index";
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//유저 로그인시
	@PostMapping("/user_login_form")  
	public String cp_login_form(UserVO vo, RedirectAttributes ra, HttpSession session, Model model) {
		UserVO result = userService.user_login_form(vo);
		if(result != null) {
			ra.addFlashAttribute("msg", "로그인에 성공하였습니다.");
			session.setAttribute("username", result.getUser_id());
			model.addAttribute("username", result.getUser_id());
		} else {
			ra.addFlashAttribute("msg", "로그인에 실패하였습니다.");
		}
		return "redirect:/";
	}


	//유저 회원가입시
	@PostMapping("/user_regist_form")
	public String user_regist_form (@Valid UserVO vo, BindingResult bindingResult, RedirectAttributes ra, Model model) {
		if (bindingResult.hasErrors()) {
			List<FieldError> list = bindingResult.getFieldErrors();
			for (FieldError err : list) {
				if (err.isBindingFailure()) {
					ra.addFlashAttribute("valid_" + err.getField(), "잘못된 값 입력입니다.");
				} else {
					ra.addFlashAttribute("valid_" + err.getField(), err.getDefaultMessage());
				}
			}
			return "redirect:/"; 
		}

		int result = userService.user_regist_form(vo);
		if(result == 1) {
			ra.addFlashAttribute("msg", "회원가입에 성공하였습니다.");
		} else {
			ra.addFlashAttribute("msg", "회원가입에 실패하였습니다.");
		}
		return "redirect:/";
	}

	//사용자 마이페이지 비밀번호확인 화면
	@GetMapping("/user_mypage_check")
	public String user_mypage_check () {
		return "user-mypage-check";			
	}

	//사용자 마이페이지 비밀번호확인
	@PostMapping("/user_mypage_check_form")
	public String user_mypage_check_form (UserVO vo, Model model, RedirectAttributes ra, HttpSession session) {
		UserVO result = userService.user_mypage_check_form(vo);
		if(result != null) {
			session.setAttribute("pwcheck", "check");
			model.addAttribute("vo", result);
			return "user-mypage";			
		} else {
			ra.addFlashAttribute("msg", "비밀번호가 다릅니다.");
			return "redirect:/user_mypage_check";
		}
	}

	//사용자 마이페이지 정보변경
	@PostMapping("/user_mypage_change")
	public String user_mypage_change (UserVO vo, Model model, RedirectAttributes ra) {
		int result = userService.user_mypage_change(vo);
		if(result == 1) {
			model.addAttribute("vo", userService.user_login_form(vo));
			ra.addFlashAttribute("msg", "정보변경에 성공하였습니다.");
		} else {
			ra.addFlashAttribute("msg", "정보변경에 실패하였습니다.");
		}
		return "user-mypage";
	}

	//사용자 상담목록 리스트
	@GetMapping("/user_consultant_view")
	public String user_consultant_view (@RequestParam("user_id") String user_id, Model model) {
		ArrayList<ConsultantVO> list = userService.user_consultant_list(user_id);
		model.addAttribute("list", list);
		return "user-consultant";
	}

	//사용자 상담목록 상세내용
	@GetMapping("user_consultant_info")
	public String user_consultant_info (@RequestParam("user_id") String user_id,
			@RequestParam("consultant_num") String consultant_num,
			Model model) {
		model.addAttribute("vo", userService.user_consultant_info(user_id, Integer.parseInt(consultant_num)));
		return "user_consultant_info";
	}

	//사용자 로그아웃
	@GetMapping("user_logout")
	public String user_logout (HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//시공사 로그인시
	@PostMapping("/cp_login_form")
	public String cp_login_form(CpVO vo, RedirectAttributes ra, HttpSession session, Model model) {
		CpVO result = cpSercvice.cp_login_form(vo);
		if(result != null) {
			ra.addFlashAttribute("msg", "로그인에 성공하였습니다.");
			session.setAttribute("cpname", result.getCp_id());
			model.addAttribute("cpname", result.getCp_id());
		} else {
			ra.addFlashAttribute("msg", "로그인에 실패하였습니다.");
		}
		return "redirect:/";
	}

	//시공사 회원가입시
	@PostMapping("/cp_regist_form")
	public String cp_regist_form(@Valid CpVO vo, BindingResult bindingResult, RedirectAttributes ra, Model model) {
		if (bindingResult.hasErrors()) {
			List<FieldError> list = bindingResult.getFieldErrors();
			for (FieldError err : list) {
				if (err.isBindingFailure()) {
					ra.addFlashAttribute("valid_" + err.getField(), "잘못된 값 입력입니다.");
				} else {
					ra.addFlashAttribute("valid_" + err.getField(), err.getDefaultMessage());
				}
			}
			return "redirect:/"; 
		}

		int result = cpSercvice.cp_regist_form(vo);
		if(result == 1) {
			ra.addFlashAttribute("msg", "회원가입에 성공하였습니다.");
		} else {
			ra.addFlashAttribute("msg", "회원가입에 실패하였습니다");
		}
		return "redirect:/";
	}

	//시공사 마이페이지 비밀번호확인 화면
	@GetMapping("/cp_mypage_check")
	public String cp_mypage_check () {

		return "company-mypage-check";			
	}

	//시공사 마이페이지 비밀번호확인
	@PostMapping("/cp_mypage_check_form")
	public String cp_mypage_check_form (CpVO vo, Model model, RedirectAttributes ra, HttpSession session) {
		CpVO result = cpSercvice.cp_mypage_check_form(vo);
		if(result != null) {

			ArrayList<CpUploadVO> list_file = cpSercvice.getFullFileList();
			//ArrayList<String> fileList = new ArrayList<>();
			Map<String, String> fileList = new HashMap<>();

			for(CpUploadVO allFile : list_file) {
				String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_" + allFile.getFilename();
				File file = new File(path);

				try {
					String base64Encoded = Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
					String dataUri = "data:image/jpeg;base64," + base64Encoded;
					fileList.put(allFile.getCp_id(), dataUri);
					System.out.println(fileList.get(allFile.getCp_id()));

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			model.addAttribute("fileList", fileList);

			model.addAttribute("cp_count", cpSercvice.cp_ex_count(vo.getCp_id()));
			session.setAttribute("pwcheck", "check");
			model.addAttribute("vo", result);
			return "company-mypage";			
		} else {
			ra.addFlashAttribute("msg", "비밀번호가 다릅니다.");
			return "redirect:/cp_mypage_check";
		}
	}


	//시공사 마이페이지 정보변경
	@PostMapping("/cp_mypage_change")
	public String cp_mypage_change (CpVO vo, Model model, RedirectAttributes ra, @RequestParam("file") List<MultipartFile> list) {

		//사진처리
		list = list.stream().filter(t -> t.isEmpty() == false).collect(Collectors.toList());
		for(MultipartFile file : list) {
			if(file.getContentType().contains("image") == false) {
				ra.addFlashAttribute("msg", "jpg, jpeg, png 이미지 파일만 등록이 가능합니다.");
				model.addAttribute("vo", cpSercvice.cp_login_form(vo));
				return "redirect:/company-mypage";
			}
		}

		ArrayList<CpUploadVO> list_file = cpSercvice.getFullFileList();
		//ArrayList<String> fileList = new ArrayList<>();
		Map<String, String> fileList = new HashMap<>();
		for(CpUploadVO allFile : list_file) {
			String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_" + allFile.getFilename();
			File file = new File(path);

			try {
				String base64Encoded = Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
				String dataUri = "data:image/jpeg;base64," + base64Encoded;
				System.out.println(allFile.getCp_id());
				fileList.put(allFile.getCp_id(), dataUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		model.addAttribute("fileList", fileList);

		int result = cpSercvice.cp_mypage_change(vo, list);
		if(result == 1) {
			model.addAttribute("vo", cpSercvice.cp_login_form(vo));
			ra.addFlashAttribute("msg", "정보변경에 성공하였습니다.");
		} else {
			ra.addFlashAttribute("msg", "정보변경에 실패하였습니다.");
		}
		return "redirect:/company_mypage?cp_id=" + vo.getCp_id() + "&cp_pw=" + vo.getCp_pw();
	}

	//
	@GetMapping("/company_mypage")
	public String company_mypage(CpVO vo, Model model) {
		CpVO result = cpSercvice.cp_mypage_check_form(vo);
		ArrayList<CpUploadVO> list_file = cpSercvice.getFullFileList();
		//ArrayList<String> fileList = new ArrayList<>();
		Map<String, String> fileList = new HashMap<>();
		for(CpUploadVO allFile : list_file) {
			String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_" + allFile.getFilename();
			File file = new File(path);

			try {
				String base64Encoded = Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
				String dataUri = "data:image/jpeg;base64," + base64Encoded;
				System.out.println(allFile.getCp_id());
				fileList.put(allFile.getCp_id(), dataUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		model.addAttribute("cp_count", cpSercvice.cp_ex_count(vo.getCp_id()));
		model.addAttribute("vo", result);
		model.addAttribute("fileList", fileList);
		return "company-mypage";
	}





	//시공사 사례 등록 화면
	@GetMapping("/cp_content_ex_regist_view")
	public String cp_content_ex_regist_view () {
		return "company-content-ex-regist";
	}

	//시공사 사례 등록
	@PostMapping("/cp_content_ex_regist")
	public String cp_content_ex_regist (CpExVO vo, RedirectAttributes ra, @RequestParam("file") List<MultipartFile> list) {


		//사진처리
		list = list.stream().filter(t -> t.isEmpty() == false).collect(Collectors.toList());
		for(MultipartFile file : list) {
			if(file.getContentType().contains("image") == false) {
				ra.addFlashAttribute("msg", "jpg, jpeg, png 이미지 파일만 등록이 가능합니다.");
				return "redirect:/cp_content_ex_regist_view";
			}
		}


		int result = cpSercvice.cp_content_ex_regist(vo, list);
		if(result == 1) {
			ra.addFlashAttribute("msg", "등록에 성공하였습니다.");
		} else {
			ra.addFlashAttribute("msg", "등록에 실패하였습니다.");
		}
		return "redirect:/cp_content_ex_list_view?cp_id=" + vo.getCp_id();
	}

	//시공종류 선택
	@GetMapping("/cp_choice")
	public String cp_choice() {
		return "company-choice";
	}

	//시공사 로그아웃
	@GetMapping("/cp_logout")
	public String cp_logout (HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	//시공사 my 사례 리스트
	@GetMapping("cp_content_ex_list_view")
	public String cp_content_ex_list_view (@RequestParam("cp_id") String cp_id, Model model) {
		model.addAttribute("list", cpSercvice.cp_getExdetail(cp_id));
		return "company-content-ex-list";
	}

	//시공사 my 사례 수정화면
	@GetMapping("cp_content_ex_modify")
	public String cp_content_ex_modify (CpExVO vo, Model model) {

		ArrayList<CpExUploadVO> list_file2 = cpSercvice.getFullExFileList();
		// ArrayList<String> fileList = new ArrayList<>();
		Map<Integer, String> fileList2 = new HashMap<>();
		for (CpExUploadVO allFile2 : list_file2) {
			String path = uploadPath + "/" + allFile2.getFilepath() + "/" + allFile2.getUuid() + "_"
					+ allFile2.getFilename();
			File file = new File(path);

			try {
				String base64Encoded = Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
				String dataUri = "data:image/jpeg;base64," + base64Encoded;
				fileList2.put(allFile2.getCp_ex_num(), dataUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		model.addAttribute("fileList2", fileList2);
		model.addAttribute("vo", cpSercvice.cp_content_ex_modify(vo));
		return "company-content-ex-modify";
	}

	//시공사 my사례 수정 form
	@PostMapping("cp_ex_change")
	public String cp_ex_change (CpExVO vo, RedirectAttributes ra, @RequestParam("file") List<MultipartFile> list) {

		System.out.println(list.toString());
		//사진처리
		list = list.stream().filter(t -> t.isEmpty() == false).collect(Collectors.toList());
		for(MultipartFile file : list) {
			if(file.getContentType().contains("image") == false) {
				ra.addFlashAttribute("msg", "jpg, jpeg, png 이미지 파일만 등록이 가능합니다.");
				return "redirect:/company-mypage";
			}
		}


		int result = cpSercvice.cp_ex_change(vo, list);
		if(result == 1) {
			ra.addFlashAttribute("msg", "수정되었습니다.");
		} else {
			ra.addFlashAttribute("msg", "요청실패");
		}
		return "redirect:/cp_content_ex_list_view?cp_id=" + vo.getCp_id();
	}

	//시공사 my 신청 리스트
	@GetMapping("cp_consultant_list_view")
	public String cp_consultant_list_view (@RequestParam("cp_id") String cp_id, Model model) {
		model.addAttribute("list", cpSercvice.cp_getConsultantList(cp_id));
		return "company-consultant-list";
	}

	//시공사 신청리스트 승낙
	@GetMapping("cp_consultant_accept")
	public String cp_consultant_accept(ConsultantVO vo, Model model, RedirectAttributes ra) {
		int result = cpSercvice.cp_consultant_accept(vo);
		if(result == 1) {
			ra.addFlashAttribute("msg", "승낙하였습니다.");
		} else {
			ra.addFlashAttribute("msg", "요청실패");
		}
		return "redirect:/cp_consultant_list_view?cp_id=" + vo.getCp_id() ;
	}

	//시공사 신청리스트 거절
	@GetMapping("cp_consultant_decline")
	public String cp_consultant_decline(ConsultantVO vo, Model model, RedirectAttributes ra) {
		int result = cpSercvice.cp_consultant_decline(vo);
		if(result == 1) {
			ra.addFlashAttribute("msg", "거절하였습니다.");
		} else {
			ra.addFlashAttribute("msg", "요청실패");
		}
		return "redirect:/cp_consultant_list_view?cp_id=" + vo.getCp_id();
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////



}
