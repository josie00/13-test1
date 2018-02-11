package com.cfs.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfs.databean.Customer;
import com.cfs.databean.Fund;
import com.cfs.repository.FundRepository;





@RestController
public class CustomerApiController {

	@Autowired
	FundRepository fr;

	//A get example
	@GetMapping("/oneFund") 
	public @ResponseBody JSONObject getAllFund(Model model, HttpServletRequest request) {
		Fund fund = fr.findOne((long) 1);
		JSONObject res = new JSONObject();
		JSONArray res2 = new JSONArray();
		res2.put(2);
		res2.put(3);
		try {
			res.put("id", fund.getFundId());
			res.put("name", fund.getName());
			res.put("token", fund.getSymbol());
			res.put("token", fund.getSymbol());
			res.put("message", "Hello!!!!");
			res.put("an array", res2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

}
