package com.evan.service;

import com.evan.dao.CustomerRepository;
import com.evan.domain.Customer;
import com.evan.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate orderJdbcTemplate;

    private static final String SQL_CREATE_ORDER = "INSERT INTO customer_order (customer_id, title,amount) VALUES (?,?,?)";

    @Transactional
    public void createOrder(Order order) {

        Optional<Customer> customer = customerRepository.findById(order.getCustomerId());
        if (customer.isPresent()) {
            customer.get().setDeposit(customer.get().getDeposit()-order.getAmount());
            customerRepository.save(customer.get());
        }

        if(order.getTitle().contains("error1")){
            throw  new RuntimeException("Error1");
        }
        orderJdbcTemplate.update(SQL_CREATE_ORDER,order.getCustomerId(),order.getTitle(),order.getAmount());
        if(order.getTitle().contains("error2")){
            throw  new RuntimeException("Error2");
        }
    }

    public Map userInfo(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        List orders = orderJdbcTemplate.queryForList("select * from customer_order where customer_id=" + id);
        Map result = new HashMap();
        result.put("customer",customer);
        result.put("order",orders);
        return result;
    }
}
