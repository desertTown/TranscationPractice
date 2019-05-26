package com.evan.service;

import com.evan.dao.CustomerRepository;
import com.evan.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = "customer:msg:new")
    public void handle(String msg){
        log.info("get msg1:{}",msg);
        Customer customer = new Customer();
        customer.setUserName(msg);
        customer.setDeposit(100);
        customerRepository.save(customer);
        if(msg.contains("error1")){
            throw new RuntimeException("Error1");
        }

        jmsTemplate.convertAndSend("customer:msg:reply",msg );
        if(msg.contains("error2")){
            throw new RuntimeException("Error2");
        }
    }

    @Transactional
    public Customer create(Customer customer){
        log.info("CustomerService create customer:{}",customer.getUserName());
        customer = customerRepository.save(customer);
        if(customer.getUserName().contains("error1")){
            throw new RuntimeException("Error1");
        }
        jmsTemplate.convertAndSend("customer:msg:reply",customer.getUserName());
        if(customer.getUserName().contains("error2")){
            throw new RuntimeException("Error2");
        }
        return customer;
    }
}
