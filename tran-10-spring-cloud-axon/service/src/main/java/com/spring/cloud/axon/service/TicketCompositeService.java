package com.spring.cloud.axon.service;

import com.spring.cloud.axon.dto.TicketDTO;

import java.util.List;

public interface TicketCompositeService {

    List<TicketDTO> getMyTickets(Long customerId);

}