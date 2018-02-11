package com.cfs.formbean;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;

import com.cfs.databean.Fund;


public class TransitionDayWrapper {

//	private String fundId;
	
	@NotEmpty
    private String transitionDate;
	
	private ArrayList<Fund> funds;
		
    public TransitionDayWrapper() {
		
	}

	public ArrayList<Fund> getFunds() {
		return funds;
	}

	public void setFunds(ArrayList<Fund> funds) {
		this.funds = funds;
	}

	public String getTransitionDate() {
		return transitionDate;
	}

	public void setTransitionDate(String transitionDate) {
		this.transitionDate = transitionDate;
	}

//	public String getFundId() {
//		return fundId;
//	}
//
//	public void setFundId(String fundId) {
//		this.fundId = fundId;
//	}

//	public String getCurrPrice() {
//		return currPrice;
//	}
//
//	public void setCurrPrice(String currPrice) {
//		this.currPrice = currPrice;
//	}

	
	
}
