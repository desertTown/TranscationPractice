package com.spring.dtx.ticket.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    private Long ticketNum;

    private String name;

    private Long lockUser;

    private Long owner;
}
