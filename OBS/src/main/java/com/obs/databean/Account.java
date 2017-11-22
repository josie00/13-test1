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
	
	protected Account() {}

	public Account(double balance, String status) {
		this.balance = balance;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", status=" + status + "]";
	}


	
	
}
