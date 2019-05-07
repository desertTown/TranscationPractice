package com.evan.c3_03_spring_txn_jta.dao;

import com.evan.c3_03_spring_txn_jta.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);
}
