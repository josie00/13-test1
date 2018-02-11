package com.cfs.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfs.databean.Fund;
import com.cfs.databean.FundPriceHistory;
import com.cfs.repository.EmployeeRepository;
import com.cfs.repository.FundPriceHistoryRepository;
import com.cfs.repository.FundRepository;

@RestController
public class EmployeeApiController {

	@Autowired
	FundRepository fr;
	
	@Autowired
	EmployeeRepository er;
	
	@Autowired
	FundPriceHistoryRepository fphr;
	
	@RequestMapping(value = "/createFund", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> createFund(@RequestBody Map<String, String> map, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();

		// Check session
		if (session.getAttribute("type") == null) {
			res.put("message", "You are not currently logged in");
			return res;
		}
		
		// Check if employee
		if (! ((String) session.getAttribute("type")).equals("employee")) {
			res.put("message", "You must be an employee to perform this action");
			return res;
		}
		
		String name = map.get("name");
		String symbol = map.get("symbol");
		String initial_value = map.get("initial_value");
		
		// what message to send if failed parse / less than 0?
		double value = 0;
		try {
			value = Double.parseDouble(initial_value);
			if (value < 0.005) {
				res.put("message", "????");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		Fund fund = new Fund(name, symbol, value, null, null);
		fr.save(fund);
		res.put("message", "The fund was successfully created");
		
		return res;
	}
	
	@RequestMapping(value = "/transitionDay", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> transitionDay(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();

		// Check session
		if (session.getAttribute("type") == null) {
			res.put("message", "You are not currently logged in");
			return res;
		}
		
		// Check if employee
		if (! ((String) session.getAttribute("type")).equals("employee")) {
			res.put("message", "You must be an employee to perform this action");
			return res;
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
		return res;
	}
}
