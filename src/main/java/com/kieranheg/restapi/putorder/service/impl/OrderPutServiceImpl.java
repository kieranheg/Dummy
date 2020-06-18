package com.kieranheg.restapi.putorder.service.impl;

import com.kieranheg.restapi.putorder.model.Order;
import com.kieranheg.restapi.putorder.repository.OrderPutRepository;
import com.kieranheg.restapi.putorder.service.OrderPutService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderPutServiceImpl implements OrderPutService {
    
    private final OrderPutRepository repo;
    
    public OrderPutServiceImpl(final OrderPutRepository repo) {
        this.repo = repo;
    }
    
    @Override
    public Optional<Order> findById(final Integer id) {
        return repo.findById(id);
    }
    
    @Override
    public boolean update(final Order updateOrder) {
        return false;
    }
}
