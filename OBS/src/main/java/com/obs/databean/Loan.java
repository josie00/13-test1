package com.obs.databean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long loanId;
	private double loanInterestRate;
	private Date startDate;
	private double duePayment;
	private double loanDurationbyMonth;
	
	protected Loan() {}
	
	public Loan(double loanInterestRate, Date startDate, double duePayment, double loanDurationbyMonth) {
		this.loanInterestRate = loanInterestRate;
		this.startDate = startDate;
		this.duePayment = duePayment;
		this.loanDurationbyMonth = loanDurationbyMonth;
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", loanInterestRate=" + loanInterestRate + ", startDate=" + startDate
				+ ", duePayment=" + duePayment + ", loanDurationbyMonth=" + loanDurationbyMonth + "]";
	}
}
