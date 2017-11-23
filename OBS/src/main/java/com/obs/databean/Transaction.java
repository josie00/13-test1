package com.obs.databean;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Transaction {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long transactionId;

	private Date timeStamp;
	private double amount;
	
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "transaction_type_id")
	private TransactionType transactionType;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "loan_id")
	private Loan loan;

	private String confirmationNumber;
	private String description;
	
	protected Transaction() {}

	public Transaction(Date timeStamp, double amount, TransactionType transactionType, Account account,
			Employee employee, Loan loan, String confirmationNumber, String description) {
		this.timeStamp = timeStamp;
		this.amount = amount;
		this.transactionType = transactionType;
		this.account = account;
		this.employee = employee;
		this.loan = loan;
		this.confirmationNumber=confirmationNumber;
		this.description = description;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", timeStamp=" + timeStamp + ", amount=" + amount
				+ ", transactionType=" + transactionType + ", account=" + account + ", employee=" + employee + ", loan="
				+ loan + ", confirmationNumber=" + confirmationNumber + ", description=" + description + "]";
	}

	
}
