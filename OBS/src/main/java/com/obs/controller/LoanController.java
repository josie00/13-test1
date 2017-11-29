package com.obs.controller;

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
import com.obs.databean.Loan;
import com.obs.databean.LoanType;
import com.obs.databean.Transaction;
import com.obs.repository.LoanRepository;

@Controller
public class LoanController {
	
	@Autowired
	LoanRepository lr;
	
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
}
