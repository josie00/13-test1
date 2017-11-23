package com.obs.databean;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long loanId;
	private double loanInterestRate;
	private Date startDate;
	private double duePayment;
	private double loanDurationbyMonth;
	
	@OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
	private Set<Transaction> transactions;
	
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
