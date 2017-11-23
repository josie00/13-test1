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
	
	private long accountId;
	private Date timeStamp;
	private long loanId;
	private double amount;
	private String description;
	
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "transaction_type_id")
	private TransactionType transactionType;
	
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
	
	protected Transaction() {}

	public Transaction(TransactionType transactionType, long accountId, Date timeStamp, long loanId,
			double amount, String description) {
		this.transactionType = transactionType;
		this.accountId = accountId;
		this.timeStamp = timeStamp;
		this.loanId = loanId;
		this.amount = amount;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionType=" + transactionType
				+ ", accountId=" + accountId + ", timeStamp=" + timeStamp + ", loanId=" + loanId + ", amount=" + amount
				+ ", description=" + description + "]";
	}
	
}
