package com.cfs.formbean;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class BuyFundForm {
	@NotEmpty
	@NotNull
	private String amount;
	
	@NotEmpty
	@NotNull
	private long fundId;
	
	@NotEmpty
	@NotNull
	private String button;
	
	public BuyFundForm() {
		
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public long getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	
	public String getButton() {
	    return button;
	}
	
	public void setButton(String button) {
	    this.button = button;
	}
	
	
	
}
