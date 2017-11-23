package com.obs.databean;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Transaction {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long transactionId;

	private Date timeStamp;
	private double amount;
	
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "transaction_type_id")
	private TransactionType transactionType;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "loan_id")
	private Loan loan;

	private String description;
	
	protected Transaction() {}

	public Transaction(Date timeStamp, double amount, TransactionType transactionType, Account account,
			Employee employee, Loan loan, String description) {
		this.timeStamp = timeStamp;
		this.amount = amount;
		this.transactionType = transactionType;
		this.account = account;
		this.employee = employee;
		this.loan = loan;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", timeStamp=" + timeStamp + ", amount=" + amount
				+ ", transactionType=" + transactionType + ", account=" + account + ", employee=" + employee + ", loan="
				+ loan + ", description=" + description + "]";
	}
	
}
