package com.obs.databean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RecurringLoanPay implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long recurringId;
	
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	private long fromAccountId;
	private long toLoanId;
	
	private String fromAccountNum;
    private String toLoanNum;
	
	private double amount;
	private String frequency;
	
	private Date dateOfFirstPay;
	private String numOfPay;
	
	public RecurringLoanPay(Customer customer, long fromAccountId, long toLoanId, String fromAccountNum,
			String toLoanNum, double amount, String frequency, Date dateOfFirstPay, String numOfPay) {
		this.customer = customer;
		this.fromAccountId = fromAccountId;
		this.toLoanId = toLoanId;
		this.fromAccountNum = fromAccountNum;
		this.toLoanNum = toLoanNum;
		this.amount = amount;
		this.frequency = frequency;
		this.dateOfFirstPay = dateOfFirstPay;
		this.numOfPay = numOfPay;
	}

	protected RecurringLoanPay() {}

	public long getRecurringId() {
		return recurringId;
	}

	public void setRecurringId(long recurringId) {
		this.recurringId = recurringId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public long getToLoanId() {
		return toLoanId;
	}

	public void setToLoanId(long toLoanId) {
		this.toLoanId = toLoanId;
	}

	public String getFromAccountNum() {
		return fromAccountNum;
	}

	public void setFromAccountNum(String fromAccountNum) {
		this.fromAccountNum = fromAccountNum;
	}

	public String getToLoanNum() {
		return toLoanNum;
	}

	public void setToLoanNum(String toLoanNum) {
		this.toLoanNum = toLoanNum;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public Date getDateOfFirstPay() {
		return dateOfFirstPay;
	}

	public void setDateOfFirstPay(Date dateOfFirstPay) {
		this.dateOfFirstPay = dateOfFirstPay;
	}

	public String getNumOfPay() {
		return numOfPay;
	}

	public void setNumOfPay(String numOfPay) {
		this.numOfPay = numOfPay;
	}
}
