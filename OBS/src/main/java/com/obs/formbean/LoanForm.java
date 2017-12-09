package com.obs.formbean;

public class LoanForm {
	private String fromAccountId;
	private String toLoanId;
	private String amount;
	private String frequency;
	private String dateOfLoanPay;
	private String dateOfFirstPay;
	private String numOfPay;

	public LoanForm(){}

	public String getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public String getToLoanId() {
		return toLoanId;
	}

	public void setToLoanId(String toLoanId) {
		this.toLoanId = toLoanId;
	}

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getDateOfLoanPay() {
		return dateOfLoanPay;
	}

	public void setDateOfLoanPay(String date) {
		this.dateOfLoanPay = date;
	}
	
	public String getDateOfFirstPay() {
		return dateOfFirstPay;
	}

	public void setDateOfFirstPay(String date) {
		this.dateOfFirstPay = date;
	}
	
	public String getNumOfPay() {
		return numOfPay;
	}

	public void setNumOfPay(String numOfPay) {
		this.numOfPay = numOfPay;
	}

}
