package com.cfs.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cfs.databean.Customer;
import com.cfs.repository.CustomerRepository;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.RestController;

import com.cfs.databean.Fund;
import com.cfs.repository.EmployeeRepository;
import com.cfs.repository.FundPriceHistoryRepository;
import com.cfs.repository.FundRepository;

@RestController
public class EmployeeApiController {
	
	@Autowired
	CustomerRepository cr;
	
	@Autowired
	FundRepository fr;
	
	@Autowired
	EmployeeRepository er;
	
	@Autowired
	FundPriceHistoryRepository fphr;

	@RequestMapping(value = "/createCustomerAccount", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> createCustomer(@RequestBody Map<String, String> map, HttpServletRequest request){
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();
		String type = (String) session.getAttribute("type");
		if (type == null) {
			res.put("message", "You are not currently logged in");
			return ResponseEntity.ok(res);
		}else if (type.equals("customer")) {
			res.put("message", "You must be an employee to perform this action");
			return ResponseEntity.ok(res);
		}
		String username = map.get("username");
		if (username == null || username.trim().equals("")) return ResponseEntity.badRequest().body(res);
		
		List<Customer> customers = cr.findByUserName(username);
		if (customers.size() != 0) {
			res.put("message", "The input you provided is not valid");
			return ResponseEntity.ok(res);
		}
		String fname = map.get("fname");
		String lname = map.get("lname");
		String address = map.get("address");
		String city = map.get("city");
		String state = map.get("state");
		String zip = map.get("zip");
		String email = map.get("email");
		String password = map.get("password");
		String cash = map.get("cash");
		if (fname == null || fname.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (lname == null || lname.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (address == null || address.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (city == null || city.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (state == null || state.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (zip == null || zip.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (email == null || email.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (password == null || password.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (cash == null || cash.trim().equals("")) return ResponseEntity.badRequest().body(res);
		
        double value = Double.parseDouble(cash);

		Customer c = new Customer(username, password, fname, lname, address, city, state, zip, email, value, 0, null, null);
		cr.save(c);
		res.put("message", c.getFirstName()+" was registered successfully");
		return ResponseEntity.ok(res);
	}
	
	@RequestMapping(value = "/createFund", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> createFund(@RequestBody Map<String, String> map, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();

		// Check session
		if (session.getAttribute("type") == null) {
			res.put("message", "You are not currently logged in");
			return ResponseEntity.ok(res);
		}
		
		// Check if employee
		if (! ((String) session.getAttribute("type")).equals("employee")) {
			res.put("message", "You must be an employee to perform this action");
			return ResponseEntity.ok(res);
		}

		String name = map.get("name");
		String symbol = map.get("symbol");
		String initial_value = map.get("initial_value");
		if (name == null || name.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (symbol == null || symbol.trim().equals("")) return ResponseEntity.badRequest().body(res);
        if (initial_value == null || initial_value.trim().equals("")) return ResponseEntity.badRequest().body(res);
		
        List<Fund> funds = fr.findBySymbolAndName(symbol, name);
        if (funds.size() != 0) {
        		res.put("message", "The fund was successfully created");
			return ResponseEntity.ok(res);
        }

        double value =  Double.parseDouble(initial_value);
        if (value < 0.005) return ResponseEntity.badRequest().body(res);
		
		Fund fund = new Fund(name, symbol, value, null, null);
		fr.save(fund);
		res.put("message", "The fund was successfully created");
		return ResponseEntity.ok(res);
	}
	
	@RequestMapping(value = "/transitionDay", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> transitionDay(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();

		// Check session
		if (session.getAttribute("type") == null) {
			res.put("message", "You are not currently logged in");
			return ResponseEntity.ok(res);
		}
		
		// Check if employee
		if (! ((String) session.getAttribute("type")).equals("employee")) {
			res.put("message", "You must be an employee to perform this action");
			return ResponseEntity.ok(res);
		}
		
		// Update fund price
		Iterable<Fund> funds = fr.findAll();
		for (Fund fund : funds) {
			double oldPrice = fund.getCurrPrice();
			double newPrice = ThreadLocalRandom.current().nextDouble(oldPrice * 0.9, oldPrice * 1.1);
			while (newPrice < 0.005) {
				newPrice = ThreadLocalRandom.current().nextDouble(oldPrice * 0.9, oldPrice * 1.1);
			}
			fund.setCurrPrice(newPrice);
			fr.save(fund);
		}
		res.put("message", "The fund prices have been successfully recalculated");
		return ResponseEntity.ok(res);
	}
}
