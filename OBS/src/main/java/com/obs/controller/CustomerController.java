package com.obs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.obs.databean.Account;
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
		List<Customer> customers = cr.findByUserName(loginForm.getUserName());
		Customer c = customers.get(0);
		if (c != null) {
			System.out.println(c.getUserName());
			if (loginForm.getPassword().equals(c.getPassword())) {
				 session.setAttribute("customer", c);
				 return "redirect:loginConfirmation";
			} else {
				return "home";
			}
		}

		return "index";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		return "register";
	}

	@PostMapping("/register")
	public String registerCustomer(@ModelAttribute RegisterForm registerForm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		String dstr=registerForm.getDateOfBirth();  
		Date dateOfBirth=null;
		try {
			dateOfBirth=sdf.parse(dstr);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		Customer customer=new Customer(registerForm.getUserName(), registerForm.getEmail(), registerForm.getPassword(),
				                       registerForm.getFirstName(), registerForm.getLastName(),registerForm.getStreet(),
				                       registerForm.getCity(), registerForm.getState(), registerForm.getZip(),registerForm.getPhone(),
				                       dateOfBirth, registerForm.getSsn(),registerForm.getDriverLicense(), null);
		cr.save(customer);
		return "register";
	}
	
	@GetMapping("/loginConfirmation")
	public String loginConfirmation(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		
		return "login-confirmation";
	}

}
