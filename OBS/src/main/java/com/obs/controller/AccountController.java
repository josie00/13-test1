package com.obs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	
	
	@GetMapping("/accounts")
	public String showAccount(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		System.out.println(c.getFirstName());
		List<Account> accounts =  ar.findByCustomer_CustomerId(c.getCustomerId());
		System.out.println(accounts.size());
		List<Account> activeAccounts = new ArrayList<>();
		for (Account account: accounts) {
			if ("active".equals(account.getStatus())) {
				activeAccounts.add(account);
			}
		}

		model.addAttribute("customer", c);
		model.addAttribute("accounts", activeAccounts);
		
		return "accounts";
	}
	
	@GetMapping("/account")
	public String getAccount(@RequestParam(value="accountId", defaultValue="0") String accountId) {
		System.out.println("accountId: " + accountId);
		long id = Long.parseLong(accountId.trim());
		Account account = ar.findOne(id);	
		System.out.println(account.getAccountNumber());
		return "checking";
	}
	
	
	
	
}
