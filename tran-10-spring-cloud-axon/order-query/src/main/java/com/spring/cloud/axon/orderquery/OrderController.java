package com.spring.cloud.axon.orderquery;

import com.spring.cloud.axon.orderquery.query.OrderEntity;
import com.spring.cloud.axon.orderquery.query.OrderEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: zhihu
 * Description:
 * Date: Create in 2019/3/29 9:30
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderEntityRepository orderEntityRepository;
    
    @GetMapping("")
    public List<OrderEntity> get() {
        return orderEntityRepository.findAll();
    }
    
    @GetMapping("/{orderId}")
    public OrderEntity get(@PathVariable String orderId) {
        return orderEntityRepository.getOne(orderId);
    }
}

