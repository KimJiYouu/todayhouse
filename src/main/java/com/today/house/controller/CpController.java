package com.today.house.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.today.house.command.ConsultantVO;
import com.today.house.command.CpExUploadVO;
import com.today.house.command.CpExVO;
import com.today.house.command.CpUploadVO;
import com.today.house.command.CpVO;
import com.today.house.cp.service.CpService;
import com.today.house.util.CpCriteria;
import com.today.house.util.CpPageVO;

@Controller
@RequestMapping("/company")
public class CpController {

	@Autowired
	@Qualifier("cpService")
	private CpService cpSercvice;

	// 업로드경로
	@Value("${project.upload.path}") // application.properties의 값 가져오기
	private String uploadPath;
	


	// 시공사 리스트
	@GetMapping("/cp_list")
	public String cp_list(@RequestParam("cp_major") String cp_major, Model model, CpCriteria cri) {
		ArrayList<CpVO> list = cpSercvice.cp_getList(cp_major, cri);
		CpPageVO cpPageVO = new CpPageVO(cri, cpSercvice.getTotal(cp_major, cri));

		ArrayList<CpUploadVO> list_file = cpSercvice.getFullFileList();
		// ArrayList<String> fileList = new ArrayList<>();
		Map<String, String> fileList = new HashMap<>();

		for (CpUploadVO allFile : list_file) {
			String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_"
					+ allFile.getFilename();
			File file = new File(path);

			try {
				String base64Encoded = Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
				String dataUri = "data:image/jpeg;base64," + base64Encoded;
				fileList.put(allFile.getCp_id(), dataUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		model.addAttribute("fileList", fileList);
		model.addAttribute("cp_major", cp_major);
		model.addAttribute("list", list);
		model.addAttribute("cpPageVO", cpPageVO);
		return "company/company-list";
	}

	// 시공사 상세정보
	@GetMapping("/cp_content_info")
	public String cp_content_info(@RequestParam("cp_id") String cp_id, Model model) {

		ArrayList<CpUploadVO> list_file = cpSercvice.getFullFileList();
		// ArrayList<String> fileList = new ArrayList<>();
		Map<String, String> fileList = new HashMap<>();
		for (CpUploadVO allFile : list_file) {
			String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_"
					+ allFile.getFilename();
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
		model.addAttribute("cp_count", cpSercvice.cp_ex_count(cp_id));
		model.addAttribute("vo", cpSercvice.cp_getdetail(cp_id));
		return "company/company-content-info";
	}

	// 시공사 사례 리스트<완료>
	@GetMapping("/cp_content_ex")
	public String cp_content_ex(@RequestParam("cp_id") String cp_id, Model model) {

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
				// System.out.println(allFile.getCp_id());
				fileList2.put(allFile2.getCp_ex_num(), dataUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		ArrayList<CpUploadVO> list_file = cpSercvice.getFullFileList();
		// ArrayList<String> fileList = new ArrayList<>();
		Map<String, String> fileList = new HashMap<>();
		for (CpUploadVO allFile : list_file) {
			String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_"
					+ allFile.getFilename();
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

		model.addAttribute("fileList2", fileList2);
		model.addAttribute("cp_count", cpSercvice.cp_ex_count(cp_id));
		model.addAttribute("vo", cpSercvice.cp_getdetail(cp_id));
		model.addAttribute("exlist", cpSercvice.cp_getExdetail(cp_id));
		return "company/company-content-ex";
	}

	// 시공사 사례 상세
	@GetMapping("/cp_content_ex_detail")
	public String cp_content_ex_detail(CpExVO vo, Model model) {
		
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
		
		model.addAttribute("vo", cpSercvice.cp_content_ex_detail(vo));
		return "company/company-content-ex-detail";
	}

	// 사용자 상담신청
	@GetMapping("user_consultant_regist")
	public String user_consultant_regist(@RequestParam("user_id") String user_id, @RequestParam("cp_id") String cp_id,
			Model model) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String formattedDate = dateFormat.format(today);

		model.addAttribute("today", formattedDate);
		model.addAttribute("user_id", user_id);
		model.addAttribute("cp_id", cp_id);
		return "user-consultant-regist";
	}

	// 사용자 상담신청
	@PostMapping("/user_consultant_regist_form")
	public String user_consultant_regist_form(ConsultantVO vo, RedirectAttributes ra) {
		int result = cpSercvice.user_consultant_regist_form(vo);
		if (result == 1) {
			ra.addFlashAttribute("msg", "신청에 성공하였습니다.");
			return "redirect:/user_consultant_view?user_id=" + vo.getUser_id();
		} else {
			ra.addFlashAttribute("msg", "신청에 실패하였습니다.");
			return "redirect:/company/cp_content_info?cp_id=" + vo.getCp_id();
		}
	}

}
