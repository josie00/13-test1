package com.obs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.obs.repository.TransactionRepository;

@Controller
public class TransactionController {
	@Autowired
	TransactionRepository tr;
	
//	
//	@GetMapping("accounts/{accountId}/transactions")
//	public List<Transaction> getTransactions(@PathVariable String accountId) {
//		long id = Long.parseLong(accountId.trim());
//		return tr.findByAccount_AccountId(id);
//	}
	
}
