package com.obs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.obs.databean.Loan;
import com.obs.databean.LoanType;
import com.obs.databean.RecurringLoanPay;
import com.obs.databean.Transaction;
import com.obs.databean.TransactionType;
import com.obs.formbean.LoanForm;
import com.obs.repository.AccountRepository;
import com.obs.repository.LoanRepository;
import com.obs.repository.RecurLoanPayRepository;
import com.obs.repository.TransactionRepository;
import com.obs.repository.TransactionTypeRepository;

@Controller
public class LoanController {
	
	@Autowired
	LoanRepository lr;
	
	@Autowired
	AccountRepository ar;
	
	@Autowired
	TransactionTypeRepository ttr;
	
	@Autowired
	TransactionRepository tr;
	
	@Autowired
	RecurLoanPayRepository rlpp;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping("/loan")
	public String getAccount(@RequestParam(value="loanId", defaultValue="0") String loanId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");	
		long id = Long.parseLong(loanId.trim());
		Loan loan = lr.findOne(id);
		LoanType loanType = loan.getLoanType();
		model.addAttribute("customer", c);
		model.addAttribute("loanType", loanType);
		model.addAttribute("loan", loan);
	
		return "loan";
	}
	
	@GetMapping("/loan-payment")
	public String getLoanPay(@RequestParam(value="loanId", defaultValue="0") String loanId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");	
		model.addAttribute("customer", c);
		
		// for directing from loan plan details
		long id = Long.parseLong(loanId.trim());
		Loan thisLoan = lr.findOne(id);
		LoanType loanType = thisLoan.getLoanType();
		
		model.addAttribute("loanType", loanType);
		model.addAttribute("thisLoan", thisLoan);
		
		// for displaying payment form
		List<Account> accounts = new ArrayList<>();
		List<RecurringLoanPay> recurringLoanPays = new ArrayList<>();
		accounts = ar.findByCustomer_CustomerId(c.getCustomerId());
		recurringLoanPays = rlpp.findByCustomer_CustomerId(c.getCustomerId());
		model.addAttribute("loanForm", new LoanForm());
		model.addAttribute("accounts", accounts);
		model.addAttribute("recurringLoanPays", recurringLoanPays);
		
		// for payment history
		List<Loan> loans = lr.findByCustomer_CustomerId(c.getCustomerId());
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		for (Loan loan : loans) {
			System.out.println(loan);
			List<Transaction> loanTransactions = tr.findByLoan_LoanId(loan.getLoanId());
			System.out.println(loanTransactions);
			for (Transaction transaction : loanTransactions) {
				System.out.println(transaction);
				transactions.add(transaction);
			}
		}
		//if (! transactions.isEmpty()) {
//			transactions.sort((t1,t2) -> t2.getTimeStamp().compareTo(t1.getTimeStamp()));
			System.out.println(transactions);
			model.addAttribute("transactions", transactions);
		//}

		System.out.println(loans);
		model.addAttribute("loans", loans);
	
		return "loan-payment";
	}
	
	@PostMapping("/confirmLoan")
	public String confirmLoanPay(@ModelAttribute LoanForm loanForm, Model model, HttpSession session){
		System.out.println("account id dddddddd" + loanForm.getFromAccountId()); // print out
		System.out.println("loan id dddddddd" + loanForm.getToLoanId());
		long fromAccountId = Long.parseLong(loanForm.getFromAccountId());
		long toLoanId = Long.parseLong(loanForm.getToLoanId());

		Account fromAccount = ar.findOne(fromAccountId);
		Loan toLoan;
		String amount = loanForm.getAmount();
		
		String dateOfLoanPayString = loanForm.getDateOfLoanPay();
		String dateOfFirstPayString = loanForm.getDateOfFirstPay();
		Date dateOfLoanPay;
		Date dateOfFirstPay;
		String frequency = loanForm.getFrequency();
		if (dateOfLoanPayString != null) {
			dateOfLoanPay = null;
			try {
				dateOfLoanPay = sdf.parse(dateOfLoanPayString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.addAttribute("dateOfLoanPay", dateOfLoanPay);
		}
		if (dateOfFirstPayString != null) {
			dateOfFirstPay = null;
			try {
				dateOfFirstPay = sdf.parse(dateOfFirstPayString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			model.addAttribute("dateOfFirstPay", dateOfFirstPay);
		}
		String numOfPay = loanForm.getNumOfPay();
		if (numOfPay != null) {
			model.addAttribute("numOfPay", numOfPay);
		}
		if (frequency != null) {
			model.addAttribute("frequency", frequency);
		}

		
		toLoan = lr.findOne(toLoanId);
		
		Customer c = (Customer) session.getAttribute("customer");
		model.addAttribute("customer", c);
		model.addAttribute("confirmLoanPay", new LoanForm());
		model.addAttribute("from", fromAccount);
		model.addAttribute("to", toLoan);
		model.addAttribute("amount", amount);
		
		return "confirm-loan-payment";
	}

	@PostMapping("/processLoan")
	public String processLoanPay(@ModelAttribute LoanForm confirmLoanPay, Model model, HttpSession session) throws ParseException {
		Customer c = (Customer) session.getAttribute("customer");
		model.addAttribute("customer", c);
		
		long fromAccountId = Long.parseLong(confirmLoanPay.getFromAccountId());
		long toLoanId = Long.parseLong(confirmLoanPay.getToLoanId());
		Account fromAccount = ar.findOne(fromAccountId);
		Loan toLoan = lr.findOne(toLoanId);
		String dateOfLoanPayString = confirmLoanPay.getDateOfLoanPay();
		String dateOfFirstPayString = confirmLoanPay.getDateOfFirstPay();
		Date dateOfLoanPay = null;
		if (dateOfLoanPayString != null) {
			dateOfLoanPay = sdf.parse(dateOfLoanPayString);
			model.addAttribute("dateOfFirstPay", dateOfLoanPay);
		}
		Date dateOfFirstPay = null;
		if (dateOfFirstPayString != null) {
			dateOfFirstPay = sdf.parse(dateOfFirstPayString);
			model.addAttribute("dateOfFirstPay", dateOfFirstPay);
		}
		String numOfPay = confirmLoanPay.getNumOfPay();
		if (numOfPay != null) {
			model.addAttribute("numOfPay", numOfPay);
		}
		
		model.addAttribute("from", fromAccount);
		model.addAttribute("to", toLoan);
		model.addAttribute("confirmLoanPay", confirmLoanPay);
		double amount = Double.parseDouble(confirmLoanPay.getAmount());
		String frequency = confirmLoanPay.getFrequency();
		if (fromAccount.getBalance() >= amount) {
			if (dateOfLoanPay != null || frequency != null) {
				System.out.println(dateOfLoanPay);
				System.out.println(frequency);
				fromAccount.setBalance(fromAccount.getBalance() - amount);
				toLoan.setDuePayment(toLoan.getDuePayment() - amount);
				String description1 = "Transfer " + amount + " from " + fromAccount.getAccountNumber() + " to "
						+ toLoan.getLoanNumber();
				String description2 = "Loan " + toLoan.getLoanNumber() + " get " + amount + " payment from " 
						+ fromAccount.getAccountNumber();
				TransactionType t = ttr.findByTransactionTypeName("Loan Pay").get(0);
				Transaction transaction1 = new Transaction(dateOfLoanPay, -amount, fromAccount.getBalance(),
						t, fromAccount, null, null, description1, "Clear");
				Transaction transaction2 = new Transaction(dateOfLoanPay, amount, toLoan.getDuePayment(), 
						t, null, toLoan, null, description2, "Clear");
				tr.save(transaction1);
				tr.save(transaction2);
			} else {
				Customer customer = fromAccount.getCustomer();
				RecurringLoanPay recurringLoanPay = new RecurringLoanPay(customer, fromAccountId, toLoanId, fromAccount.getAccountNumber(),
						toLoan.getLoanNumber(), amount, frequency, dateOfFirstPay, numOfPay);
				rlpp.save(recurringLoanPay);
			}
			return "success-loan-payment";
		} else {
			model.addAttribute("error", "Not enough money");
			return "redirect:loan-payment";
		}
	}
}
