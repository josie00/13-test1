package com.obs.databean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TransactionType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long transactionTypeId;
	private double transactionFee;
	private String transactionTypeName;
	
	@OneToMany(mappedBy = "transactionType", cascade = CascadeType.ALL)
	private Set<Transaction> transactions;
	
	protected TransactionType() {}

	public TransactionType(double transactionFee, String transactionTypeName, Set<Transaction> transactions) {
		this.transactionFee = transactionFee;
		this.transactionTypeName = transactionTypeName;
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return transactionTypeName;
	}
}
