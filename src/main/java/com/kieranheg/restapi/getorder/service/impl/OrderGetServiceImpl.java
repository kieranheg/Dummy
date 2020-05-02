package com.kieranheg.restapi.getorder.service.impl;

import com.kieranheg.restapi.getorder.model.Order;
import com.kieranheg.restapi.getorder.repository.OrderGetRepository;
import com.kieranheg.restapi.getorder.service.OrderGetService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class OrderGetServiceImpl implements OrderGetService {
    private final OrderGetRepository orderGetRepository;
    
    public OrderGetServiceImpl(final OrderGetRepository orderGetRepository) {
        this.orderGetRepository = orderGetRepository;
    }
    
    @Override
    public Optional<Order> findById(final String id) {
        return orderGetRepository.findById(id);
    }
}
