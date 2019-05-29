package com.evan;


import com.evan.command.AccountCreateCommand;
import com.evan.command.AccountDepositCommand;
import com.evan.command.AccountWithdrawCommand;
import com.evan.query.AccountEntity;
import com.evan.query.AccountEntityRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Description:
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private AccountEntityRepository accountEntityRepository;
    
    // 创建账户
    @PostMapping("")
    public CompletableFuture<Object> createBankAccount(@RequestParam String name) {
        log.info("Request to create account for: {}", name);
        UUID accountId = UUID.randomUUID();
        AccountCreateCommand createAccountCommand = new AccountCreateCommand(accountId.toString(), name);
        return commandGateway.send(createAccountCommand);
    }
    
    // 存款
    @PutMapping("/{accountId}/deposit/{amount}")
    public CompletableFuture<Object> depositMoney(@PathVariable String accountId, @PathVariable Double amount) {
        log.info("Request to withdraw {} from account {} ", amount, accountId);
        return commandGateway.send(new AccountDepositCommand(accountId, amount));
    }
    
    // 取款
    @PutMapping("/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withdrawMoney(@PathVariable String accountId, @PathVariable Double amount) {
        log.info("Request to withdraw {} from account {} ", amount, accountId);
//        return commandGateway.send(new AccountDepositCommand(accountId, amount));
        return  commandGateway.send(new AccountWithdrawCommand(accountId, amount));
    }
    
    // 通过账户id获得账户
    @GetMapping("/{accountId}")
    public AccountEntity getAccountById(@PathVariable String accountId) {
        log.info("Request Account with id: {}", accountId);
        return accountEntityRepository.getOne(accountId);
    }
    
    // 获得所有账户
    @GetMapping("")
    public List<AccountEntity> getAllAccounts() {
        log.info("Request all Accounts");
        return accountEntityRepository.findAll();
    }
}
