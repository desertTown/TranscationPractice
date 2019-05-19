package com.evan.web;


import com.evan.domain.Order;
import com.evan.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/order")
    private void create(@RequestBody Order order){
        customerService.createOrder(order);
    }
    @GetMapping("/{id}")
    public Map userInfo(@PathVariable Long id){
        return customerService.userInfo(id);
    }
}
