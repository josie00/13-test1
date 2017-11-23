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
	private String description;
	
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
	
	protected Transaction() {}

	public Transaction(TransactionType transactionType, Date timeStamp, double amount, String description, Account account) {
		this.transactionType = transactionType;
		this.timeStamp = timeStamp;
		this.amount = amount;
		this.description = description;
		this.account = account;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", timeStamp=" + timeStamp
				 + ", amount=" + amount + ", description=" + description + ", transactionType="
				+ transactionType + ", account=" + account + "]";
	}

	
	
}
