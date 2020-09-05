package com.kieranheg.restapi.putorder.repository;

import com.kieranheg.restapi.putorder.model.Order;

import java.util.Optional;

public interface OrderPutRepository {
    Optional<Order> findById(final Integer id);
    
    boolean update(final Order updatedOrder);
}
