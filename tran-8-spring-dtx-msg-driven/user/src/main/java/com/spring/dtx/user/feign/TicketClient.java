package com.spring.dtx.user.feign;

import com.spring.dtx.service.dto.TicketDTO;
import com.spring.dtx.service.service.TicketCompositeService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by mavlarn on 2018/4/5.
 */
@FeignClient(value = "ticket", path = "/api/ticket")
public interface TicketClient extends TicketCompositeService {

    @GetMapping("/{customerId}")
    List<TicketDTO> getMyTickets(@PathVariable(name = "customerId") Long customerId);

}
