package com.evan.event;

/**
 * Description: 取款的事件Event
 */
public class AccountMoneyWithdrawnEvent {
    private String accountId;
    
    private Double amount;
    
    public AccountMoneyWithdrawnEvent(String accountId, Double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
