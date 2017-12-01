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
import com.obs.databean.Loan;
import com.obs.databean.RecurringPayment;
import com.obs.databean.Transaction;
import com.obs.databean.TransactionType;
import com.obs.formbean.TransferForm;
import com.obs.repository.AccountRepository;
import com.obs.repository.RecurPaymentRepository;
import com.obs.repository.TransactionRepository;
import com.obs.repository.TransactionTypeRepository;

@Controller
public class TransferController {
	@Autowired
	AccountRepository ar;

	@Autowired
	TransactionTypeRepository ttr;

	@Autowired
	TransactionRepository tr;

	@Autowired
	RecurPaymentRepository rpr;

	// @PersistenceContext
	// private EntityManager em;

	@GetMapping("/transfer")
	public String transferPage(Model model, HttpSession session) {
		List<Account> accounts = new ArrayList<>();
		List<RecurringPayment> recurringPayments = new ArrayList<>();
		Object obj = session.getAttribute("customer");
		if (obj instanceof Customer) {
			Customer customer = (Customer) obj;
			// System.out.println(customer.getCustomerId());
			accounts = ar.findByCustomer_CustomerId(customer.getCustomerId());
			recurringPayments = rpr.findByCustomer_CustomerId(customer.getCustomerId());
			model.addAttribute("customer", customer);
		}
		model.addAttribute("transferForm", new TransferForm());
		model.addAttribute("accounts", accounts);
		model.addAttribute("recurringPayments", recurringPayments);
		return "transfer";
	}

	@PostMapping("/confirm")
	public String confirmTransfer(@ModelAttribute TransferForm transferForm, Model model, HttpSession session) {
		long fromId = Long.parseLong(transferForm.getFromAccountId());
		Account from = ar.findOne(fromId);
		Account to;
		String type = transferForm.getType();
		if (type.equals("withinMyAccounts")) {
			long toId = Long.parseLong(transferForm.getToAccountId());
			to = ar.findOne(toId);
		} else {
			String toNum = transferForm.getToAccountNum();
			to = ar.findByAccountNumber(toNum).get(0);
		}
		Customer c = (Customer) session.getAttribute("customer");
		model.addAttribute("customer", c);
		model.addAttribute("confirmTransfer", new TransferForm());
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return "confirm-transfer";
	}

	@PostMapping("/process")
	public String transfer(@ModelAttribute TransferForm confirmTransfer, Model model, HttpSession session) {
		Customer c = (Customer) session.getAttribute("customer");
		model.addAttribute("customer", c);
		long fromId = Long.parseLong(confirmTransfer.getFromAccountId());
		long toId = Long.parseLong(confirmTransfer.getToAccountId());
		Account from = ar.findOne(fromId);
		Account to = ar.findOne(toId);
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("confirmTransfer", confirmTransfer);
		double amount = Double.parseDouble(confirmTransfer.getAmount());
		String frequency = confirmTransfer.getFrequency();
		if (from.getBalance() >= amount) {
			if (frequency == null || frequency.equals("One time, immediately")) {
				from.setBalance(from.getBalance() - amount);
				to.setBalance(to.getBalance() + amount);
				// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date d = new Date();
				// String today = sdf.format(d);
				String description = "Transfer" + amount + "from" + from.getAccountNumber() + "to"
						+ to.getAccountNumber();
				TransactionType t = ttr.findByTransactionTypeName("Transfer").get(0);
				Transaction transaction = new Transaction(d, amount, from.getBalance(), t, from, null, description,
						"clear");
				tr.save(transaction);
			} else {
//				Customer c = from.getCustomer();
				RecurringPayment recurringPayment = new RecurringPayment(c, fromId, toId, from.getAccountNumber(),
						to.getAccountNumber(), from.getAccountType().toString(), to.getAccountType().toString(), amount,
						frequency);
				rpr.save(recurringPayment);
			}
			return "success-transfer";
		} else {
			model.addAttribute("error", "Not enough money");
			return "redirect:transfer";
		}
	}
}
