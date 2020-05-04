package com.kieranheg.restapi.getorder.repository;

import com.kieranheg.restapi.getorder.model.Order;

import java.util.Optional;

public interface OrderGetRepository {
    Optional<Order> findById(final Integer id);
}
