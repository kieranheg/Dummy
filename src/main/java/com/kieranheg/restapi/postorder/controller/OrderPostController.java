package com.kieranheg.restapi.postorder.controller;

import com.kieranheg.restapi.postorder.model.Order;
import com.kieranheg.restapi.postorder.service.OrderPostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@Validated
@RequestMapping("${url.post-order}")
public class OrderPostController {
    
    private final OrderPostService service;
    
    @Value("${url.post-order}")
    private String url;
    
    public OrderPostController(OrderPostService service) {
        this.service = service;
    }
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = service.save(order);
        try {
            return ResponseEntity
                    .created(new URI(url + newOrder.getId()))
                    .body(newOrder);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
