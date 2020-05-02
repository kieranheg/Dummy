package com.kieranheg.restapi.postorder.repository;

import com.kieranheg.restapi.postorder.model.Order;

public interface OrderPostRepository {
    Order save(final Order order);
}
