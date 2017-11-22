package com.obs.databean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long accountId;
	private long accountTypeId;
	private long customerId;
	private double balance;
	private String status;
	
	protected Account() {}

	public Account(long accountId, long accountTypeId, long customerId, double balance, String status) {
		this.accountId = accountId;
		this.accountTypeId = accountTypeId;
		this.customerId = customerId;
		this.balance = balance;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountTypeId=" + accountTypeId + ", customerId=" + customerId
				+ ", balance=" + balance + ", status=" + status + "]";
	}
	
}
