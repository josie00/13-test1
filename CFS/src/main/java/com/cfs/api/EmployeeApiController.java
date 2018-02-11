package com.cfs.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cfs.databean.Customer;
import com.cfs.repository.CustomerRepository;

@Controller
public class EmployeeApiController {
	
	@Autowired
	CustomerRepository cr;

	@RequestMapping(value = "/createCustomerAccount", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> createCustomer(@RequestBody Map<String, String> map, HttpServletRequest request){
		HttpSession session = request.getSession();
		Map<String, String> res = new HashMap<String,String>();
		/*String type = (String) session.getAttribute("type");
		if (type == null) {
			res.put("message", "You are not currently logged in");
			return res;
		}else if (type.equals("customer")) {
			res.put("message", "You must be an employee to perform this action");
			return res;
		}*/
		String username = map.get("username");
		if (username == null || username.length() == 0) {
			res.put("message", "missing value");
			return res;
		}
		List<Customer> customers = cr.findByUserName(username);
		if (customers.size() != 0) {
			res.put("message", "The input you provided is not valid");
			return res;
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
		double value;
		if (fname == null || fname.length() == 0) {
			res.put("message", "missing value");
			return res;
		}
		if (lname == null || lname.length() == 0) {
			res.put("message", "missing value");
			return res;
		}
		if (address == null || address.length() == 0) {
			res.put("message", "missing value");
			return res;
		}
		if (city == null || city.length() == 0) {
			res.put("message", "missing value");
			return res;
		}
		if (state == null || state.length() == 0) {
			res.put("message", "missing value");
			return res;
		}
		if (zip == null || zip.length() == 0) {
			res.put("message", "missing value");
			return res;
		}
		if (email == null || email.length() == 0) {
			res.put("message", "missing value");
			return res;
		}
		if (password == null || password.length() == 0) {
			res.put("message", "missing value");
			return res;
		}
		if (cash == null || cash.length() == 0) {
			value = 0;
		}else {
			value = Double.parseDouble(cash);
		}
		Customer c = new Customer(username, password, fname, lname, address, city, state, zip, email, value, 0, null, null);
		cr.save(c);
		res.put("message", "fname was registered successfully");
		return res;
	}
}
