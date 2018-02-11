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
import com.cfs.databean.Employee;
import com.cfs.databean.Fund;
import com.cfs.repository.CustomerRepository;
import com.cfs.repository.EmployeeRepository;
import com.cfs.repository.FundRepository;


@RestController
public class CustomerApiController {

	@Autowired
	FundRepository fr;
	
	@Autowired
	CustomerRepository cr;
	
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
	public @ResponseBody Map<String, String> login(@RequestBody Map<String, String> map, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = map.get("username");
		String password = map.get("password");
		
		
		List<Customer> customers = cr.findByUserName(username);
		List<Employee> employees = er.findByUserName(username);
		Map<String, String> res = new HashMap<String,String>();
		if (customers.size() != 0) {
			Customer c = customers.get(0);
			if (password.equals(c.getPassword())) {
				session.setAttribute("user", c);
				session.setAttribute("type", "customer");
				res.put("message", "Welcome " + c.getFirstName());
				return res;
			} 

		} 
		
		if (employees.size()!= 0) {
			System.out.println("employee");
			Employee e = employees.get(0);
			if(password.equals(e.getPassword())) {
				session.setAttribute("user", e);
				session.setAttribute("type", "employee");
				res.put("message", "Welcome " + e.getFirstName());
				return res;
			} 
		}
		
		res.put("message", "There seems to be an issue with the username/password combination that you entered");
		return res;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();	
		String type = (String)session.getAttribute("type");
		if(type == null) {
			res.put("message", "You are not currently logged in");
		}
		session.setAttribute("type", null);
		session.setAttribute("user", null);
		res.put("message", "You have been successfully logged out");
		
		return res;
	}
	

	
}
