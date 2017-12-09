package com.obs.databean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RecurBillPay {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long recurBillPayId;
    
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    private long fromAccountId;
    private String fromAccountNum;
    private String fromAccountType;
    private String toAccountName;
    private double amount;
    private String frequency;
    
    public RecurBillPay(Customer customer, long fromAccountId, String fromAccountNum,
            String fromAccountType, String toAccountName, double amount, String frequency) {
        this.customer = customer;
        this.fromAccountId = fromAccountId;
        this.fromAccountNum = fromAccountNum;
        this.fromAccountType = fromAccountType;
        this.toAccountName = toAccountName;
        this.amount = amount;
        this.frequency = frequency;
    }
    
    protected RecurBillPay() {}
    
    public long getRecurBillPayId() {
        return recurBillPayId;
    }
    public void setRecurBillPayId(long recurBillPayId) {
        this.recurBillPayId = recurBillPayId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public long getFromAccountId() {
        return fromAccountId;
    }
    public void setFromAccountId(long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }
    public String getFromAccountNum() {
        return fromAccountNum;
    }

    public void setFromAccountNum(String fromAccountNum) {
        this.fromAccountNum = fromAccountNum;
    }
    
    public String getFromAccountType() {
        return fromAccountType;
    }

    public void setFromAccountType(String fromAccountType) {
        this.fromAccountType = fromAccountType;
    }
    
    public String getToAccountName() {
        return toAccountName;
    }

    public void setTpAocountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
