package com.cfs.databean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Position implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long positionId;
	
	private double shares;
	
	private double tempShares;
	
	@ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name = "fund_id")
	private Fund fund;
	
	protected Position() {}
	
	

	public Position(double shares, double tempShares, Customer customer, Fund fund) {
		this.shares = shares;
		this.tempShares = tempShares;
		this.customer = customer;
		this.fund = fund;
	}



	public long getPositionId() {
		return positionId;
	}

	public void setPositionId(long positionId) {
		this.positionId = positionId;
	}

	public double getShares() {
		return shares;
	}

	public void setShares(double shares) {
		this.shares = shares;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}



	public double getTempShares() {
		return tempShares;
	}



	public void setTempShares(double tempShares) {
		this.tempShares = tempShares;
	}

	

	
	
}
