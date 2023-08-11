package com.today.house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.today.house.service.ConsultantService;

@Controller
@RequestMapping("/consultant")
public class ConsultantController {

	@Autowired
	@Qualifier("consultantService")
	private ConsultantService consultantService;
	
	
	// 견적서 작성페이지 진입
	@GetMapping("/consultant")
	public String consultant() {
		
		return "consultant/consultant";
	}
	
	
	//견적서 리스트 
	@GetMapping("/consultant_list")
	public String consultant_list() {
		
		return "consultant/consultant_list";
	}
	
	
	//내용보기
	@GetMapping("/consultant_detail")
	public String consultant_detail() {
		
		return "consultant/consultant_detail";
	}
}
