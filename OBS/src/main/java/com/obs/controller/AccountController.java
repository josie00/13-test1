package com.obs.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.obs.databean.Account;
import com.obs.databean.Customer;
import com.obs.repository.AccountRepository;
import com.obs.repository.CustomerRepository;

@Controller
public class AccountController {
	@Autowired
	CustomerRepository cr;
	
	@Autowired
	AccountRepository ar;
	
	
	
	@GetMapping("/account")
	public String showAccount(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");

		Set<Account> accounts = c.getAccounts();
		for (Account account: accounts) {
			if ("inactive".equals(account.getStatus())) {
				accounts.remove(account);
			}
		}

		model.addAttribute("customer", c);
		model.addAttribute("accounts", accounts);
		
		return "accounts";
	}
	
	@GetMapping("accounts/{accountId}")
	public Account getAccount(@PathVariable String accountId) {
		long id = Long.parseLong(accountId.trim());
		Account account = ar.findOne(id);	
		return account;
	}
	
	
}
