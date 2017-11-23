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
	private double balance;
	private String status;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "account_type_id")
	private AccountType accountType;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private Customer customer;
    
	protected Account() {}

	public Account(double balance, String status, AccountType accountType) {
		this.balance = balance;
		this.status = status;
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", status=" + status + "]";
	}


	
	
}
