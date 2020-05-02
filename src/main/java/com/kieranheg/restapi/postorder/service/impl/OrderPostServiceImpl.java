package com.kieranheg.restapi.postorder.service.impl;

import com.kieranheg.restapi.postorder.repository.OrderPostRepository;
import com.kieranheg.restapi.postorder.model.Order;
import com.kieranheg.restapi.postorder.service.OrderPostService;
import org.springframework.stereotype.Service;

@Service
class OrderPostServiceImpl implements OrderPostService {
    private final OrderPostRepository orderPostRepository;
    
    public OrderPostServiceImpl(final OrderPostRepository orderPostRepository) {
        this.orderPostRepository = orderPostRepository;
    }
    
    @Override
    public Order save(final Order order) {
        return orderPostRepository.save(order);
    }
}
