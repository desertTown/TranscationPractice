package com.spring.dtx.user.feign;

import com.spring.dtx.service.dto.OrderDTO;
import com.spring.dtx.service.service.OrderCompositeService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "order", path = "/api/order")
public interface OrderClient extends OrderCompositeService {

    @GetMapping("/{customerId}")
    List<OrderDTO> getMyOrder(@PathVariable(name = "customerId") Long id);
}
