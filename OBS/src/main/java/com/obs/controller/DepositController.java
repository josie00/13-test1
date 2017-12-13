package com.obs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.obs.databean.Account;
import com.obs.databean.Customer;
import com.obs.databean.RecurringPayment;
import com.obs.databean.Transaction;
import com.obs.databean.TransactionType;
import com.obs.formbean.DepositForm;
import com.obs.formbean.TransferForm;
import com.obs.repository.AccountRepository;
import com.obs.repository.TransactionRepository;
import com.obs.repository.TransactionTypeRepository;

@Controller
public class DepositController {

	@Autowired
	AccountRepository ar;

	@Autowired
	TransactionTypeRepository ttr;

	@Autowired
	TransactionRepository tr;
	
	@GetMapping("/deposit")
	public String depositView(Model model, HttpSession session) {
		Customer customer = (Customer) session.getAttribute("customer");
		model.addAttribute("customer", customer);
		List<Account> accounts = ar.findByCustomer_CustomerId(customer.getCustomerId());
		List<Account> activeAccounts = new ArrayList<>();
		for (Account account: accounts) {
			if ("active".equals(account.getStatus())) {
				activeAccounts.add(account);
			}
		}
		model.addAttribute("depositForm", new DepositForm());
		model.addAttribute("accounts", activeAccounts);
		return "deposit";
	}
	@PostMapping("/depositProcess")
	public String depositProcess(@ModelAttribute DepositForm depositForm, Model model, HttpSession session) {
		Customer c = (Customer) session.getAttribute("customer");
		model.addAttribute("customer", c);
		long toId = Long.parseLong(depositForm.getToAccountId());
		double amount = Double.parseDouble(depositForm.getAmount());
		Account to = ar.findOne(toId);
		to.setBalance(to.getBalance() + amount);
		Date d = new Date();
		String description = "Deposit " + amount + " to " + to.getAccountNumber();
		TransactionType t = ttr.findByTransactionTypeName("Transfer").get(0);
		Transaction transaction = new Transaction(d, amount, to.getBalance(), t, to, null, description,
				"Clear");
		Transaction t1=tr.save(transaction);
		if(t1 == null) {
			throw new IllegalArgumentException();
		}
		return "success-deposit";
	}
}
