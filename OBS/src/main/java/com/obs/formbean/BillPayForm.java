package com.obs.formbean;

public class BillPayForm {
    private String fromAccountId;
    private String toAccountName;
    private String toAccountId;
    private String amount;
    private String frequency;
    
    
public BillPayForm() {}
    
    public String getFromAccountId() {
        return fromAccountId;
    }
    
    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }
    
    public String getToAccountName() {
        return toAccountName;
    }
    
    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }
    
    public String getToAccountId() {
        return toAccountId;
    }
    
    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }
    
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
}
