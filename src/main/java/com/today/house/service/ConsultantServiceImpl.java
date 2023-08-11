package com.today.house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("consultantService")
public class ConsultantServiceImpl implements ConsultantService {

	@Autowired
	private ConsultantMapper consultantMapper;
	
	
	
	
}
