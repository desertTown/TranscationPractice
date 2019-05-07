package com.evan.c3_03_spring_txn_jta.service;

import com.evan.c3_03_spring_txn_jta.dao.CustomerRepository;
import com.evan.c3_03_spring_txn_jta.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class CustomerServiceTxInAnnotation {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public Customer create(Customer customer) {
        log.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        return customerRepository.save(customer);
    }

    @Transactional
    @JmsListener(destination = "customer:msg:new")
    public void createByListener(String name) {
        log.info("CustomerService In Annotation by Listener create customer:{}", name);
        Customer customer = new Customer();
        customer.setUsername("Annotation " + name);
        customer.setRole("USER");
        customer.setPassword("111111");

        jmsTemplate.convertAndSend("customer:msg:reply", customer.getUsername() + " created.");
        customerRepository.save(customer);
    }
}
