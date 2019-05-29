package com.evan.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Description: 创建账户的业务Command
 */
public class AccountCreateCommand {
    
    @TargetAggregateIdentifier
    private String accountId;
    
    private String name;
    
    public AccountCreateCommand(String accountId, String name) {
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
