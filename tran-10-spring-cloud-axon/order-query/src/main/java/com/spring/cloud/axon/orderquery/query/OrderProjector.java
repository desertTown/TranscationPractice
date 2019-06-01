package com.spring.cloud.axon.orderquery.query;

import com.spring.cloud.axon.order.event.OrderFailedEvent;
import com.spring.cloud.axon.order.event.OrderFinishedEvent;
import com.spring.cloud.axon.order.event.saga.OrderCreatedEvent;
import com.spring.cloud.axon.user.event.saga.OrderPaidEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProjector {

    @Autowired
    private OrderEntityRepository repository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity order = new OrderEntity();
        order.setId(event.getOrderId());
        order.setCustomerId(event.getCustomerId());
        order.setTicketId(event.getTicketId());
        order.setAmount(event.getAmount());
        order.setCreatedDate(event.getCreatedDate());
        order.setTitle(event.getTitle());
        order.setStatus("NEW");
        repository.save(order);
    }

    @EventSourcingHandler
    public void onPaid(OrderPaidEvent event) {
        OrderEntity order = repository.getOne(event.getOrderId());
        order.setStatus("PAID");
        repository.save(order);
    }

    @EventSourcingHandler
    public void onFinished(OrderFinishedEvent event) {
        OrderEntity order = repository.getOne(event.getOrderId());
        order.setStatus("FINISH");
        repository.save(order);
    }

    @EventSourcingHandler
    public void onFailed(OrderFailedEvent event) {
        OrderEntity order = repository.getOne(event.getOrderId());
        order.setStatus("FAILED");
        order.setReason(event.getReason());
        repository.save(order);
    }
}
