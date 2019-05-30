package com.evan.customer.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Description: 创建customer的command
 */
public class CustomerCreateCommand {
    
    @TargetAggregateIdentifier
    private String customerId;
    
    private String name;
    private String password;
    
    public CustomerCreateCommand(String customerId, String name, String password) {
        this.customerId = customerId;
        this.name = name;
        this.password = password;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPassword() {
        return password;
    }
}
