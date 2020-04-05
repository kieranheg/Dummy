package com.kieranheg.restapi.getorder.service;

import com.kieranheg.restapi.getorder.model.Order;

import java.util.Optional;

public interface OrderService {
    Optional<Order> findById(final String id);
}
