package com.obs.databean;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class AccountType {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long accountTypeId;
	private String name;
	private double rate;
	
	@OneToMany(mappedBy = "accountType", cascade = CascadeType.ALL)
	private Set<Account> accounts;
	
	protected AccountType() {}

	public AccountType(String name, double rate, Set<Account> accounts) {
		this.name = name;
		this.rate = rate;
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return "AccountType [accountTypeId=" + accountTypeId + ", name=" + name + ", rate=" + rate + "]";
	}
	
}
