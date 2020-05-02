package com.kieranheg.restapi.postorder.controller;

import com.kieranheg.restapi.postorder.model.Order;
import com.kieranheg.restapi.postorder.service.OrderPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Validated
public class OrderPostController {
    private final OrderPostService orderPostService;
    
    public OrderPostController(OrderPostService orderPostService) {
        this.orderPostService = orderPostService;
    }
    
    @PostMapping("/order/new")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = orderPostService.save(order);
        try {
            return ResponseEntity
                    .created(new URI("/order/new/" + newOrder.getId()))
                    .body(newOrder);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
