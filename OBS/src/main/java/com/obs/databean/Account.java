package com.obs.databean;
import java.util.Set;

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
	private double balance;
	private String status;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "account_type_id")
	private AccountType accountType;
    

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
	

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private Customer customer;
    

	protected Account() {}

	public Account(double balance, String status, AccountType accountType, Set<Transaction> transactions) {
		this.balance = balance;
		this.status = status;
		this.accountType = accountType;
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", status=" + status + ", accountType="
				+ accountType + ", transactions=" + transactions + "]";
	}

	


	
	
}
