package com.cfs.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cfs.databean.Customer;
import com.cfs.databean.Employee;
import com.cfs.databean.Fund;
import com.cfs.databean.Position;
import com.cfs.formbean.FundForm;
import com.cfs.formbean.PortfolioForm;

import com.cfs.repository.CustomerRepository;

import com.cfs.repository.EmployeeRepository;
import com.cfs.repository.FundRepository;
import com.cfs.repository.PositionRepository;

import com.cfs.repository.TransactionRepository;

@Controller
@RestController
public class CustomerApiController {

	@Autowired
	FundRepository fr;
	
	@Autowired
	CustomerRepository cr;
	
	@Autowired
	PositionRepository pr;

	@Autowired
	TransactionRepository tr;

	@Autowired
	EmployeeRepository er;

	// A get example
	@RequestMapping(value = "/oneFund", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getAllFund(Model model, HttpServletRequest request) {
		Fund fund = fr.findOne((long) 1);
		Map<String, String> map = new HashMap<>();
		map.put("message", "success");

		return map;
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> map, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = map.get("username");
		String password = map.get("password");
		
		Map<String, String> res = new HashMap<String,String>();
        
        // Missing input
        if (username == null || username.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (password == null || password.trim().equals("")) return ResponseEntity.badRequest().body(res);
		
		List<Customer> customers = cr.findByUserName(username);
		List<Employee> employees = er.findByUserName(username);
		if (customers.size() != 0) {
			Customer c = customers.get(0);
			if (password.equals(c.getPassword())) {
				session.setAttribute("user", c);
				session.setAttribute("type", "customer");
				res.put("message", "Welcome " + c.getFirstName());
				return ResponseEntity.ok(res);
			} 

		} 
		
		if (employees.size()!= 0) {
			System.out.println("employee");
			Employee e = employees.get(0);
			if(password.equals(e.getPassword())) {
				session.setAttribute("user", e);
				session.setAttribute("type", "employee");
				res.put("message", "Welcome " + e.getFirstName());
				return ResponseEntity.ok(res);
			} 
		}
		
		res.put("message", "There seems to be an issue with the username/password combination that you entered");
		return ResponseEntity.ok(res);
	}
	

	@RequestMapping(value = "/buyFund", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> buyFund(@RequestBody Map<String, String> map, HttpServletRequest request) {
		Map<String, String> res = new HashMap<String,String>();
		HttpSession session = request.getSession();
		String type = (String) session.getAttribute("type");
		String symbol = map.get("symbol");
		String cashValue = map.get("cashValue");
		
		// Missing input
        if (symbol == null || symbol.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (cashValue == null || cashValue.trim().equals("")) return ResponseEntity.badRequest().body(res);
		
		double amount = Double.parseDouble(cashValue);
		if (type == null) {
			res.put("message", "You are not currently logged in");
			return ResponseEntity.ok(res);
		} else if (type.equals("employee")) {
			res.put("message", "You must be a customer to perform this action");
			return ResponseEntity.ok(res);
		}
		Customer c = (Customer) session.getAttribute("user");
		if (c.getCash() < amount) {
			System.out.println("cash : " +c.getCash());
			res.put("message", "You don’t have enough cash in your account to make this purchase");
			return ResponseEntity.ok(res);
		}
		List<Fund> funds = fr.findBySymbol(symbol);
		if (funds == null || funds.size() == 0) {
			res.put("message", "The fund you provided does not exist");
			return ResponseEntity.ok(res);
		}
		
		Fund fund  = funds.get(0);
		double price = fund.getCurrPrice();
		if (price > amount) {
			
			res.put("message", "You didn’t provide enough cash to make this purchase");
			return ResponseEntity.ok(res);
		}	
		int mod = ((int)(amount*100)) % ((int)(price*100));
		if (mod > 0) {
			amount = amount - ((double)mod)/100;
		}
		int shares = (int)(amount/price);
		c.setCash(c.getCash()-amount);	
		cr.save(c);
		session.setAttribute("user", c);
		List<Position> pos = pr.findByFund_FundIdAndCustomer_CustomerId(fund.getFundId(), c.getCustomerId());
		if (pos.size() > 0) {
			Position p = pos.get(0);
			p.setShares(p.getShares()+shares);	
			pr.save(p);
		} else {
			System.out.println("tests");	
			Position p = new Position(shares, 0, c, fund);
			pr.save(p);
		}
		res.put("message", "The fund has been successfully purchased");	
		return ResponseEntity.ok(res);
	}
	
	@Transactional
	@RequestMapping(value = "/sellFund", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> sellFund(@RequestBody Map<String, String> map, HttpServletRequest request) {
		Map<String, String> res = new HashMap<String,String>();
		HttpSession session = request.getSession();
		String type = (String) session.getAttribute("type");
		String symbol = map.get("symbol");
		String numShares = map.get("numShares");
		
		// Missing input
        if (symbol == null || symbol.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (numShares == null || numShares.trim().equals("")) return ResponseEntity.badRequest().body(res);
		
		double shares = Double.parseDouble(numShares);
		if (type == null) {
			res.put("message", "You are not currently logged in");
			return ResponseEntity.ok(res);
		} else if (type.equals("employee")) {
			res.put("message", "You must be a customer to perform this action");
			return ResponseEntity.ok(res);
		}
		Customer c = (Customer) session.getAttribute("user");
		
		List<Fund> funds = fr.findBySymbol(symbol);
		if (funds == null || funds.size() == 0) {
			res.put("message", "The fund you provided does not exist");
			return ResponseEntity.ok(res);
		}
		
		Fund fund  = funds.get(0);
		List<Position> pos = pr.findByFund_FundIdAndCustomer_CustomerId(fund.getFundId(), c.getCustomerId());
		
		if (pos.size() == 0 || pos.get(0).getShares() < shares) {
			res.put("message", "You don’t have that many shares in your portfolio");
			return ResponseEntity.ok(res);
		}
		Position p = pos.get(0);
		double amount = shares * fund.getCurrPrice();
		c.setCash(c.getCash()+amount);	
		cr.save(c);
		session.setAttribute("user", c);
		p.setShares(p.getShares()-shares);
		pr.save(p);
		res.put("message", "The shares have been successfully sold");
		return ResponseEntity.ok(res);
	}

	
	@RequestMapping(value = "/depositCheck", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Map<String, String>> depositCheck(@RequestBody Map<String, String> map, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String username = map.get("username");
        String cash = map.get("cash");
        Map<String, String> res = new HashMap<String,String>();
        Customer c = null;
        Employee e = (Employee) session.getAttribute("user");
        
        // Missing input
        if (username == null || username.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (cash == null || cash.trim().equals("")) return ResponseEntity.badRequest().body(res);
        
        Double amount = Double.parseDouble(cash);
        
        List<Customer> customers = cr.findByUserName(username);
        if (customers.size() == 0) {
            res.put("message", "There seems to be an issue with the username that you entered");
            return ResponseEntity.ok(res);
        } else {
            c = customers.get(0);   
            String type = (String) session.getAttribute("type");
            if (type == null) {
                res.put("message", "You are not currently logged in");
                return ResponseEntity.ok(res);
            } else if (e == null) {
                res.put("message", "You must be an employee to perform this action");
                return ResponseEntity.ok(res);
            } else {
                c.setCash(c.getCash() + amount);
                res.put("message", "The check was successfully deposited");
                return ResponseEntity.ok(res);
            }
        }
	}


	@RequestMapping(value = "/viewPortfolio", method = RequestMethod.GET)
	public @ResponseBody Object viewPortfolio(HttpServletRequest request){
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();
		String type = (String) session.getAttribute("type");
		if (type == null) {
			res.put("message", "You are not currently logged in”}");
			return ResponseEntity.ok(res);
		}else if (type.equals("employee")) {
			res.put("message", "You must be a customer to perform this action");
			return ResponseEntity.ok(res);
		}
		Customer c = (Customer)session.getAttribute("user");
		PortfolioForm portfolio = new PortfolioForm();
		List<Position> positions = pr.findByCustomer_CustomerId(c.getCustomerId());
		if (positions.size() != 0) {
			for (Position p: positions) {
				if (p.getShares() > 0) {
					FundForm f = new FundForm();
					f.setName(p.getFund().getName());
					f.setPrice(String.valueOf(p.getFund().getCurrPrice()));
					f.setShares(String.valueOf(p.getShares()));
					portfolio.addFund(f);
				}
			}
		}
		portfolio.setMessage("The action was successful");
		portfolio.setCash(String.valueOf(c.getCash()));
		return ResponseEntity.ok(portfolio);
	}
	

	@RequestMapping(value = "/requestCheck", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Map<String, String>> requestCheck(@RequestBody Map<String, String> map, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
        String cashValue = map.get("cashValue");
        Map<String, String> res = new HashMap<String,String>();
        Customer c = (Customer) session.getAttribute("user");
        
        // Missing input
        if (cashValue == null || cashValue.trim().equals("")) return ResponseEntity.badRequest().body(res);
        Double amount = Double.parseDouble(cashValue);

        String type = (String) session.getAttribute("type");
        if (type == null) {
            res.put("message", "You are not currently logged in");
            return ResponseEntity.ok(res);
        } else if (c == null) {
             res.put("message", "You must be a customer to perform this action");
             return ResponseEntity.ok(res);
        } else if (c.getCash() < amount) {
            res.put("message", "You don't have sufficient funds in your account to cover the requested check");
            return ResponseEntity.ok(res);
        } else {
            c.setCash(c.getCash() - amount);
            session.setAttribute("user", c);
            res.put("message", "The check was successfully requested");
            return ResponseEntity.ok(res);
        }      
        
    }

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();	
		String type = (String)session.getAttribute("type");
		if(type == null) {
			res.put("message", "You are not currently logged in");
			return ResponseEntity.ok(res);
		}
		session.setAttribute("type", null);
		session.setAttribute("user", null);
		res.put("message", "You have been successfully logged out");
	
		return ResponseEntity.ok(res);
	}
}
