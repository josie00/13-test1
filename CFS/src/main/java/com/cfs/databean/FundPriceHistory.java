package com.cfs.databean;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class FundPriceHistory implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long fundPriceHistoryId;

    private Date priceDate;
    private double price;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fund_id")
	private Fund fund;
    
    protected FundPriceHistory() {}

	public FundPriceHistory(Date priceDate, double price, Fund fund) {
		this.priceDate = priceDate;
		this.price = price;
		this.fund = fund;
	}

	public long getFundPriceHistoryId() {
		return fundPriceHistoryId;
	}

	public void setFundPriceHistoryId(long fundPriceHistoryId) {
		this.fundPriceHistoryId = fundPriceHistoryId;
	}

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

    
}
