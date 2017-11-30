package com.obs.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		List<Account> accounts=new ArrayList<>();
		Object obj=session.getAttribute("customer");
		if(obj instanceof Customer) {
			Customer customer=(Customer) obj;
			//System.out.println(customer.getCustomerId());
			accounts=ar.findByCustomer_CustomerId(customer.getCustomerId());
		}
		model.addAttribute("transferForm", new TransferForm());
		model.addAttribute("accounts", accounts);
		return "transfer";
	}
	
	@PostMapping("/confirm")
	public String confirmTransfer(@ModelAttribute TransferForm transferForm, Model model) {
		long fromId=Long.parseLong(transferForm.getFromAccountId());
		long toId=Long.parseLong(transferForm.getToAccountId());
		Account from=ar.findOne(fromId);
		Account to=ar.findOne(toId);
		model.addAttribute("confirmTransfer", new TransferForm());
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return "confirm-transfer";
	}
	
	@PostMapping("/process")
	public String transfer(@ModelAttribute TransferForm confirmTransfer, Model model) {
		System.out.println(confirmTransfer.getAmount());
		long fromId=Long.parseLong(confirmTransfer.getFromAccountId());
		long toId=Long.parseLong(confirmTransfer.getToAccountId());
		Account from=ar.findOne(fromId);
		Account to=ar.findOne(toId);
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("confirmTransfer", confirmTransfer);
		double amount=Double.parseDouble(confirmTransfer.getAmount());
		String frequency=confirmTransfer.getFrequency();
		if(from.getBalance()>=amount) {
			if(frequency.equals("oneTime")) {
				from.setBalance(from.getBalance()-amount);
				to.setBalance(to.getBalance()+amount);
				//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date d = new Date();
				//String today = sdf.format(d);
			}
			return "success-transfer";
		}else {
			model.addAttribute("error", "Not enough money");
			return "redirect:transfer";
		}
	}
}
