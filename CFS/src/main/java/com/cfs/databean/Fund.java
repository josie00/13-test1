package com.cfs.databean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"symbol"})})
public class Fund implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long fundId;
	private String name;
	
	@Column (name = "symbol", unique = true)
	private String symbol;
	private double currPrice;
	
	@OneToMany(mappedBy = "fund", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
	
	@OneToMany(mappedBy = "fund", cascade = CascadeType.ALL)
    private Set<Position> positions;
	
	@OneToMany(mappedBy = "fund", cascade = CascadeType.MERGE)
	private Set<FundPriceHistory> fphs;
	
	public Fund() {}
	
	


	public Fund(String name, String symbol, double currPrice, Set<Position> positions, Set<FundPriceHistory> fphs) {
		this.name = name;
		this.symbol = symbol;
		this.currPrice = currPrice;
		this.positions = positions;
		this.fphs = fphs;
	}



	public long getFundId() {
		return fundId;
	}

	public void setFundId(long fundId) {
		this.fundId = fundId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public double getCurrPrice() {
		return currPrice;
	}

	public void setCurrPrice(double currPrice) {
		this.currPrice = currPrice;
	}

	public Set<Position> getPositions() {
		return positions;
	}

	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}



	public Set<FundPriceHistory> getFphs() {
		return fphs;
	}



	public void setFphs(Set<FundPriceHistory> fphs) {
		this.fphs = fphs;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
