package com.obs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.obs.databean.Account;
import com.obs.databean.Customer;
import com.obs.formbean.TransferForm;
import com.obs.repository.AccountRepository;

@Controller
public class TransferController {
	@Autowired
	AccountRepository ar;
	
	@PersistenceContext
	private EntityManager em;
	
	@GetMapping("/transfer")
	public String transferPage(Model model, HttpSession session) {
		model.addAttribute("transferForm", new TransferForm());
		List<Account> accounts=new ArrayList<>();
		Object obj=session.getAttribute("customer");
		if(obj instanceof Customer) {
			Customer customer=(Customer) obj;
			//System.out.println(customer.getCustomerId());
			accounts=ar.findByCustomer_CustomerId(customer.getCustomerId());
		}
		model.addAttribute("accounts", accounts);
		return "transfer";
	}
	
	@PostMapping("/confirm")
	public String confirmTransfer(@ModelAttribute TransferForm transferForm, Model model) {
		Account from=em.find(Account.class, Long.parseLong(transferForm.getFromAccountId()));
		Account to=em.find(Account.class, Long.parseLong(transferForm.getToAccountId()));
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return "confirm-transfer";
	}
	
	@PostMapping("/process")
	public String transfer(@ModelAttribute TransferForm transferForm) {
/*		System.out.println(transferForm.getFromAccountId());
		Account from=em.find(Account.class, Long.parseLong(transferForm.getFromAccountId()));
		Account to=em.find(Account.class, Long.parseLong(transferForm.getToAccountId()));
		double amount=Double.parseDouble(transferForm.getAmount());
		em.getTransaction().begin();
		from.setBalance(from.getBalance()-amount);
		to.setBalance(to.getBalance()+amount);
		em.getTransaction().commit();*/
		return "success-transfer";
	}
}
