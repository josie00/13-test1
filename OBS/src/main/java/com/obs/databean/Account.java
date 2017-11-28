package com.obs.databean;
import java.io.Serializable;
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

public class Account implements Serializable {
	
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

	public Account(double balance, String status, AccountType accountType, String accountNumber, String peRoutingNumber,
			String wireRoutingNumber, Date openDate, Customer customer, Set<Transaction> transactions) {
		super();
		this.balance = balance;
		this.status = status;
		this.accountType = accountType;
		this.accountNumber = accountNumber;
		this.peRoutingNumber = peRoutingNumber;
		this.wireRoutingNumber = wireRoutingNumber;
		this.openDate = openDate;
		this.customer = customer;
		this.transactions = transactions;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPeRoutingNumber() {
		return peRoutingNumber;
	}

	public void setPeRoutingNumber(String peRoutingNumber) {
		this.peRoutingNumber = peRoutingNumber;
	}

	public String getWireRoutingNumber() {
		return wireRoutingNumber;
	}

	public void setWireRoutingNumber(String wireRoutingNumber) {
		this.wireRoutingNumber = wireRoutingNumber;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	


	
	
}
