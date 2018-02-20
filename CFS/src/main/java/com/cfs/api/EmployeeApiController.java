package com.cfs.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
	public synchronized @ResponseBody ResponseEntity<Map<String, String>> createCustomer(@RequestBody Map<String, String> map, HttpServletRequest request){
		System.out.println("----------------Starting create customer-------------------");
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();
		String type = (String) session.getAttribute("type");
		
		String username = map.get("username");
		System.out.println("Input: username = " + username);
		if (username == null || username.trim().equals("")) {
			System.out.println("username is null or empty");
			return ResponseEntity.badRequest().body(res);
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
		System.out.println("Input: fname = " + fname + " lname = " + lname + " address = " + address + "city = " +city+ " state = "+state+" zip = "+zip+" email= "+email+" password = "+ password + " cash = "+cash);
		if (fname == null || fname.trim().equals("")) {
			System.out.println("fname is null or empty");
			return ResponseEntity.badRequest().body(res);
		}
        if (lname == null || lname.trim().equals("")) {
        	System.out.println("lname is null or empty");
        	return ResponseEntity.badRequest().body(res);
        }
        if (address == null || address.trim().equals("")) {
        	System.out.println("address is null or empty");
        	return ResponseEntity.badRequest().body(res);
        }
        if (city == null || city.trim().equals("")) {
        	System.out.println("city is null or empty");
        	return ResponseEntity.badRequest().body(res);
        }
        if (state == null || state.trim().equals("")) {
        	System.out.println("state is null or empty");
        	return ResponseEntity.badRequest().body(res);
        }
        if (zip == null || zip.trim().equals("")) {
        	System.out.println("zip is null or empty");
        	return ResponseEntity.badRequest().body(res);
        }
        if (email == null || email.trim().equals("")) {
        	System.out.println("email is null or empty");
        	return ResponseEntity.badRequest().body(res);
        }
        if (password == null || password.trim().equals("")) {
        	System.out.println("password is null or empty");
        	return ResponseEntity.badRequest().body(res);
        }
        if (cash == null) {
        	System.out.println("cash is null");
        	return ResponseEntity.badRequest().body(res);
        }

        if (type == null) {
			res.put("message", "You are not currently logged in");
			System.out.println("not logged in");
			System.out.println(res.get("message"));
			return ResponseEntity.ok(res);
		}else if (type.equals("customer")) {
			res.put("message", "You must be an employee to perform this action");
			System.out.println("not an employee");
			System.out.println(res.get("message"));
			return ResponseEntity.ok(res);
		}
        
        List<Customer> customers = cr.findByUserName(username);
		if (customers.size() != 0) {
			res.put("message", "The input you provided is not valid");
			System.out.println("username already exist");
			System.out.println(res.get("message"));
			return ResponseEntity.ok(res);
		}
        double value = 0;
		if (!cash.trim().equals("")) {
			value = Double.parseDouble(cash);
			System.out.println("parse cash: " + value);
		}
		Customer c = new Customer(username, password, fname, lname, address, city, state, zip, email, value, 0, null, null);
		cr.save(c);
		res.put("message", c.getFirstName()+" was registered successfully");
		System.out.println(res.get("message"));
		System.out.println("create customer success");
		return ResponseEntity.ok(res);
	}
	
	@RequestMapping(value = "/createFund", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> createFund(@RequestBody Map<String, String> map, HttpServletRequest request) {
		System.out.println("---------------------Starting createFund------------------");
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();

		String name = map.get("name");
		String symbol = map.get("symbol");
		String initial_value = map.get("initial_value");
		System.out.println("Input: name = " + name + " symbol = " + symbol + " initial Value = " + initial_value);
		if (name == null || name.trim().equals("")) {
			System.out.println("name is null or empty");
			return ResponseEntity.badRequest().body(res);
		}
        if (symbol == null || symbol.trim().equals("")) {
        	System.out.println("symbol is null or empty");
        	return ResponseEntity.badRequest().body(res);
        }
        if (initial_value == null || initial_value.trim().equals("")) {
        	System.out.println("initial value is null or empty");
        	return ResponseEntity.badRequest().body(res);
        }
		
		// Check session
		if (session.getAttribute("type") == null) {
			res.put("message", "You are not currently logged in");
			System.out.println("not logged in");
			System.out.println(res.get("message"));
			return ResponseEntity.ok(res);
		}
		
		// Check if employee
		if (! ((String) session.getAttribute("type")).equals("employee")) {
			res.put("message", "You must be an employee to perform this action");
			System.out.println("not an employee");
			System.out.println(res.get("message"));
			return ResponseEntity.ok(res);
		}

        List<Fund> funds = fr.findBySymbolAndName(symbol, name);
        if (funds.size() != 0) {
        	res.put("message", "The fund was successfully created");
        	System.out.println("fund exist, success");
        	System.out.println(res.get("message"));
			return ResponseEntity.ok(res);
        }

        double value =  Double.parseDouble(initial_value);
        if (value < 0.005) {
        	System.out.println("input initial value is too small");
        	return ResponseEntity.badRequest().body(res);
        }
		
		Fund fund = new Fund(name, symbol, value, null, null);
		fr.save(fund);
		res.put("message", "The fund was successfully created");
		System.out.println(res.get("message"));
		System.out.println("fund created successfully");
		return ResponseEntity.ok(res);
	}
	
	@RequestMapping(value = "/transitionDay", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> transitionDay(HttpServletRequest request) {
		System.out.println("----------------Starting transitionDay----------------");
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();

		// Check session
		if (session.getAttribute("type") == null) {
			res.put("message", "You are not currently logged in");
			System.out.println("not logged in");
			System.out.println(res.get("message"));
			return ResponseEntity.ok(res);
		}
		
		// Check if employee
		if (! ((String) session.getAttribute("type")).equals("employee")) {
			res.put("message", "You must be an employee to perform this action");
			System.out.println("not an employee");
			System.out.println(res.get("message"));
			return ResponseEntity.ok(res);
		}
		
		// Update fund price
		Iterable<Fund> funds = fr.findAll();
		for (Fund fund : funds) {
			System.out.println("---fund name: " + fund.getName());
			double oldPrice = fund.getCurrPrice();
			System.out.println("old price: " + oldPrice);
			double newPrice = ThreadLocalRandom.current().nextDouble(oldPrice * 0.9, oldPrice * 1.1);
			System.out.println("new price: " + newPrice);
			while (newPrice < 0.005) {
				System.out.println("price generated is too small, create new one");
				newPrice = ThreadLocalRandom.current().nextDouble(oldPrice * 0.9, oldPrice * 1.1);
			}
			System.out.println("final new price: " + newPrice);
			fund.setCurrPrice(newPrice);
			fr.save(fund);
		}
		res.put("message", "The fund prices have been successfully recalculated");
		System.out.println(res.get("message"));
		System.out.println("transition day success");
		return ResponseEntity.ok(res);
	}
}
