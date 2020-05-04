package com.kieranheg.restapi.postorder.service.impl;

import com.kieranheg.restapi.postorder.repository.OrderPostRepository;
import com.kieranheg.restapi.postorder.model.Order;
import com.kieranheg.restapi.postorder.service.OrderPostService;
import org.springframework.stereotype.Service;

@Service
class OrderPostServiceImpl implements OrderPostService {
    private final OrderPostRepository repo;
    
    public OrderPostServiceImpl(final OrderPostRepository repo) {
        this.repo = repo;
    }
    
    @Override
    public Order save(final Order order) {
        return repo.save(order);
    }
}
