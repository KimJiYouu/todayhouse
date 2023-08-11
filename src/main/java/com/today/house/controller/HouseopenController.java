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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.today.house.command.HouseopenUploadVO;
import com.today.house.command.HouseopenVO;
import com.today.house.houseopen.service.HouseopenService;

@Controller
@RequestMapping("/houseopen")
public class HouseopenController {
	@Autowired
	@Qualifier("houseOpenService")
	private HouseopenService houseOpenService;

	// 업로드경로
	@Value("${project.upload.path}") // application.properties의 값 가져오기
	private String uploadPath;

	@GetMapping("/houseopen") // 리스트화면
	public String houseopen(Model model, HttpSession session) {

		ArrayList<HouseopenVO> list = houseOpenService.getHouseopenList();
		ArrayList<HouseopenUploadVO> list_file = houseOpenService.getFullFileList();
		// ArrayList<String> fileList = new ArrayList<>();
		Map<Integer, String> fileList = new HashMap<>();
		for (HouseopenUploadVO allFile : list_file) {
			String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_"
					+ allFile.getFilename();
			File file = new File(path);

			try {
				String base64Encoded = Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
				String dataUri = "data:image/jpeg;base64," + base64Encoded;
				// fileList.add(dataUri);
				fileList.put(allFile.getHouse_num(), dataUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		model.addAttribute("list", list);
		model.addAttribute("fileList", fileList);
		model.addAttribute("session", session.getAttribute("username"));

		System.out.println("세샨" + session.getAttribute("username"));
		return "houseopen/houseopen";
	}

	@GetMapping("/houseopenList")
	public String houseopenList(Model model, @RequestParam("house_type") String house_type,
			@RequestParam("house_size") String house_size, @RequestParam("house_adr") String house_adr,
			@RequestParam("house_fam") String house_fam, @RequestParam("house_remo") String house_remo) {

		model.addAttribute("house_type", house_type);
		model.addAttribute("house_size", house_size);
		model.addAttribute("house_adr", house_adr);
		model.addAttribute("house_fam", house_fam);
		model.addAttribute("house_remo", house_remo);

		HouseopenVO vo = HouseopenVO.builder().house_type(house_type).house_size(house_size).house_adr(house_adr)
				.house_fam(house_fam).house_remo(house_remo).build();
		ArrayList<HouseopenVO> list = houseOpenService.getFilterList(vo);
		model.addAttribute("list", list);

		ArrayList<HouseopenUploadVO> list_file = houseOpenService.getFullFileList();
		Map<Integer, String> fileList = new HashMap<>();
		for (HouseopenUploadVO allFile : list_file) {
			String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_"
					+ allFile.getFilename();
			File file = new File(path);

			try {
				String base64Encoded = Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
				String dataUri = "data:image/jpeg;base64," + base64Encoded;
				// fileList.add(dataUri);
				fileList.put(allFile.getHouse_num(), dataUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		model.addAttribute("fileList", fileList);

		return "houseopen/houseopen";
	}

	@GetMapping("/houseopenWrite") // 등록화면이동
	public String houseopenWrite(HttpSession session, RedirectAttributes ra) {

		
		 if(session.getAttribute("username") != null || session.getAttribute("cpname") != null) { return
		  "houseopen/houseopenWrite";
		  
		  } else { ra.addFlashAttribute("msg", "로그인 후 이용해주세요"); return
		  "redirect:/houseopen/houseopen"; }
		 
	}

	@GetMapping("/houseopenContent") // 글상세화면
	public String houseopenContent(@RequestParam("house_num") Integer house_num, Model model, HttpSession session) {
		HouseopenVO vo = houseOpenService.getContent(house_num);
		model.addAttribute("vo", vo);

		ArrayList<HouseopenUploadVO> list_file = houseOpenService.getFullFileList();
		// ArrayList<String> fileList = new ArrayList<>();
		Map<Integer, String> fileList = new HashMap<>();
		for (HouseopenUploadVO allFile : list_file) {
			String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_"
					+ allFile.getFilename();
			File file = new File(path);

			try {
				String base64Encoded = Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
				String dataUri = "data:image/jpeg;base64," + base64Encoded;
				// fileList.add(dataUri);
				fileList.put(allFile.getHouse_num(), dataUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		model.addAttribute("fileList", fileList);
		model.addAttribute("session", (String) session.getAttribute("username"));
		System.out.println(session.getAttribute("username"));

		return "houseopen/houseopenContent";
	}

	///////////////////////////////////////
	// 글등록하기//
	@PostMapping("/houseopenWrite_form")
	public String houseopenWrite_form(HouseopenVO vo, RedirectAttributes ra,
			@RequestParam("file") List<MultipartFile> list, HttpSession session) {
		vo.setUser_id((String) session.getAttribute("username"));

		// 사진처리
		list = list.stream().filter(t -> t.isEmpty() == false).collect(Collectors.toList());
		for (MultipartFile file : list) {
			if (file.getContentType().contains("image") == false) {
				ra.addFlashAttribute("msg", "jpg, jpeg, png 이미지 파일만 등록이 가능합니다.");
				return "redirect:/houseopen/houseopen";
			}
		}

		int result = houseOpenService.houseopenWrite(vo, list);
		String msg = result == 1 ? "등록되었습니다" : "등록실패";
		ra.addFlashAttribute("msg", msg);

		return "redirect:/houseopen/houseopen";
	}

	@GetMapping("/houseopenDelete")
	public String houseopenDelete(@RequestParam("house_num") Integer house_num) {
		houseOpenService.houseopenDelete(house_num);
		return "redirect:/houseopen/houseopen";
	}

	////////////// 글 수정 화면 이동/////////////
	@GetMapping("/houseopenModify")
	public String houseopenModify(@RequestParam("house_num") Integer house_num, Model model) {

		HouseopenVO vo = houseOpenService.getContent(house_num);
		model.addAttribute("vo", vo);

		ArrayList<HouseopenUploadVO> list_file = houseOpenService.getFullFileList();
		// ArrayList<String> fileList = new ArrayList<>();
		Map<Integer, String> fileList = new HashMap<>();
		for (HouseopenUploadVO allFile : list_file) {
			String path = uploadPath + "/" + allFile.getFilepath() + "/" + allFile.getUuid() + "_"
					+ allFile.getFilename();
			File file = new File(path);

			try {
				String base64Encoded = Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file));
				String dataUri = "data:image/jpeg;base64," + base64Encoded;
				// fileList.add(dataUri);
				fileList.put(allFile.getHouse_num(), dataUri);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		model.addAttribute("fileList", fileList);

		return "houseopen/houseopenModify";
	}

	/////////// 글 수정하기/////////////
	@PostMapping("/houseopenModify_form")
	public String houseopenModify_form(HouseopenVO vo, RedirectAttributes ra,
			@RequestParam("file") List<MultipartFile> list) {

		// 사진처리
		list = list.stream().filter(t -> t.isEmpty() == false).collect(Collectors.toList());
		for (MultipartFile file : list) {
			if (file.getContentType().contains("image") == false) {
				ra.addFlashAttribute("msg", "jpg, jpeg, png 이미지 파일만 등록이 가능합니다.");
				return "redirect:/houseopen/houseopen";
			}
		}

		int result = houseOpenService.houseopenModify(vo, list);
		String msg = result == 1 ? "수정되었습니다" : "등록실패";
		ra.addFlashAttribute("msg", msg);
		ra.addAttribute("house_num", vo.getHouse_num());

		return "redirect:/houseopen/houseopenContent";
	}

}
