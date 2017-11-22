package com.obs.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.obs.databean.AccountType;
import com.obs.repository.AccountTypeRepository;


@Controller
public class AccountTypeController {	
	@Autowired
	AccountTypeRepository atp;
	
	@GetMapping("/api")
	public void test(Model model) {
		AccountType at = new AccountType("checking", 0.1);	
		atp.save(at);
		
	}
	
	
}