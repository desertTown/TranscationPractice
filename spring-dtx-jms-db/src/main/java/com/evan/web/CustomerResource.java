package com.evan.web;

import com.evan.dao.CustomerRepository;
import com.evan.domain.Customer;
import com.evan.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("")
    public Customer createInAnnotation(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @PostMapping("/msg")
    public void create(@RequestParam String msg){
        jmsTemplate.convertAndSend("customer:msg:new",msg);
    }

    @GetMapping("")
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    @GetMapping("/msg")
    public String getMsg(){
        jmsTemplate.setReceiveTimeout(3000);
        Object reply = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(reply);
    }

}
