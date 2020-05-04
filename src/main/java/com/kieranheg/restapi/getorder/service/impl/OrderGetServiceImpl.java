package com.kieranheg.restapi.getorder.service.impl;

import com.kieranheg.restapi.getorder.model.Order;
import com.kieranheg.restapi.getorder.repository.OrderGetRepository;
import com.kieranheg.restapi.getorder.service.OrderGetService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class OrderGetServiceImpl implements OrderGetService {
    private final OrderGetRepository repo;
    
    public OrderGetServiceImpl(final OrderGetRepository repo) {
        this.repo = repo;
    }
    
    @Override
    public Optional<Order> findById(final String id) {
        return repo.findById(id);
    }
}
