package com.obs.databean;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long loanId;
	private Date startDate;
	private Date nextDueDate;
	private String status;
	private double duePayment;
	private double totalAmount;
	private String loanNumber;
	
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "loan_type_id")
	private LoanType loanType;
	
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	
	
	
	@OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
	private Set<Transaction> transactions;
	
	
	
	public Loan(Date startDate, Date nextDueDate, String status, double duePayment, double totalAmount,
			String loanNumber, LoanType loanType, Customer customer, Set<Transaction> transactions) {
		super();
		this.startDate = startDate;
		this.nextDueDate = nextDueDate;
		this.status = status;
		this.duePayment = duePayment;
		this.totalAmount = totalAmount;
		this.loanNumber = loanNumber;
		this.loanType = loanType;
		this.customer = customer;
		this.transactions = transactions;
	}


	protected Loan() {}

	
	public long getLoanId() {
		return loanId;
	}


	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getNextDueDate() {
		return nextDueDate;
	}


	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public double getDuePayment() {
		return duePayment;
	}


	public void setDuePayment(double duePayment) {
		this.duePayment = duePayment;
	}


	public double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getLoanNumber() {
		return loanNumber;
	}


	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}


	public LoanType getLoanType() {
		return loanType;
	}


	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Set<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}


	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", startDate=" + startDate + ", nextDueDate=" + nextDueDate + ", status="
				+ status + ", duePayment=" + duePayment + ", totalAmount=" + totalAmount + ", loanNumber=" + loanNumber
				+ ", loanType=" + loanType + ", customer=" + customer + ", transactions=" + transactions + "]";
	}
	
	

	
}
