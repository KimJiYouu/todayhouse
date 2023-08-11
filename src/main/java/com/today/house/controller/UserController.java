package com.today.house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.today.house.command.UserVO;
import com.today.house.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {


	@Autowired
	@Qualifier("userService")
	private UserService userService;

}
