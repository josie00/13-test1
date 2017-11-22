package com.obs.databean;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Account {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long accountId;
    
	private AccountType accountType;
    
	private long customerId;
	private double balance;
	private String status;
	
	protected Account() {}

	public Account(AccountType accountType, long customerId, double balance, String status) {
		this.accountType = accountType;
		this.customerId = customerId;
		this.balance = balance;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountTypeId=" + accountType + ", customerId=" + customerId
				+ ", balance=" + balance + ", status=" + status + "]";
	}
	
}
