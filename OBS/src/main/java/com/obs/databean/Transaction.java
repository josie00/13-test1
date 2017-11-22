package com.obs.databean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long transactionId;
	private long transactionTypeId;
	private long accountId;
	private String timeStamp;
	private long loanId;
	private double amount;
	private String description;
	
	protected Transaction() {}

	public Transaction(long transactionId, long transactionTypeId, long accountId, String timeStamp, long loanId,
			double amount, String description) {
		this.transactionId = transactionId;
		this.transactionTypeId = transactionTypeId;
		this.accountId = accountId;
		this.timeStamp = timeStamp;
		this.loanId = loanId;
		this.amount = amount;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionTypeId=" + transactionTypeId
				+ ", accountId=" + accountId + ", timeStamp=" + timeStamp + ", loanId=" + loanId + ", amount=" + amount
				+ ", description=" + description + "]";
	}
	
}
