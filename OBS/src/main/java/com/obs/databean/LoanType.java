package com.obs.databean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class LoanType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long loanTypeId;
	
	private String loanTypeName;
	private double interestRate;
	private int durationByMonth;
	
	@OneToMany(mappedBy = "loanType", cascade = CascadeType.ALL)
	private Set<Loan> loans;
	
	
	protected LoanType() {}

	public String getLoanTypeName() {
		return loanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getDurationByMonth() {
		return durationByMonth;
	}

	public void setDurationByMonth(int durationByMonth) {
		this.durationByMonth = durationByMonth;
	}

	public Set<Loan> getLoans() {
		return loans;
	}

	public void setLoans(Set<Loan> loans) {
		this.loans = loans;
	}

	@Override
	public String toString() {
		return loanTypeName;
	}
	
	
}
