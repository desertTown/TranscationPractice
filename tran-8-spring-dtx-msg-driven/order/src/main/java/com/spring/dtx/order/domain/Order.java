package com.spring.dtx.order.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Data
@Entity(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String uuid;

    private Long customerId;

    private String title;

    private Long ticketNum;

    private int amount;

    private String status;

    private String reason;

    private ZonedDateTime createdDate;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", customerId=" + customerId +
                ", title='" + title + '\'' +
                ", ticketNum=" + ticketNum +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
