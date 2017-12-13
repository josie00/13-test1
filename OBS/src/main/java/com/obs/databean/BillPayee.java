package com.obs.databean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class BillPayee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long billPayeeId;
	private String status;
	private String billPayeeName;
	
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "bill_payee_type_id")
	private BillPayeeType billPayeeType;
	
	@OneToMany(mappedBy = "billPayee", cascade = CascadeType.ALL)
	private Set<Account> accounts;
	
	@OneToMany(mappedBy = "billPayee", cascade = CascadeType.ALL)
	private Set<Transaction> transactions;
	
	
	public BillPayee(String status, String billPayeeName, BillPayeeType billPayeeType, Set<Account> accounts,
			Set<Transaction> transactions) {
		this.status = status;
		this.billPayeeName = billPayeeName;
		this.billPayeeType = billPayeeType;
		this.accounts = accounts;
		this.transactions = transactions;
	}

	protected BillPayee() {}

	public long getBillPayeeId() {
		return billPayeeId;
	}


	public void setBillPayeeId(long billPayeeId) {
		this.billPayeeId = billPayeeId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getBillPayeeName() {
		return billPayeeName;
	}


	public void setBillPayeeName(String billPayeeName) {
		this.billPayeeName = billPayeeName;
	}


	public BillPayeeType getBillPayeeType() {
		return billPayeeType;
	}


	public void setBillPayeeType(BillPayeeType billPayeeType) {
		this.billPayeeType = billPayeeType;
	}


	public Set<Account> getAccounts() {
		return accounts;
	}


	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}


	public Set<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}


	@Override
	public String toString() {
		return billPayeeName;
	}
}
