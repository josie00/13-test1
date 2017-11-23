package com.obs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.obs.databean.Customer;
import com.obs.formbean.LoginForm;
import com.obs.formbean.RegisterForm;
import com.obs.repository.CustomerRepository;

@Controller
public class CustomerController {
	@Autowired
	CustomerRepository cr;

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "index";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute LoginForm loginForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println("login");
		System.out.println(loginForm.getUserName());
		System.out.println(loginForm.getPassword());
		List<Customer> customers = cr.findByUserName(loginForm.getUserName());
		Customer c = customers.get(0);
		if (c != null) {
			System.out.println(c.getUserName());
			if (loginForm.getPassword().equals(c.getPassword())) {
				 session.setAttribute("customer", c);
				return "loginConfirmation";
			} else {
				return "home";
			}
		}

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
	
	@GetMapping("/loginConfirmation")
	public String loginConfirmation(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		System.out.println("login confirmation");
		System.out.println(c.getEmail());
		return "login-confirmation";
	}

}
