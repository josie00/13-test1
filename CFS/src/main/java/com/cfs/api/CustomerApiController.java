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
import com.cfs.repository.CustomerRepository;
import com.cfs.repository.FundRepository;


@RestController
public class CustomerApiController {

	@Autowired
	FundRepository fr;
	
	@Autowired
	CustomerRepository cr;

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

}
