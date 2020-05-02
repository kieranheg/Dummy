package com.kieranheg.restapi.postorder.service;

import com.kieranheg.restapi.postorder.model.Order;

public interface OrderPostService {
    Order save(final Order order);
}
