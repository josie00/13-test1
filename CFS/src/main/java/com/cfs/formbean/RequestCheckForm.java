package com.cfs.formbean;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class RequestCheckForm {

	@NotEmpty
	@NotNull
	@DecimalMin (value = "0.01", message = "Amount should be greater than 0.")
	private String amount;
	
	private String button;
	
    public RequestCheckForm() {
		
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getButton() {
	    return button;
	}
	
	public void setButton(String button) {
	    this.button = button;
	}

	
	
}
