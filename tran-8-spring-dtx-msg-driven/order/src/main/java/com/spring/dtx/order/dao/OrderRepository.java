package com.spring.dtx.order.dao;

import com.spring.dtx.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerId(Long customerId);

    List<Order> findAllByStatusAndCreatedDateBefore(String status, ZonedDateTime checkTime);

    Order findOneByUuid(String uuid);
}
