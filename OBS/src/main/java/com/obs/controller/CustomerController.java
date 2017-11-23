package com.obs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.obs.formbean.LoginForm;
import com.obs.formbean.RegisterForm;
import com.obs.repository.CustomerRepository;



@Controller
public class CustomerController {
	@Autowired
	CustomerRepository cr;
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("login", new LoginForm());
		return "index";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute LoginForm loginForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		return "index";
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("customer", new LoginForm());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerCustomer(@ModelAttribute RegisterForm registerForm) {
		
		return "register";
	}

}
