package com.spring.dtx.order.web;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.spring.dtx.order.dao.OrderRepository;
import com.spring.dtx.order.domain.Order;
import com.spring.dtx.service.dto.OrderDTO;
import com.spring.dtx.service.service.OrderCompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderResource implements OrderCompositeService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    private TimeBasedGenerator uuidGenerator = Generators.timeBasedGenerator();

    @Transactional
    @PostMapping("")
    public void create(@RequestBody OrderDTO dto) {
        dto.setUuid(uuidGenerator.generate().toString());
        jmsTemplate.convertAndSend("order:new", dto);
    }

    @GetMapping("/{customerId}")
    public List<OrderDTO> getMyOrder(@PathVariable Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        return orders.stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setStatus(order.getStatus());
            dto.setTicketNum(order.getTicketNum());
            dto.setAmount(order.getAmount());
            dto.setCustomerId(order.getCustomerId());
            dto.setTitle(order.getTitle());
            dto.setUuid(order.getUuid());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("")
    public List<Order> getAll() {
        return orderRepository.findAll();
    }


}
