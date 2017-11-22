package com.obs.databean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccountType {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long accountTypeId;
	private String name;
	private double rate;
	
	protected AccountType() {}
	
	public AccountType(String name, double rate) {
		this.name = name;
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "AccountType [accountTypeId=" + accountTypeId + ", name=" + name + ", rate=" + rate + "]";
	}
	
}
