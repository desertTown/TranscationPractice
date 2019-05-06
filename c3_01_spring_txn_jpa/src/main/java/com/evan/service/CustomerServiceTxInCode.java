package com.evan.service;


import com.evan.dao.CustomerRepository;
import com.evan.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Service
@Slf4j
public class CustomerServiceTxInCode {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    public Customer create(Customer customer) {
        log.info("CustomerService In Code create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            customer.setUsername("Code:" + customer.getUsername());
            customerRepository.save(customer);
            transactionManager.commit(status);
            return customer;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
