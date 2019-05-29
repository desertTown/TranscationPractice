package com.evan.query;


import com.evan.event.AccountCreatedEvent;
import com.evan.event.AccountMoneyDepositedEvent;
import com.evan.event.AccountMoneyWithdrawnEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 */
@Service
public class AccountProjector {
    
    @Autowired
    private AccountEntityRepository accountEntityRepository;
    
    @EventHandler
    public void on(AccountCreatedEvent event) {
        AccountEntity account = new AccountEntity(event.getAccountId(), event.getName());
        accountEntityRepository.save(account);
    }
    
    @EventHandler
    public void on(AccountMoneyDepositedEvent event) {
        String accountId = event.getAccountId();
        AccountEntity accountView = accountEntityRepository.getOne(accountId);
        accountView.setDeposit(accountView.getDeposit() + event.getAmount());
        accountEntityRepository.save(accountView);
    }
    
    @EventHandler
    public void on(AccountMoneyWithdrawnEvent event) {
        String accountId = event.getAccountId();
        AccountEntity accountView = accountEntityRepository.getOne(accountId);
        accountView.setDeposit(accountView.getDeposit() - event.getAmount());
        accountEntityRepository.save(accountView);
    }
}

