package com.kieranheg.restapi.getorder.service;

import com.kieranheg.restapi.getorder.model.Order;

import java.util.Optional;

public interface OrderGetService {
    Optional<Order> findById(final String id);
}
