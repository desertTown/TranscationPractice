package com.evan.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_name")
    private String userName;
    @Column
    private Integer deposit;
}
