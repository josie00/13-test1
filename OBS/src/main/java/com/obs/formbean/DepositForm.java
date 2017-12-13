package com.obs.formbean;

public class DepositForm {

	String amount;
	String toAccountId;
	
	public DepositForm() {}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}
	
}
