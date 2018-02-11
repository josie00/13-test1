package com.cfs.formbean;

import java.util.ArrayList;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.cfs.databean.Fund;


public class TransitionDayForm {

	private String fundId;
	
	@NotEmpty
	@NotNull
	@DecimalMin (value = "0.01", message = "Price should be greater than 0.")
    private String currPrice;
	
	@NotEmpty
	@NotNull
	private String transitionDate;
	
//	private ArrayList<Fund> funds;
		
    public TransitionDayForm() {
		
	}

//	public ArrayList<Fund> getFunds() {
//		return funds;
//	}
//
//	public void setFunds(ArrayList<Fund> funds) {
//		this.funds = funds;
//	}

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public String getCurrPrice() {
		return currPrice;
	}

	public void setCurrPrice(String currPrice) {
		this.currPrice = currPrice;
	}

	public String getTransitionDate() {
		return transitionDate;
	}

	public void setTransitionDate(String transitionDate) {
		this.transitionDate = transitionDate;
	}

	
	
}
