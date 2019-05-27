package com.spring.dtx.service.dto;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TicketDTO {

    private Long id;

    private Long ticketNum;

    private String name;

    private Long lockUser;

    private Long owner;

}
