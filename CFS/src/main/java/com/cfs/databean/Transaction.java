package com.cfs.databean;

import javax.persistence.*;

import com.cfs.databean.Customer;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Transaction implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long transactionId;

    private Date executeDate;
    private double shares;
    private double sharePrice;
    private String transactionType;
    private double transactionAmount;
    private String status;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fund_id")
    private Fund fund;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
	private Customer customer;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id")
	private Position position;
    
    protected Transaction() {}

	public Transaction(Date executeDate, double shares, double sharePrice, String transactionType,
			double transactionAmount, String status, Fund fund, Customer customer, Position position) {
		this.executeDate = executeDate;
		this.shares = shares;
		this.sharePrice = sharePrice;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.status = status;
		this.fund = fund;
		this.customer = customer;
		this.position = position;
	}



	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public double getShares() {
		return shares;
	}

	public void setShares(double shares) {
		this.shares = shares;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}
    
}
