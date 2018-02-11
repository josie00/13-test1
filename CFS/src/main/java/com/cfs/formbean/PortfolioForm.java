package com.cfs.formbean;

import java.util.ArrayList;
import java.util.List;

public class PortfolioForm {
	private String message;
	private String cash;
	private List<FundForm> funds = new ArrayList<>();
	
	public PortfolioForm() {}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public List<FundForm> getFunds() {
		return funds;
	}

	public void addFund(FundForm fund) {
		funds.add(fund);
	}

}
