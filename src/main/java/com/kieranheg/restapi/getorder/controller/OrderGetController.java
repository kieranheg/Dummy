package com.kieranheg.restapi.getorder.controller;

import com.kieranheg.restapi.auxiliary.validation.ValidOrderId;
import com.kieranheg.restapi.getorder.service.OrderGetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Validated
public class OrderGetController {
    private final OrderGetService orderGetService;
    
    public OrderGetController(OrderGetService orderGetService) {
        this.orderGetService = orderGetService;
    }
    
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrder(final @PathVariable("id") @ValidOrderId String id) {
        return orderGetService.findById(id)
                .map(order -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .location(new URI("/order/" + order.getId()))
                                .body(order);
                    } catch (URISyntaxException e ) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
