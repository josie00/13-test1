package com.obs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.obs.databean.Transaction;
import com.obs.repository.TransactionRepository;

@Controller
public class TransactionController {
	@Autowired
	TransactionRepository tr;
	
	@GetMapping("accounts/{accountId}/transactions")
	public List<Transaction> getTransactions(@PathVariable String accountId) {
		long id = Long.parseLong(accountId.trim());
		return tr.findByAccount_AccountId(id);
	}
	
}
