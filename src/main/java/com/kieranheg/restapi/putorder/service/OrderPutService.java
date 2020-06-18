package com.kieranheg.restapi.putorder.service;

import com.kieranheg.restapi.putorder.model.Order;

import java.util.Optional;

public interface OrderPutService {
    Optional<Order> findById(final Integer id);
    
    boolean update(final Order updateOrder);
}
