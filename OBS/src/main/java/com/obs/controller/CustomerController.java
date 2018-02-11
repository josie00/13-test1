package com.obs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String login(@Valid LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
            return "index";
        }
		HttpSession session = request.getSession();
		List<Customer> customers = cr.findByUserName(loginForm.getUserName());
/*		if (customers == null) {
			bindingResult.addError(new ObjectError("userNameError", "Username is incorrect."));
			return "index";
		}*/ 
		if (customers.size() != 0) {
			Customer c = customers.get(0);
			System.out.println(c.getUserName());
			if (loginForm.getPassword().equals(c.getPassword())) {
				session.setAttribute("customer", c);
				return "redirect:loginConfirmation";
			} else {
				bindingResult.addError(new ObjectError("passwordError", "Password is incorrect."));
				return "index";
			}
		} else {
			bindingResult.addError(new ObjectError("userNameError", "Username is incorrect."));
			return "index";
		}
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		return "register";
	}

	@PostMapping("/register")
	public String registerCustomer(@Valid RegisterForm registerForm,BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors()) {
            return "register";
        }
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
		Customer newCustomer=cr.save(customer);
		if(newCustomer==null) {
			throw new IllegalArgumentException();
		}
		session.setAttribute("customer", customer);
		return "redirect:accounts";
	}
	
	@GetMapping("/loginConfirmation")
	public String loginConfirmation(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		
		return "login-confirmation";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.setAttribute("customer", null);
		return "redirect:home";
	}
	
	@GetMapping("/personal-info")
	public String personal(Model model, HttpSession session) {
		Customer c = (Customer) session.getAttribute("customer");
		model.addAttribute("customer", c);
		return "personal-info";
	}
	

}
