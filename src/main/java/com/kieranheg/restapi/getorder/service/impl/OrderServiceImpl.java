package com.kieranheg.restapi.getorder.service.impl;

import com.kieranheg.restapi.getorder.model.Order;
import com.kieranheg.restapi.getorder.repository.OrderRepository;
import com.kieranheg.restapi.getorder.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    
    public OrderServiceImpl(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Override
    public Optional<Order> findById(final String id) {
        return orderRepository.findById(id);
    }
}
