package com.obs.databean;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
    
    @Column(unique=true)
    private String accountNumber;
    private String peRoutingNumber;
    private String wireRoutingNumber;
    private Date openDate;
    
    

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

	protected Account() {}

	public Account(double balance, String status, AccountType accountType, Customer customer,
			Set<Transaction> transactions) {
		this.balance = balance;
		this.status = status;
		this.accountType = accountType;
		this.customer = customer;
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", status=" + status + ", accountType="
				+ accountType + ", customer=" + customer + ", transactions=" + transactions + "]";
	}
	
}
