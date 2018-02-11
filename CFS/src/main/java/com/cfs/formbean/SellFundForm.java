package com.cfs.formbean;

import org.hibernate.validator.constraints.NotEmpty;

public class SellFundForm {
	@NotEmpty
	private String shares;
	
	@NotEmpty
	private long fundId; 

	public SellFundForm() {
		
	}
	
	public String getShares() {
		return shares;
	}

	public void setShares(String shares) {
		this.shares = shares;
	}

	public long getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	
}
