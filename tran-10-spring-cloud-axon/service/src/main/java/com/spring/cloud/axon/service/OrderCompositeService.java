package com.spring.cloud.axon.service;

import com.spring.cloud.axon.dto.OrderDTO;

import java.util.List;

public interface OrderCompositeService {

    List<OrderDTO> getMyOrder(Long id);
}