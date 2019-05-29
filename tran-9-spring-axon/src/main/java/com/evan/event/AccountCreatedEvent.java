package com.evan.event;

/**
 * Description: 创建账户的事件Event
 */
public class AccountCreatedEvent {
    private String accountId;
    private String name;
    
    public AccountCreatedEvent(String accountId, String nam) {
        this.accountId = accountId;
        this.name = name;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
