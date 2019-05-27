package com.spring.dtx.service.service;

import com.spring.dtx.service.dto.OrderDTO;

import java.util.List;

public interface OrderCompositeService {

    List<OrderDTO> getMyOrder(Long id);
}
