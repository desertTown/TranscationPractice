package com.spring.dtx.service.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderDTO {

    private Long id;

    private String uuid;

    private Long customerId;

    private String title;

    private Long ticketNum;

    private int amount;

    private String status;

}
