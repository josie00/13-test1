package com.obs.formbean;


public class TransferForm {
	private String fromAccountId;
	private String toAccountId;
//	private String fromAccountNum;
//	private String toAccountNum;
	private String amount;
	private String dateOfTransfer;

	public TransferForm(){}

	public String getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public String getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(String toAccountId) {
		this.toAccountId = toAccountId;
	}

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDateOfTransfer() {
		return dateOfTransfer;
	}
	public void setDateOfTransfer(String dateOfTransfer) {
		this.dateOfTransfer = dateOfTransfer;
	}
	
}
