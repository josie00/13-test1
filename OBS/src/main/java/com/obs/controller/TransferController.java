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
		Object obj = session.getAttribute("customer");
	    Customer customer = (Customer) obj;
		List<Account> accounts = ar.findByCustomer_CustomerId(customer.getCustomerId());
		List<Account> activeAccounts = new ArrayList<>();
		for (Account account: accounts) {
			if ("active".equals(account.getStatus())) {
				activeAccounts.add(account);
			}
		}
		List<RecurringPayment> recurringPayments = rpr.findByCustomer_CustomerId(customer.getCustomerId());
		model.addAttribute("customer", customer);
		model.addAttribute("transferForm", new TransferForm());
		model.addAttribute("accounts", activeAccounts);
		model.addAttribute("recurringPayments", recurringPayments);
		return "transfer";
	}

	@PostMapping("/confirm")
	public String confirmTransfer(@ModelAttribute TransferForm transferForm, Model model, HttpSession session) {
		long fromId = Long.parseLong(transferForm.getFromAccountId());
		Account from = ar.findOne(fromId);
		Account to = new Account();
		String type = transferForm.getType();
		if (type.equals("withinMyAccounts")) {
			long toId = Long.parseLong(transferForm.getToAccountId());
			to = ar.findOne(toId);
		} else if (type.equals("toOtherAccount")) {
			String toNum = transferForm.getToAccountNum();
			to = ar.findByAccountNumber(toNum).get(0);
		} else {
			String toAccountNum = transferForm.getToAccountNum();
			System.out.println(toAccountNum);
			model.addAttribute("toAccountNum", toAccountNum);
		}
		Customer c = (Customer) session.getAttribute("customer");
		model.addAttribute("customer", c);
		model.addAttribute("confirmTransfer", new TransferForm());
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		return "confirm-transfer";
	}

	@PostMapping("/process")
	public String transfer(@ModelAttribute TransferForm confirmTransfer, Model model, HttpSession session, @RequestParam(value="action", required=true) String action) {
		if(action.equals("Don't Make Transfer")) {
			return "redirect:accounts";
		}
		Customer c = (Customer) session.getAttribute("customer");
		model.addAttribute("customer", c);
		long fromId = Long.parseLong(confirmTransfer.getFromAccountId());
		Account from = ar.findOne(fromId);
		Account to = new Account();
		double amount = Double.parseDouble(confirmTransfer.getAmount());
		String type = confirmTransfer.getType();
		if(type.equals("toOtherBank")) {
			String toAccountNum = confirmTransfer.getToAccountNum();
			model.addAttribute("from", from);
			model.addAttribute("to", to);
			model.addAttribute("confirmTransfer", confirmTransfer);
			if (from.getBalance() >= amount) {
				from.setBalance(from.getBalance() - amount);
				Date d = new Date();
				String description = "Transfer " + amount + " from " + from.getAccountNumber() + " to "
						+ toAccountNum;
				TransactionType t = ttr.findByTransactionTypeName("Transfer").get(0);
				Transaction transaction = new Transaction(d, -amount, from.getBalance(), t, from, null, description,
						"Clear");
				Transaction tran=tr.save(transaction);
				if(tran == null) {
					throw new IllegalArgumentException();
				}
				return "success-transfer";
			}else {
				model.addAttribute("error", "Not enough money");
				return "redirect:transfer";
			}
		}
		long toId = Long.parseLong(confirmTransfer.getToAccountId());
		to = ar.findOne(toId);
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("confirmTransfer", confirmTransfer);
		String frequency = confirmTransfer.getFrequency();
		if (from.getBalance() >= amount) {
			if (frequency == null || frequency.equals("One time, immediately")) {
				from.setBalance(from.getBalance() - amount);
				to.setBalance(to.getBalance() + amount);
				// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date d = new Date();
				String description1 = "Transfer " + amount + " from " + from.getAccountNumber() + " to "
						+ to.getAccountNumber();
				String description2 = "Receive " + amount + " from " + from.getAccountNumber() + " to "
						+ to.getAccountNumber();
				TransactionType t = ttr.findByTransactionTypeName("Transfer").get(0);
				Transaction transaction1 = new Transaction(d, -amount, from.getBalance(), t, from, null, description1,
						"Clear");
				Transaction transaction2 = new Transaction(d, amount, to.getBalance(), t, to, null, description2,
						"Clear");
				Transaction t1=tr.save(transaction1);
				Transaction t2=tr.save(transaction2);
				if(t1 == null) {
					throw new IllegalArgumentException();
				}
				if(t2 == null) {
					throw new IllegalArgumentException();
				}
			} else {
				Customer customer = from.getCustomer();
				RecurringPayment recurringPayment = new RecurringPayment(customer, fromId, toId, from.getAccountNumber(),
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
