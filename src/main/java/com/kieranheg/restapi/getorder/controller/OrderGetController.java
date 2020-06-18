package com.kieranheg.restapi.getorder.controller;

import com.kieranheg.restapi.auxiliary.validation.ValidOrderId;
import com.kieranheg.restapi.getorder.service.OrderGetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@Validated
@RequestMapping("${url.get-order}")
public class OrderGetController {
    
    private final OrderGetService service;
    
    @Value("${url.get-order}")
    private String url;
    
    public OrderGetController(OrderGetService service) {
        this.service = service;
    }
    
    @GetMapping(path = "{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getOrder(final @PathVariable("id") @ValidOrderId Integer id) {
        return service.findById(id)
                .map(order -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .location(new URI(url + order.getId()))
                                .body(order);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.status(NOT_FOUND).build());
    }
}
