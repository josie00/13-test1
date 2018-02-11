package com.cfs.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfs.databean.Customer;
import com.cfs.databean.Fund;
import com.cfs.databean.Position;
import com.cfs.repository.CustomerRepository;
import com.cfs.repository.FundPriceHistoryRepository;
import com.cfs.repository.FundRepository;
import com.cfs.repository.PositionRepository;
import com.cfs.repository.TransactionRepository;


@RestController
public class CustomerApiController {

	@Autowired
	FundRepository fr;
	
	@Autowired
	CustomerRepository cr;
	
	@Autowired
	TransactionRepository tr;
	
	@Autowired
	PositionRepository pr;
	
	
	
	
	

	// A get example
	@RequestMapping(value = "/oneFund", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getAllFund(Model model, HttpServletRequest request) {
		Fund fund = fr.findOne((long) 1);
		Map<String, String> map = new HashMap<>();
		map.put("message", "success");

		return map;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> login(@RequestBody Map<String, String> map, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = map.get("username");
		String password = map.get("password");
		
		List<Customer> customers = cr.findByUserName(username);
		Map<String, String> res = new HashMap<String,String>();
		if (customers.size() != 0) {
			Customer c = customers.get(0);
			if (password.equals(c.getPassword())) {
				session.setAttribute("customer", c);
				res.put("message", "Welcome " + c.getFirstName());
			} else {
				res.put("message", "There seems to be an issue with the username/password combination that you entered");
			}

		} else {
			res.put("message", "There seems to be an issue with the username/password combination that you entered");
		}
		
		return res;

	}
	
	@RequestMapping(value = "/buyFund", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> buyFund(@RequestBody Map<String, String> map, HttpServletRequest request) {
		Map<String, String> res = new HashMap<String,String>();
		HttpSession session = request.getSession();
		String type = (String) session.getAttribute("type");
		String symbol = map.get("symbol");
		String cashValue = map.get("cashValue");
		double amount = Double.parseDouble(cashValue);
		if (type == null) {
			res.put("message", "You are not currently logged in");
			return res;
		} else if (type.equals("employee")) {
			res.put("message", "You must be a customer to perform this action");
			return res;
		}
		Customer c = (Customer) session.getAttribute("customer");
		if (c.getCash() < amount) {
			res.put("message", "You don’t have enough cash in your account to make this purchase");
			return res;
		}
		List<Fund> funds = fr.findBySymbol(symbol);
		if (funds == null || funds.size() == 0) {
			res.put("message", "The fund you provided does not exist");
			return res;
		}
		
		Fund fund  = funds.get(0);
		double price = fund.getCurrPrice();
		if (price > amount) {
			res.put("message", "You didn’t provide enough cash to make this purchase");
			return res;
		}	
		int mod = ((int)(amount*100)) % ((int)(price*100));
		if (mod > 0) {
			amount = amount - ((double)mod)/100;
		}
		int shares = (int)(amount/price);
		c.setCash(c.getCash()-amount);	
		cr.save(c);
		List<Position> pos = pr.findByFund_FundIdAndCustomer_CustomerId(fund.getFundId(), c.getCustomerId());
		if (pos.size() > 0) {
			Position p = pos.get(0);
			p.setShares(p.getShares()+shares);	
			pr.save(p);
		} else {
			Position p = new Position(shares, 0, c, fund);
			pr.save(p);
		}
		res.put("message", "The fundhas been successfully purchased");	
		return res;	
	}
	
	@RequestMapping(value = "/sellFund", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> sellFund(@RequestBody Map<String, String> map, HttpServletRequest request) {
		Map<String, String> res = new HashMap<String,String>();
		HttpSession session = request.getSession();
		String type = (String) session.getAttribute("type");
		String symbol = map.get("symbol");
		String numShares = map.get("numShares");
		double shares = Double.parseDouble(numShares);
		if (type == null) {
			res.put("message", "You are not currently logged in");
			return res;
		} else if (type.equals("employee")) {
			res.put("message", "You must be a customer to perform this action");
			return res;
		}
		Customer c = (Customer) session.getAttribute("customer");
		
		List<Fund> funds = fr.findBySymbol(symbol);
		if (funds == null || funds.size() == 0) {
			res.put("message", "The fund you provided does not exist");
			return res;
		}
		
		Fund fund  = funds.get(0);
		List<Position> pos = pr.findByFund_FundIdAndCustomer_CustomerId(fund.getFundId(), c.getCustomerId());
		
		if (pos.size() == 0 || pos.get(0).getShares() < shares) {
			res.put("message", "You don’t have that many shares in your portfolio");
			return res;
		}
		Position p = pos.get(0);
		double amount = shares * fund.getCurrPrice();
		c.setCash(c.getCash()+amount);	
		cr.save(c);
		p.setShares(p.getShares()-shares);
		pr.save(p);
		
		return res;
	}
	

}
