package com.cfs.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfs.databean.Fund;
import com.cfs.repository.EmployeeRepository;
import com.cfs.repository.FundRepository;

@RestController
public class EmployeeApiController {

	@Autowired
	FundRepository fr;
	
	@Autowired
	EmployeeRepository er;
	
	@RequestMapping(value = "/createFund", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> login(@RequestBody Map<String, String> map, HttpServletRequest request) {
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
		
		// what message to send if failed parse?
		double value = 0;
		try {
			value = Double.parseDouble(initial_value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		Fund fund = new Fund(name, symbol, value, null, null);
		fr.save(fund);
		res.put("message", "The fund was successfully created");
		
		return res;
	}
}
