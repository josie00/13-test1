package com.cfs.controller;



import java.util.List;

import javax.persistence.LockModeType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cfs.databean.Customer;
import com.cfs.databean.Employee;
import com.cfs.databean.Fund;

import com.cfs.databean.FundPriceHistory;
import com.cfs.databean.Position;
import com.cfs.databean.Transaction;
import com.cfs.formbean.BuyFundForm;
import com.cfs.formbean.ChangePasswordForm;
import com.cfs.formbean.CreateCustomerForm;
import com.cfs.formbean.CreateFundForm;
import com.cfs.formbean.LoginForm;
import com.cfs.formbean.SellFundForm;
import com.cfs.repository.CustomerRepository;
import com.cfs.repository.EmployeeRepository;
import com.cfs.repository.FundPriceHistoryRepository;
import com.cfs.repository.FundRepository;
import com.cfs.repository.PositionRepository;
import com.cfs.repository.TransactionRepository;
import com.cfs.service.FormService;
import com.cfs.service.TransactionService;

@Controller
public class FundController {
	
	@Autowired
	CustomerRepository cr;

	@Autowired
	EmployeeRepository er;
	
	@Autowired
	FundRepository fr;
	
	@Autowired
	PositionRepository pr;
	
	@Autowired
	TransactionRepository tr;
	
	@Autowired
	FundPriceHistoryRepository fphr;
	
	@Autowired
	TransactionService ts;
	
	@Autowired
	FormService fs;
	
	@GetMapping("/getAllFund") 
	public String getAllFund(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		if (c == null) {
			model.addAttribute("error", "Please log in first.");
			return "error";
		}
		Iterable<Fund> funds = fr.findAll(); 
		model.addAttribute("customer",c);
		model.addAttribute("funds", funds);
		return "research-fund";
	}
	
	
	@GetMapping("/fund") 
	public String getfund(@RequestParam(value="fundId", defaultValue="0") String fundId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		if (c == null) {
			model.addAttribute("error", "Please log in first.");
			return "error";
		}
		long id = Long.parseLong(fundId.trim());
		Fund fund = fr.findOne(id);
		List<FundPriceHistory> hphs = fphr.findByFund_FundId(id);
		List<Position> pos = pr.findByFund_FundIdAndCustomer_CustomerId(id, c.getCustomerId());
		double shares = 0;
		double estShares = 0;
		if (pos != null & pos.size() > 0) {
			Position position = pos.get(0);
			shares = position.getShares();
			estShares = position.getTempShares();
		}
		model.addAttribute("shares", shares);
		model.addAttribute("estShares", estShares);
		model.addAttribute("customer", c);
		model.addAttribute("hphs",hphs);
		model.addAttribute("fund", fund);
		return "fund";
	}
	
	@GetMapping("/buyFund") 
	public String buyFundPage(@RequestParam(value="fundId", defaultValue="0") String fundId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		if (c == null) {
			model.addAttribute("error", "Please log in first.");
			return "error";
		}
		long id = Long.parseLong(fundId.trim());
		Fund fund = fr.findOne(id);
		BuyFundForm form = new BuyFundForm();
		model.addAttribute("customer", c);
		model.addAttribute("fund", fund);	
		model.addAttribute("form", form);
		return "buyFund";
		
	}
	
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@PostMapping("/buyFund") 
	public String buyFund(Model model, HttpServletRequest request, BuyFundForm form, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");	
		if (form == null || form.getAmount() == null || form.getButton() == null ) {
			model.addAttribute("error", "Input/button actions can not be null.");
			return "error";
		}
		long id = form.getFundId();
		Fund fund = fr.findOne(id);
		if (c == null) {
			model.addAttribute("error", "Please log in first.");
			return "error";
		}
		model.addAttribute("customer", c);
		if (bindingResult.hasErrors()) {
            return "redirect:buyFund";
        }
		if (ts.numberError(form.getAmount())) {
		    model.addAttribute("error", "Your amount input is not valid, please enter a double larger than 0 and smaller than 100,000");
            return "error"; 
		}
		Double amount = Double.parseDouble(form.getAmount());
		System.out.println("=================before" + c.getCash() + "----" + c.getTempCash());
		double tempCash = c.getTempCash() - amount;
		if (tempCash < 0) {
			model.addAttribute("error", "You don't have enough money to buy!");
			return "error";
		}
		Transaction tran = new Transaction(null, 0, 0, "Buy Fund", amount, "Pending", fund, c, null);
		c.setTempCash(tempCash);
		cr.save(c);
//		cr.setTempCash(tempCash, c.getCustomerId());
		System.out.println("=================after" + c.getCash() + "----" + c.getTempCash());
		tr.save(tran);
		session.setAttribute("customer", c);
		model.addAttribute("customer", c);
		return "tradeSuccess";
	}
	
	
	@GetMapping("/sellFund") 
	public String sellFundPage(@RequestParam(value="fundId", defaultValue="0") String fundId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");
		if (c == null) {
			model.addAttribute("error", "Please log in first.");
			return "error";
		}
		long id = Long.parseLong(fundId.trim());
		Fund fund = fr.findOne(id);
		SellFundForm form = new SellFundForm();
		List<Position> pos = pr.findByFund_FundIdAndCustomer_CustomerId(id, c.getCustomerId());
		double shares = 0;
		double estShares = 0;
		if (pos != null & pos.size() > 0) {
			Position position = pos.get(0);
			shares = position.getShares();
			estShares = position.getTempShares();
		}
		model.addAttribute("shares", shares);
		model.addAttribute("estShares", estShares);
		model.addAttribute("customer", c);
		model.addAttribute("fund", fund);	
		model.addAttribute("form", form);
		return "sellFund";
		
	}
	
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@PostMapping("/sellFund") 
	public String sellFund(Model model, HttpServletRequest request, SellFundForm form, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		Customer c = (Customer) session.getAttribute("customer");	
		if (form.getShares() == null) {
			model.addAttribute("error", "Input/button actions can not be null.");
			return "error";
		}
		if (c == null) {
			model.addAttribute("error", "Please log in first.");
			return "error";
		}
		model.addAttribute("customer", c);
		if (bindingResult.hasErrors()) {
            return "redirect:sellFund";
        }	
		long id = form.getFundId();
		
		Fund fund = fr.findOne(id);
		List<Position> ps= pr.findByFund_FundIdAndCustomer_CustomerId(id, c.getCustomerId());
		System.out.println("id:" + id);
		System.out.println("pos:" + ps.size());
		if (ps == null || ps.size() == 0) {
			model.addAttribute("error", "You do not have any share of this fund to sell!");
			return "error";
		} 

		Position po = ps.get(0);
		if (ts.numberError(form.getShares())) {
		    model.addAttribute("error", "Your shares input is not valid, please enter a double larger than 0 adn smaller than 100,000");
            return "error"; 
		}
		Double shares = Double.parseDouble(form.getShares());
		double tempShares = po.getTempShares() - shares;
		if (tempShares < 0) {
			model.addAttribute("error", "You don't have enough shares to sell!");
			return "error";
		}
		po.setTempShares(tempShares);
		pr.save(po);
//		pr.setTempShares(tempShares, c.getCustomerId(), id);
		
		Transaction tran = new Transaction(null, shares, 0, "Sell Fund", 0, "Pending", fund, c, null);
		tr.save(tran);
//		session.setAttribute("customer", c);
//		model.addAttribute("customer", c);
		return "tradeSuccess";
	}

	

	@GetMapping("/createFund")
	public String createFundPage(Model model, HttpServletRequest request) {
		model.addAttribute("createFundForm", new CreateFundForm());
		HttpSession session = request.getSession();
        Employee e = (Employee) session.getAttribute("employee");
        if (e == null) {
        	model.addAttribute("error", "You should log in first to create fund.");
			return "error";
        }
        model.addAttribute("employee", e);
		return "createFund";
	}

	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@PostMapping("/createFund")
	public String createCustomer(@Valid CreateFundForm createFundForm, Model model, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
            return "createFund";
        }
		
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
			model.addAttribute("error", "You should log in first to create fund.");
			return "error";
		}
		model.addAttribute("employee", e);
		
		if (fs.invalid(createFundForm.getSymbol())) {
		    model.addAttribute("error", "Fund symbol may not contain angle brackets or quotes.");
            return "error";
		}
		
		if (fs.invalid(createFundForm.getName())) {
            model.addAttribute("error", "Fund name may not contain angle brackets or quotes.");
            return "error";
        }

		if (fr.findBySymbol(createFundForm.getSymbol()).size() != 0) {
			model.addAttribute("error", "Fund with the same symbol is already created.");
			return "error";
		}
		
		if (fr.findBySymbol(createFundForm.getName()).size() != 0) {
            model.addAttribute("error", "Fund with the same name is already created.");
            return "error";
        }
		Fund fund = new Fund(createFundForm.getName(), createFundForm.getSymbol(), 0.00, null, null);
		fr.save(fund);
		
		return "transitionSuccessEmployee";

	}
}
