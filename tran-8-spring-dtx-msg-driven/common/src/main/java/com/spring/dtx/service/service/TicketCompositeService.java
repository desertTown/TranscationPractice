package com.spring.dtx.service.service;

import com.spring.dtx.service.dto.TicketDTO;

import java.util.List;

public interface TicketCompositeService {
    
    List<TicketDTO> getMyTickets(Long customerId);
    
}
