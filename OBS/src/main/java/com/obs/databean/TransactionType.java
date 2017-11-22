package com.obs.databean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long transactionTypeId;
	private double transactionFee;
	private String transactionTypeName;
	
	protected TransactionType() {}

	public TransactionType(double transactionFee, String transactionTypeName) {
		this.transactionFee = transactionFee;
		this.transactionTypeName = transactionTypeName;
	}

	@Override
	public String toString() {
		return "TransactionType [transactionTypeId=" + transactionTypeId + ", transactionFee=" + transactionFee
				+ ", transactionTypeName=" + transactionTypeName + "]";
	}
}
