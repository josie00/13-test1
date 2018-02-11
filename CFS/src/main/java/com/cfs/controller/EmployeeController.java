package com.cfs.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import javax.persistence.LockModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cfs.databean.Customer;
import com.cfs.databean.Employee;
import com.cfs.databean.Fund;
import com.cfs.databean.FundPriceHistory;
import com.cfs.databean.Position;
import com.cfs.databean.Transaction;
import com.cfs.formbean.ChangePasswordForm;
import com.cfs.formbean.CreateCustomerForm;
import com.cfs.formbean.CreateEmployeeForm;
import com.cfs.formbean.DepositCheckForm;
import com.cfs.formbean.FundViewForm;
import com.cfs.formbean.RequestCheckForm;
import com.cfs.formbean.ResetPasswordForm;
import com.cfs.formbean.SearchForm;
import com.cfs.formbean.TransitionDayForm;
import com.cfs.formbean.TransitionDayWrapper;
import com.cfs.repository.CustomerRepository;
import com.cfs.repository.EmployeeRepository;
import com.cfs.repository.FundPriceHistoryRepository;
import com.cfs.repository.FundRepository;
import com.cfs.repository.PositionRepository;
import com.cfs.repository.TransactionRepository;
import com.cfs.service.ButtonService;
import com.cfs.service.FormService;
import com.cfs.service.TransactionService;


@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository er;

	@Autowired
	CustomerRepository cr;
	
	@Autowired
	TransactionRepository tr;
	
	@Autowired
	FundRepository fr;
	
	@Autowired
	FundPriceHistoryRepository fphr;
	
	@Autowired
	PositionRepository pr;
	
	@Autowired
	TransactionService ts;
	
	@Autowired
	FormService fs;
	
	@Autowired
	ButtonService bs;
	
	
	@GetMapping("/createAccount")
	public String createAccountPage(Model model, HttpServletRequest request) {
        model.addAttribute("createCustomerForm", new CreateCustomerForm());
        model.addAttribute("createEmployeeForm", new CreateEmployeeForm());
        
        HttpSession session = request.getSession();
        Employee e = (Employee) session.getAttribute("employee");
        if (e == null) {
        	model.addAttribute("error", "Please log in first to create accounts.");
			return "error";
        }
        model.addAttribute("employee", e);
        
        return "createAccount";
    }

	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@PostMapping("/createCustomer")
	public String createCustomer(@Valid CreateCustomerForm createCustomerForm, Model model, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
            return "error";
        }
		
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
			model.addAttribute("error", "Please log in first to create account for customer.");
			return "error";
		}
		if (fs.invalid(createCustomerForm.getUserName())) {
            model.addAttribute("error", "User name cannot contain special characters.");
            return "error";
        } else if (fs.invalid(createCustomerForm.getPassword())) {
            model.addAttribute("error", "Password cannot contain special characters.");
            return "error";
        } else if (fs.nameInvalid(createCustomerForm.getFirstName())) {
            model.addAttribute("error", "First name cannot contain special character or number.");
            return "error";
        } else if (fs.nameInvalid(createCustomerForm.getLastName())) {
            model.addAttribute("error", "Last name cannot contain special character or number.");
            return "error";
        } else if (fs.addressInvalid(createCustomerForm.getAddrLine1())) {
            model.addAttribute("error", "Address line 1 cannot contain special character.");
            return "error";
        } else if (fs.addressInvalid(createCustomerForm.getAddrLine2())) {
            model.addAttribute("error", "Address line 2 cannot contain special character.");
            return "error";
        } else if (fs.nameInvalid(createCustomerForm.getCity())) {
            model.addAttribute("error", "City cannot contain special character or number.");
            return "error";
        } else if (fs.nameInvalid(createCustomerForm.getState())) {
            model.addAttribute("error", "State cannot contain special character or number.");
            return "error";
        } else if (fs.zipInvalid(createCustomerForm.getZip())) {
            model.addAttribute("error", "Zip code is not valid, please enter a US zip code in format of XXXXX or XXXXX-XXXX.");
            return "error";
        } else if (bs.buttonMismatch(createCustomerForm.getButton(), "CreateCustomer")) {
            model.addAttribute("error", "The action must equal to create customer.");
            return "error";
        }
		List<Customer> customers = cr.findByUserName(createCustomerForm.getUserName());
		if (customers.size() != 0) {
		    model.addAttribute("error", "The username you entered already existed, please use another username.");
            return "error";
		}
		Customer customer = new Customer(createCustomerForm.getUserName(), createCustomerForm.getPassword(), createCustomerForm.getFirstName(),
				createCustomerForm.getLastName(), createCustomerForm.getAddrLine1(), createCustomerForm.getAddrLine2(),
				createCustomerForm.getCity(), createCustomerForm.getState(), createCustomerForm.getZip(), 0, 0, null, null);
		cr.save(customer);
		return "redirect:employee";
	}
	
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@PostMapping("/createEmployee")
	public String createCustomer(@Valid CreateEmployeeForm createEmployeeForm, Model model, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
            return "error";
        }

		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
			model.addAttribute("error", "Please log in first to create account for customer.");
			return "error";
		} else if (fs.invalid(createEmployeeForm.getUserName())) {
		    model.addAttribute("error", "User name cannot contain special character.");
            return "error";
		} else if (fs.invalid(createEmployeeForm.getPassword())) {
            model.addAttribute("error", "Password cannot contain special character.");
            return "error";
        } else if (fs.nameInvalid(createEmployeeForm.getFirstName())) {
            model.addAttribute("error", "First name cannot contain special character or number.");
            return "error";
		} else if (fs.nameInvalid(createEmployeeForm.getLastName())) {
            model.addAttribute("error", "Last name cannot contain special character or number.");
            return "error";
        } else if (bs.buttonMismatch(createEmployeeForm.getButton(), "CreateEmployee")) {
            model.addAttribute("error", "The action must equal to create employee.");
            return "error";
        }
		List<Employee> employees = er.findByUserName(createEmployeeForm.getUserName());
        if (employees.size() != 0) {
            model.addAttribute("error", "The username you entered already existed, please use another username.");
            return "error";
        }
		
		Employee employee = new Employee (createEmployeeForm.getUserName(), createEmployeeForm.getPassword(), createEmployeeForm.getFirstName(),
				createEmployeeForm.getLastName());
		er.save(employee);
		return "redirect:employee";
	}
	
	@GetMapping("/resetPassword")
    public String resetPasswordPage(@RequestParam(value="customerId", defaultValue="0") String customerId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee e = (Employee) session.getAttribute("employee");
        if (e == null) {
            model.addAttribute("error", "Please log in first to reset password for customer.");
            return "error";
        }
        model.addAttribute("employee", e);
        long id = Long.parseLong(customerId.trim());
        Customer c = cr.findOne(id);
        model.addAttribute("customer", c);
        return "resetPassword";
    }
	
	@GetMapping("/confirmResetPassword")
	public String confirmResetPage(@RequestParam(value="customerId", defaultValue="0") String customerId, Model model, HttpServletRequest request) {
	    HttpSession session = request.getSession();
        Employee e = (Employee) session.getAttribute("employee");
        if (e == null) {
            model.addAttribute("error", "Please log in first to confirm reset password for customer.");
            return "error";
        }
        model.addAttribute("employee", e);
        long id = Long.parseLong(customerId.trim());
        Customer c = cr.findOne(id);
        model.addAttribute("customer", c);
        c.setPassword("123456");
        cr.save(c);
        return "redirect:manageCustomer";
	}
	
	@GetMapping("/employee")
	public String employeeAccount(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
            model.addAttribute("error", "Please log in first to view your account.");
            return "error";
        }
		model.addAttribute("employee", e);
		return "employeeAccount";
	}
	
	@GetMapping("/depositCheck")
	public String depositCheckPage(@RequestParam(value="customerId", defaultValue="0") String customerId, Model model, HttpServletRequest request) {
		model.addAttribute("depositCheckForm", new DepositCheckForm());
		
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
			model.addAttribute("error", "Please log in first to make deposits for customer.");
			return "error";
		}
		model.addAttribute("employee", e);
		Long customerIdLong = Long.parseLong(customerId);
		Customer customer = cr.findOne(customerIdLong);
		model.addAttribute("customer", customer);
		
		return "depositCheck";
	}

	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@PostMapping("/depositCheck")
	public String depositCheck(@ModelAttribute @Valid DepositCheckForm depositCheckForm, Model model,
			BindingResult bindingResult, HttpServletRequest request) {
		if (depositCheckForm.getAmount()==null || depositCheckForm.getButton() == null || depositCheckForm.getCustomerId() == null) {
			model.addAttribute("error", "Input/button actions can not be null.");
			return "error";
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "You have invalid input: fields should not be empty and amount should be greater than 0.");
            return "error";
        }
		
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
			model.addAttribute("error", "You should log in first to process check deposits.");
			return "error";
		}
		
		model.addAttribute("employee", e);
		String customerId = depositCheckForm.getCustomerId();
		Customer c = cr.findOne(Long.parseLong(customerId));
		if (c == null) {
            model.addAttribute("error", "No customer found.");
            return "error";
        }
		if (ts.numberError(depositCheckForm.getAmount())) {
		    model.addAttribute("error", "The amount can only be a double larger than 0 and smaller than 100,000.");
            return "error";
		}
		Double amount = Double.parseDouble(depositCheckForm.getAmount());
		
		if (bs.buttonMismatch(depositCheckForm.getButton(), "DepositCheck")) {
		    model.addAttribute("error", "The action must equal to deposit check.");
            return "error";
		}
		Transaction transaction = new Transaction(null, 0.00, 0.00, "Deposit Check", amount,
				"Pending", null, c, null);
		tr.save(transaction);
		return "tradeSuccessEmployee";
	}
		
	@GetMapping("/manageCustomer")
	public String manageCustomer(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
    		model.addAttribute("error", "Please log in first to manage customer.");
		    return "error";
		}
		model.addAttribute("employee", e);
		model.addAttribute("searchForm", new SearchForm());
		return "manageCustomer";
	}
	
	@GetMapping("/getAllCustomer")
	public String getAllCustomer(Model model,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
    		model.addAttribute("error", "Please log in first to get all customers.");
		    return "error";
		}
		model.addAttribute("employee", e);
		Iterable<Customer> customers = cr.findAll();
    	model.addAttribute("customers", customers);
    	model.addAttribute("searchForm", new SearchForm());
		return "showCustomer";
	}
	
	@Transactional
	@PostMapping("/searchCustomer")
	public String searchCustomer(@Valid SearchForm searchForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
    		model.addAttribute("error", "Please log in first to search customer.");
		    return "error";
		}
		model.addAttribute("employee", e);
		if (bindingResult.hasErrors()) {
			return "manageCustomer";
		}
		String category = searchForm.getCategory();
		String input = searchForm.getInput();
		String button = searchForm.getButton();
	    System.out.println(button);
		model.addAttribute("searchForm", new SearchForm());
		if (bs.buttonMismatch(button, "SearchCustomer")) {
		    System.out.println("error");
		    model.addAttribute("error", "The action must equal to search customer.");
            return "error";
		}
		if (category.equals("userName")) {
			List<Customer> customers = cr.findByUserName(input);
			model.addAttribute("customers", customers);
			return "showCustomer";
		}
        if (category.equals("lastName")) {
        	List<Customer> customers = cr.findByLastName(input);
        	model.addAttribute("customers", customers);
			return "showCustomer";
		}
        if (category.equals("firstName")) {
        	List<Customer> customers = cr.findByFirstName(input);
        	model.addAttribute("customers", customers);
			return "showCustomer";
		}
        bindingResult.addError(new ObjectError("actionError", "Invalid Action."));
		return "manageCustomer";
	}
	
	@GetMapping("/viewAccount")
	public String viewAccount(@RequestParam(value="customerId", defaultValue="0") String customerId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
    		model.addAttribute("error", "Please log in first to view customer.");
		    return "error";
		}
		model.addAttribute("employee", e);
		long id = Long.parseLong(customerId.trim());
		Customer c = cr.findOne(id);
		model.addAttribute("customer", c);
		List<Transaction> transactions = tr.findByCustomer_CustomerId(id);
		model.addAttribute("transactions", transactions);
		
		List<Transaction> processTransactions = new ArrayList<Transaction>();
		for (Transaction t: transactions) {
			if (!t.getStatus().equals("Pending")) {
				processTransactions.add(t);
			}
			
		}
		if(processTransactions != null && processTransactions.size() > 0) {
			Collections.sort(processTransactions, new Comparator<Transaction>() {
				@Override
				public int compare(Transaction o1, Transaction o2) {
					return o2.getExecuteDate().compareTo(o1.getExecuteDate());
				}
			});
			Date lastDate = processTransactions.get(0).getExecuteDate();
			model.addAttribute("lastDate", lastDate);	
		}
		
		List<Position> positions = pr.findByCustomer_CustomerId(id);
		List<FundViewForm> customerFunds = new ArrayList<>();
		for (Position p: positions) {
			FundViewForm fundView = new FundViewForm();
			Fund fund = p.getFund();
			fundView.setFundId(fund.getFundId());
			fundView.setFundName(fund.getName());
			fundView.setFundSymbol(fund.getSymbol());
			fundView.setShares(p.getShares());
			double value = p.getShares() * fund.getCurrPrice();
			fundView.setValue(value);
			customerFunds.add(fundView);
		}
		model.addAttribute("customerFunds", customerFunds);
		return "viewCustomer";
	}
	
	@GetMapping("/transitionDay")
	public String transitionDay(Model model, HttpServletRequest request) {
		model.addAttribute("transitionDayForm", new TransitionDayForm());
		
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
			model.addAttribute("error", "You should log in first to process transition day.");
			return "error";
		}

		Iterable<Fund> funds = fr.findAll(); 
		model.addAttribute("funds", funds);
		TransitionDayWrapper transitionDayWrapper = new TransitionDayWrapper();
		model.addAttribute("transitionDayWrapper", transitionDayWrapper);
		model.addAttribute("employee", e);
		return "transitionDay";
	}
	
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@PostMapping("/transitionDay")
	public String transitionDay(@ModelAttribute TransitionDayWrapper transitionDayWrapper, Model model,
			BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
//			System.out.println("1");
            return "error";
        }
		
		HttpSession session = request.getSession();
		Employee e = (Employee) session.getAttribute("employee");
		if (e == null) {
			model.addAttribute("error", "You should log in first to process transition day.");
//			System.out.println("2");
			return "error";
		}
		model.addAttribute("employee", e);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date transitionDate = null;
		try {
			transitionDate = sdf.parse(transitionDayWrapper.getTransitionDate());
		} catch (ParseException e1) {
			Iterable<Fund> allFunds = fr.findAll(); 
			model.addAttribute("funds", allFunds);
			model.addAttribute("transitionDayWrapper", transitionDayWrapper);
			bindingResult.addError(new ObjectError("dateError", "Date error"));
			return "transitionDay";
		}
		if (fphr.findFirstByOrderByPriceDateDesc() != null) {
			if (transitionDate.compareTo(fphr.findFirstByOrderByPriceDateDesc().getPriceDate()) <= 0) {
				Iterable<Fund> allFunds = fr.findAll(); 
				model.addAttribute("funds", allFunds);
				model.addAttribute("transitionDayWrapper", transitionDayWrapper);
				bindingResult.addError(new ObjectError("dateError", "Date of transition must be greater than previous transition date."));
				return "transitionDay";
			}
		}
		ArrayList<Fund> funds = transitionDayWrapper.getFunds();
		Map<Long, Double> fundCurrPrices = new HashMap<Long, Double>();
		for (Fund fund : funds) {
			if (fund.getCurrPrice() <= 0.0) {
				Iterable<Fund> allFunds = fr.findAll(); 
				model.addAttribute("funds", allFunds);
				model.addAttribute("transitionDayWrapper", transitionDayWrapper);
				bindingResult.addError(new ObjectError("priceError", "Fund price should be larger than 0."));
				return "transitionDay";
			} else if (fund.getCurrPrice() > 100000) {
			    Iterable<Fund> allFunds = fr.findAll(); 
                model.addAttribute("funds", allFunds);
                model.addAttribute("transitionDayWrapper", transitionDayWrapper);
                bindingResult.addError(new ObjectError("priceError", "Fund price should be less than than 100000."));
                return "transitionDay";
			}
			DecimalFormat df = new DecimalFormat("#.0#");
			String p = df.format(fund.getCurrPrice());
			String temp = Double.toString(fund.getCurrPrice());
			if (!p.equals(temp)) {
				Iterable<Fund> allFunds = fr.findAll(); 
				model.addAttribute("funds", allFunds);
				model.addAttribute("transitionDayWrapper", transitionDayWrapper);
				bindingResult.addError(new ObjectError("priceError", "Fund price should only have two decimals."));
				return "transitionDay";
			}
		}
		for (Fund fund : funds) {
			fr.setCurrPrice(fund.getCurrPrice(), fund.getFundId());
			System.out.println("========" + fund.getFundId() + "======" + fund.getCurrPrice() + "========");
			FundPriceHistory fph = new FundPriceHistory(transitionDate, fund.getCurrPrice(), fund);
			fphr.save(fph);
			fundCurrPrices.put(fund.getFundId(), fund.getCurrPrice());
		}
		
		
		List<Transaction> buyOrders = tr.findByStatusAndTransactionType("Pending", "Buy Fund");
		List<Transaction> sellOrders = tr.findByStatusAndTransactionType("Pending", "Sell Fund");
		List<Transaction> requestChecks = tr.findByStatusAndTransactionType("Pending", "Request Check");
		List<Transaction> depositChecks = tr.findByStatusAndTransactionType("Pending", "Deposit Check");
		
//		double sumBuyPending = tr.getPendingSum("Pending", "Buy Fund");
//		double sumRequestPending = tr.getPendingSum("Pending", "Request Check");
//		double sumPending = sumBuyPending + sumRequestPending;
		
		for (Transaction buyOrder : buyOrders) {
			if (buyOrder.getCustomer().getCash() < buyOrder.getTransactionAmount()) {
				model.addAttribute("error", "Customer has insufficient cash balance to process buy order");
//				System.out.println("3");
				return "error";
			}
			
			double sharePrice = fundCurrPrices.get(buyOrder.getFund().getFundId());
			System.out.println("BUYYY===========" + sharePrice);
			buyOrder.setExecuteDate(transitionDate);
			buyOrder.setSharePrice(sharePrice);
			buyOrder.setShares(buyOrder.getTransactionAmount() / sharePrice);
			buyOrder.setStatus("Clear");
			tr.save(buyOrder);
//			tr.setExecuteDate(transitionDate, buyOrder.getTransactionId());
//			tr.setSharePrice(sharePrice, buyOrder.getTransactionId());
//			tr.setShares(buyOrder.getTransactionAmount() / sharePrice, buyOrder.getTransactionId());
//			tr.setStatus("Clear", buyOrder.getTransactionId());
			
			Customer customer = cr.findOne(buyOrder.getCustomer().getCustomerId());
			customer.setCash(customer.getCash() - buyOrder.getTransactionAmount());
			customer.setTempCash(customer.getCash());
			cr.save(customer);
//			cr.setCash(buyOrder.getCustomer().getCash() - buyOrder.getTransactionAmount(),
//					buyOrder.getCustomer().getCustomerId());
//			cr.setTempCash(buyOrder.getCustomer().getCash() - buyOrder.getTransactionAmount(),
//					buyOrder.getCustomer().getCustomerId());
			
			List<Position> positions = pr.findByFund_FundIdAndCustomer_CustomerId(buyOrder.getFund().getFundId(),
					buyOrder.getCustomer().getCustomerId());
			if (positions.size() == 0) {
				Position position = new Position(buyOrder.getTransactionAmount() / sharePrice, buyOrder.getShares(),
						buyOrder.getCustomer(), buyOrder.getFund());
				pr.save(position);
			} else {
				Position position = positions.get(0);
				position.setShares(position.getShares() + (buyOrder.getTransactionAmount() / sharePrice));
				position.setTempShares(position.getShares());
				pr.save(position);
//				pr.setShares(position.getShares() + (buyOrder.getTransactionAmount() / sharePrice),
//						buyOrder.getCustomer().getCustomerId(), buyOrder.getFund().getFundId());
//				pr.setTempShares(position.getShares() + (buyOrder.getTransactionAmount() / sharePrice),
//						buyOrder.getCustomer().getCustomerId(), buyOrder.getFund().getFundId());
			}
			
		}
		
		for (Transaction sellOrder : sellOrders) {
			List<Position> positions = pr.findByFund_FundIdAndCustomer_CustomerId(sellOrder.getFund().getFundId(),
					sellOrder.getCustomer().getCustomerId());
			Position position = positions.get(0);
			if (position.getShares() < sellOrder.getShares()) {
				model.addAttribute("error", "Customer has insufficient shares to process sell order");
				return "error";
			}
			double sharePrice = fundCurrPrices.get(sellOrder.getFund().getFundId());
			System.out.println("======== Sell" + sharePrice + "========");
			sellOrder.setExecuteDate(transitionDate);
			sellOrder.setSharePrice(sharePrice);
			sellOrder.setTransactionAmount(sharePrice * sellOrder.getShares());
			sellOrder.setStatus("Clear");
			tr.save(sellOrder);
//			tr.setExecuteDate(transitionDate, sellOrder.getTransactionId());
//			tr.setSharePrice(sharePrice, sellOrder.getTransactionId());
//			System.out.println(sellOrder.getTransactionId());
//			tr.setTransactionAmount(sharePrice * sellOrder.getShares(), sellOrder.getTransactionId());
//			tr.setStatus("Clear", sellOrder.getTransactionId());
			
			Customer customer = cr.findOne(sellOrder.getCustomer().getCustomerId());
			customer.setCash(customer.getCash() + sellOrder.getTransactionAmount());
			customer.setTempCash(customer.getCash());
			cr.save(customer);
//			cr.setCash(sellOrder.getCustomer().getCash() + sellOrder.getTransactionAmount(),
//					sellOrder.getCustomer().getCustomerId());
//			cr.setTempCash(sellOrder.getCustomer().getCash() - sellOrder.getTransactionAmount(),
//					sellOrder.getCustomer().getCustomerId());
			
			position.setShares(position.getShares() - (sellOrder.getTransactionAmount() / sharePrice));
			position.setTempShares(position.getShares());
			pr.save(position);
//			pr.setShares(position.getShares() - sellOrder.getShares(),
//					sellOrder.getCustomer().getCustomerId(), sellOrder.getFund().getFundId());
//			pr.setTempShares(position.getShares() - sellOrder.getShares(),
//					sellOrder.getCustomer().getCustomerId(), sellOrder.getFund().getFundId());
		}
		
		for (Transaction requestCheck : requestChecks) {
			if (requestCheck.getCustomer().getCash() < requestCheck.getTransactionAmount()) {
				model.addAttribute("error", "Customer has insufficient cash balance to process check request");
				return "error";
			}
			requestCheck.setExecuteDate(transitionDate);
			requestCheck.setStatus("Clear");
			tr.save(requestCheck);
//			tr.setExecuteDate(transitionDate, requestCheck.getTransactionId());
//			tr.setTransactionAmount(requestCheck.getTransactionAmount(), requestCheck.getTransactionId());
//			tr.setStatus("Clear", requestCheck.getTransactionId());
			
			Customer customer = cr.findOne(requestCheck.getCustomer().getCustomerId());
			customer.setCash(customer.getCash() - requestCheck.getTransactionAmount());
			customer.setTempCash(customer.getCash());
			cr.save(customer);
//			cr.setCash(requestCheck.getCustomer().getCash() - requestCheck.getTransactionAmount(),
//					requestCheck.getCustomer().getCustomerId());
//			cr.setTempCash(requestCheck.getCustomer().getCash() - requestCheck.getTransactionAmount(),
//					requestCheck.getCustomer().getCustomerId());
		}
		
		for (Transaction depositCheck : depositChecks) {
			depositCheck.setExecuteDate(transitionDate);
			depositCheck.setStatus("Clear");
			tr.save(depositCheck);
//			tr.setExecuteDate(transitionDate, depositCheck.getTransactionId());
//			tr.setTransactionAmount(depositCheck.getTransactionAmount(), depositCheck.getTransactionId());
//			tr.setStatus("Clear", depositCheck.getTransactionId());
			
			Customer customer = cr.findOne(depositCheck.getCustomer().getCustomerId());
			customer.setCash(customer.getCash() + depositCheck.getTransactionAmount());
			customer.setTempCash(customer.getCash());
			cr.save(customer);
//			cr.setCash(depositCheck.getCustomer().getCash() + depositCheck.getTransactionAmount(),
//					depositCheck.getCustomer().getCustomerId());
//			cr.setTempCash(depositCheck.getCustomer().getCash() + depositCheck.getTransactionAmount(),
//					depositCheck.getCustomer().getCustomerId());
		}
		
		return "transitionSuccessEmployee";
	}
		

	@GetMapping("/employeeChangePassword")
    public String employeeChangePasswordPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee e = (Employee) session.getAttribute("employee");
        if (e == null) {
            model.addAttribute("error", "Please log in first to change password.");
            return "error";
        }
        model.addAttribute("employee", e);
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "employeeChangePassword";
    }
    
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @PostMapping("/employeeChangePassword")
    public String  changePassword(@Valid ChangePasswordForm changePasswordForm, BindingResult bindingResult, HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:employeeChangePassword";
        }
        
        HttpSession session = request.getSession();
        Employee e= (Employee) session.getAttribute("employee");
        if (e == null) {
            model.addAttribute("error", "Please log in first to change password.");
            return "error";
        }
        
        if (!changePasswordForm.getCurrentPassword().equals(e.getPassword())) {
            model.addAttribute("error", "Current password is not correct.");
            return "error";
        } else if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {
            model.addAttribute("error", "New password does not match.");
            return "error";
        } else if (changePasswordForm.getCurrentPassword().equals(changePasswordForm.getNewPassword())) {
            model.addAttribute("error", "New password cannot be the same as current password.");
            return "error";
        }  else if (fs.invalid(changePasswordForm.getCurrentPassword())) {
            model.addAttribute("error", "Current password may not contain special character.");
            return "error";
        } else if (fs.invalid(changePasswordForm.getNewPassword())) {
            model.addAttribute("error", "New password may not contain special character.");
            return "error";
        } else if (fs.invalid(changePasswordForm.getConfirmPassword())) {
            model.addAttribute("error", "Confirm password may not contain special character.");
            return "error";
        } else if (bs.buttonMismatch(changePasswordForm.getButton(), "EmployeeChangePassword")) {
            model.addAttribute("error", "The action must equal to change password");
            return "error";
        } else {
            e.setPassword(changePasswordForm.getNewPassword());
            System.out.println("changed to:" + e.getPassword());
            er.save(e);
            return "redirect:employee";       
        }
        
    }
    
    @GetMapping("/employeeLogout")
    public String employeeLogout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("employee", null);
        return "redirect:home";
    }
	
}
