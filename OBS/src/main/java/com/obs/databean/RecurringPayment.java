package com.obs.databean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RecurringPayment implements Serializable {
	
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
	private long toAccountId;
	
	private String fromAccountNum;
	private String toAccountNum;
	
	private String fromAccountType;
	private String toAccountType;
	
	private double amount;
	private String frequency;
	

	public RecurringPayment(Customer customer, long fromAccountId, long toAccountId, String fromAccountNum,
			String toAccountNum, String fromAccountType, String toAccountType, double amount, String frequency) {
		this.customer = customer;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.fromAccountNum = fromAccountNum;
		this.toAccountNum = toAccountNum;
		this.fromAccountType = fromAccountType;
		this.toAccountType = toAccountType;
		this.amount = amount;
		this.frequency = frequency;
	}

	protected RecurringPayment() {}

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
	public long getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(long toAccountId) {
		this.toAccountId = toAccountId;
	}


	public String getFromAccountNum() {
		return fromAccountNum;
	}

	public void setFromAccountNum(String fromAccountNum) {
		this.fromAccountNum = fromAccountNum;
	}

	public String getToAccountNum() {
		return toAccountNum;
	}

	public void setToAccountNum(String toAccountNum) {
		this.toAccountNum = toAccountNum;
	}

	public String getFromAccountType() {
		return fromAccountType;
	}

	public void setFromAccountType(String fromAccountType) {
		this.fromAccountType = fromAccountType;
	}

	public String getToAccountType() {
		return toAccountType;
	}

	public void setToAccountType(String toAccountType) {
		this.toAccountType = toAccountType;
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
	
}
