package com.evan;

import com.evan.command.AccountCreateCommand;
import com.evan.command.AccountDepositCommand;
import com.evan.command.AccountWithdrawCommand;
import com.evan.event.AccountCreatedEvent;
import com.evan.event.AccountMoneyDepositedEvent;
import com.evan.event.AccountMoneyWithdrawnEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Description: 聚合对象
 */
@Aggregate
public class Account {
    
    // 聚合对象id
    @AggregateIdentifier
    private String accountId;
    
    private Double deposit;
    
    public Account() {
    }
    
    // 创建账户的业务Command处理，触发创建账户的事件Event
    @CommandHandler
    public Account(AccountCreateCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getName()));
    }
    
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.deposit = 0d;
    }
    
    // 存款的业务Command处理，触发存款的事件Event
    @CommandHandler
    public void handle(AccountDepositCommand command) {
        apply(new AccountMoneyDepositedEvent(command.getAccountId(), command.getAmount()));
    }
    
    @EventSourcingHandler
    public void on(AccountMoneyDepositedEvent event) {
        this.deposit += event.getAmount();
    }
    
    // 取款的业务Command处理，触发取款的事件Event
    @CommandHandler
    public void handle(AccountWithdrawCommand command) {
        if (deposit >= command.getAmount()) {
            apply(new AccountMoneyWithdrawnEvent(command.getAccountId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("余额不足");
        }
    }
    
    @EventSourcingHandler
    public void on(AccountMoneyWithdrawnEvent event) {
        this.deposit -= event.getAmount();
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public Double getDeposit() {
        return deposit;
    }
}


