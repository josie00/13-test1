//package com.cfs.controller;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.LockModeType;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.transaction.Transactional;
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Lock;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import com.cfs.databean.Customer;
//import com.cfs.databean.Employee;
//import com.cfs.databean.Fund;
//import com.cfs.databean.Position;
//import com.cfs.databean.Transaction;
//import com.cfs.formbean.ChangePasswordForm;
//import com.cfs.formbean.FundViewForm;
//import com.cfs.formbean.LoginForm;
//import com.cfs.formbean.RequestCheckForm;
//import com.cfs.repository.CustomerRepository;
//import com.cfs.repository.EmployeeRepository;
//import com.cfs.repository.FundRepository;
//import com.cfs.repository.PositionRepository;
//import com.cfs.repository.TransactionRepository;
//import com.cfs.service.ButtonService;
//import com.cfs.service.FormService;
//import com.cfs.service.TransactionService;
//
//@Controller
//public class CustomerController {
//	
//	@Autowired
//	CustomerRepository cr;
//
//	@Autowired
//	EmployeeRepository er;
//	
//	@Autowired
//	TransactionRepository tr;
//	
//	@Autowired
//	PositionRepository pr;
//	
//	@Autowired
//	FundRepository fr;
//	
//	@Autowired
//	FormService fs;
//	
//	@Autowired
//	ButtonService bs;
//	
//	@Autowired
//	TransactionService ts;
//
//	@GetMapping("/home")
//	public String home(Model model) {
//		model.addAttribute("loginForm", new LoginForm());
//		return "index";
//	}
//
//	@PostMapping("/login")
//	public String login(@Valid LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
//		if (bindingResult.hasErrors()) {
//			return "index";
//		}
//		
//		HttpSession session = request.getSession();
//		String identity = loginForm.getIdentity();
//		String username = loginForm.getUserName();
//		String password = loginForm.getPassword();
//		String button = loginForm.getButton();
//		
//		if (fs.invalid(username)) {
//		    bindingResult.addError(new ObjectError("userNameError", "UserName may not contain special character."));
//		    return "index";
//		}
//		if (fs.invalid(password)) {
//            bindingResult.addError(new ObjectError("passwordError", "Password may not contain special character."));
//            return "index";
//		}
//		if (bs.buttonMismatch(button, "Login") || bs.invalidButton(button)) {
//		    bindingResult.addError(new ObjectError("buttonError", "Button may only be Login and may not contain angle brackets or quotes."));
//            return "index";
//		}
//		
//		if (identity.equals("Customer")) {
//			List<Customer> customers = cr.findByUserName(loginForm.getUserName());
//			if (customers.size() != 0) {
//				Customer c = customers.get(0);
//				System.out.println(c.getUserName());
//				if (loginForm.getPassword().equals(c.getPassword())) {
//					session.setAttribute("customer", c);
//					return "redirect:customer";
//				} else {
//					bindingResult.addError(new ObjectError("passwordError", "Password is incorrect."));
//					return "index";
//				}
//
//			} else {
//				bindingResult.addError(new ObjectError("userNameError", "Username is incorrect."));
//				return "index";
//			}
//		}
//		if (identity.equals("Employee")) {
//			List<Employee> employees = er.findByUserName(loginForm.getUserName());
//			if (employees.size() != 0) {
//				Employee e = employees.get(0);
//				if (loginForm.getPassword().equals(e.getPassword())) {
//					session.setAttribute("employee", e);
//					return "redirect:employee";
//				} else {
//					bindingResult.addError(new ObjectError("passwordError", "Password is incorrect."));
//					return "index";
//				}
//			} else {
//				bindingResult.addError(new ObjectError("userNameError", "Username is incorrect."));
//				return "index";
//			}
//		}
//		bindingResult.addError(new ObjectError("identityError", "Identity is invalid."));
//		return "index";
//		
//	}

//	
//	@GetMapping("/customerLogout")
//    public String customerLogout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        session.setAttribute("customer", null);
//        return "redirect:home";
//    }
//	
//	@GetMapping("/customer")
//	public String customerAccount(Model model, HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		Customer c = (Customer) session.getAttribute("customer");
//		if (c == null) {
//		    model.addAttribute("error", "You should log in first to view your account.");
//		    return "error";
//		}
//		Customer cNew = cr.findOne(c.getCustomerId());
//		session.setAttribute("customer", cNew);
//		c = cNew;
//		System.out.println("firstname" + c.getFirstName());
//		model.addAttribute("customer", c);
//		List<Transaction> transactions = tr.findByCustomer_CustomerId(c.getCustomerId());
//		model.addAttribute("transactions", transactions);
//		List<Position> positions = pr.findByCustomer_CustomerId(c.getCustomerId());
//		List<FundViewForm> customerFunds = new ArrayList<>();
//		List<Transaction> processTransactions = new ArrayList<Transaction>();
//		for (Transaction t: transactions) {
//			if (!t.getStatus().equals("Pending")) {
//				processTransactions.add(t);
//			}
//			
//		}
//		System.out.println("size"+ processTransactions.size());
//		if(processTransactions != null && processTransactions.size() > 0) {
//			Collections.sort(processTransactions, new Comparator<Transaction>() {
//				@Override
//				public int compare(Transaction o1, Transaction o2) {
//					return o2.getExecuteDate().compareTo(o1.getExecuteDate());
//				}
//			});
//			Date lastDate = processTransactions.get(0).getExecuteDate();
//			model.addAttribute("lastDate", lastDate);	
//		}
//		
//		for (Position p: positions) {
//			FundViewForm fundView = new FundViewForm();
//			Fund fund = p.getFund();
//			fundView.setFundId(fund.getFundId());
//			fundView.setFundName(fund.getName());
//			fundView.setFundSymbol(fund.getSymbol());
//			fundView.setShares(p.getShares());
//			double value = p.getShares() * fund.getCurrPrice();
//			fundView.setValue(value);
//			customerFunds.add(fundView);
//		}
//		
//		model.addAttribute("customerFunds", customerFunds);
//		return "customerAccount";
//	}
//	
//	@GetMapping("/transactionHistory")
//	public String transactions(Model model, HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		Customer c = (Customer) session.getAttribute("customer");
//		if (c == null) {
//            model.addAttribute("error", "You should log in first to view your transaction history.");
//            return "error";
//		}
//		model.addAttribute("customer", c);
//		List<Transaction> transactions = tr.findByCustomer_CustomerId(c.getCustomerId());
//		model.addAttribute("transactions", transactions);
//		return "transactionHistory";
//	}
//	
//	@GetMapping("/changePassword")
//    public String changePasswordPage(Model model, HttpServletRequest request) {
//        model.addAttribute("changePasswordForm", new ChangePasswordForm());
//        HttpSession session = request.getSession();
//        Customer c = (Customer) session.getAttribute("customer");
//        if (c == null) {
//            model.addAttribute("error", "You should log in first to process change password.");
//            return "error";
//        }
//        System.out.println("firstname" + c.getFirstName());
//        model.addAttribute("customer", c);
//        return "changePassword";
//    }
//    
//	@Transactional
//    @PostMapping("/changePassword")
//    public String  changePassword(@Valid ChangePasswordForm changePasswordForm, BindingResult bindingResult, HttpServletRequest request, Model model) {
//	    HttpSession session = request.getSession();
//        Customer c = (Customer) session.getAttribute("customer");
//        if (c == null) {
//            model.addAttribute("error", "You should log in first to process change password.");
//            return "error";
//        }
//        
//        
//	    if (bindingResult.hasErrors()) {
//	        model.addAttribute("error", "You have invalid input: fields should not be empty.");
//	        return "error";
//        }
//	    
//	    if (fs.invalid(changePasswordForm.getCurrentPassword())) {
//            model.addAttribute("error", "Current password may not contain special characters.");
//            return "error";
//        }
//        
//        if (fs.invalid(changePasswordForm.getNewPassword())) {
//            model.addAttribute("error", "New password may not contain special characters.");
//            return "error";
//        }
//        
//        if (fs.invalid(changePasswordForm.getConfirmPassword())) {
//            model.addAttribute("error", "Confirm password may not contain special characters.");
//            return "error";
//        }
//        
//        if (!changePasswordForm.getCurrentPassword().equals(c.getPassword())) {
//            model.addAttribute("error", "Current password is incorrect");
//            return "error";
//        } else if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {
//            model.addAttribute("error", "New password does not match");
//            return "error";
//        } else if (changePasswordForm.getCurrentPassword().equals(changePasswordForm.getNewPassword())) {
//            model.addAttribute("error", "New password is the same as the current password, please create a new one.");
//            return "error";
//        } else if (bs.buttonMismatch(changePasswordForm.getButton(), "ChangePassword")) {
//            model.addAttribute("error", "The action must equal to change password");
//            return "error";
//        } else {
//            c.setPassword(changePasswordForm.getNewPassword());
//            cr.save(c);
//            return "redirect:customer";       
//        }
//    }
//    
//    @GetMapping("/requestCheck")
//	public String requestCheckPage(Model model, HttpServletRequest request) {
//		model.addAttribute("requestCheckForm", new RequestCheckForm());
//		
//		HttpSession session = request.getSession();
//		Customer c = (Customer) session.getAttribute("customer");
//		if (c == null) {
//			model.addAttribute("error", "Please log in first.");
//			return "error";
//		}
//		model.addAttribute("customer", c);
//		return "requestCheck";
//	}
//
//	@Transactional
//	@Lock(LockModeType.PESSIMISTIC_WRITE)
//	@PostMapping("/requestCheck")
//	public String requestCheck(@ModelAttribute @Valid RequestCheckForm requestCheckForm, Model model,
//			BindingResult bindingResult, HttpServletRequest request) {
//		if (requestCheckForm.getAmount() == null || requestCheckForm.getButton() == null) {
//			model.addAttribute("error", "Input/button actions can not be null.");
//			return "error";
//		}
//		if (bindingResult.hasErrors()) {
////			System.out.println("dddd");
//		    model.addAttribute("error", "You have invalid input: fields should not be empty and amount should be greater than 0.");
//            return "error";
//        }
//		
//		HttpSession session = request.getSession();
//		Customer c = (Customer) session.getAttribute("customer");
//		if (c == null) {
//			model.addAttribute("error", "You should log in first to process check requests.");
//			return "error";
//		}
//		model.addAttribute("customer", c);
//		if (ts.numberError(requestCheckForm.getAmount())) {
//            model.addAttribute("error", "Your amount input is not valid, please enter a double larger than 0 and smaller than 100,000");
//            return "error"; 
//        }
//		if (bs.buttonMismatch(requestCheckForm.getButton(), "RequestCheck")) {
//            model.addAttribute("error", "The action must equal to Request Check");
//            return "error";
//        }
//		
//		double amount = Double.parseDouble(requestCheckForm.getAmount());
//		double tempCash = c.getTempCash() - amount;
//		if (tempCash < 0) {
//			model.addAttribute("error", "Not enough cash balance.");
//			return "error";
//		}
//		c.setTempCash(tempCash);
//		cr.save(c);
////		cr.setTempCash(tempCash, c.getCustomerId());
//		
//		Transaction transaction = new Transaction(null, 0.00, 0.00, "Request Check", amount,
//				"Pending", null, c, null);
////		System.out.println(transaction);
//		tr.save(transaction);
////		session.setAttribute("customer", c);
////		model.addAttribute("customer", c);
//		
//		return "tradeSuccess";
//	}
//}