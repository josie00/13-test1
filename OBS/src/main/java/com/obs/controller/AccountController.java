package com.obs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.obs.databean.Account;
import com.obs.databean.Customer;
import com.obs.databean.Transaction;
import com.obs.repository.AccountRepository;
import com.obs.repository.CustomerRepository;
import com.obs.repository.TransactionRepository;

@Controller
public class AccountController {
	@Autowired
	CustomerRepository cr;
	
	@Autowired
	AccountRepository ar;
	
	@Autowired
	TransactionRepository tr;

	//Get all accounts for current user
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
	
	//Restful Controller Example: Get all transactions based on accountId
	//eg. Request uri should be /account?accountId=1
	@GetMapping("/account")
	public String getAccount(@RequestParam(value="accountId", defaultValue="0") String accountId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		
		long id = Long.parseLong(accountId.trim());
		Account account = ar.findOne(id);
		
		List<Transaction> transactions = tr.findByAccount_AccountId(id);
		transactions.sort((t1,t2) -> t2.getTimeStamp().compareTo(t1.getTimeStamp()));
		model.addAttribute("transactions", transactions);
		model.addAttribute("customer", c);
		model.addAttribute("account", account);
	
		return "transaction";
	}
	
}
