package com.cfs.formbean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;


import org.hibernate.validator.constraints.NotEmpty;

public class DepositCheckForm {

	@NotEmpty
	@NotNull
	@DecimalMin (value = "0.01", message = "Amount should be greater than 0.")
	private String amount;
	
	private String button;
	
	private String customerId;
	
	
    public DepositCheckForm() {
		
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getButton() {
        return button;
    }
    
    public void setButton(String button) {
        this.button = button;
    }
	
}
