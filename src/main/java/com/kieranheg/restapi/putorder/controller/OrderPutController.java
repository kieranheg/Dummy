package com.kieranheg.restapi.putorder.controller;

import com.kieranheg.restapi.auxiliary.validation.ValidOrderId;
import com.kieranheg.restapi.putorder.model.Order;
import com.kieranheg.restapi.putorder.service.OrderPutService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@Validated
@RequestMapping("${url.put-order}")
public class OrderPutController {
    
    private final OrderPutService service;
    
    @Value("${url.put-order}")
    private String url;
    
    public OrderPutController(OrderPutService service) {
        this.service = service;
    }
    
    @PutMapping(path = "{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> putOrder(final @RequestBody Order order,
                                      final @PathVariable("id") @ValidOrderId Integer id) {
        
        Optional<Order> existingOrder = service.findById(id);
        
        return existingOrder.map(updateOrder -> {
            
            updateOrder.setName(order.getName());
            updateOrder.setQuantity(order.getQuantity());
            
            if (service.update(updateOrder)) {
                try {
                    return ResponseEntity.ok()
                            .location(new URI(url + order.getId()))
                            .body(updateOrder);
                } catch (URISyntaxException e) {
                    return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return ResponseEntity.status(NOT_FOUND).build();
            }
        }).orElse(ResponseEntity.status(NOT_FOUND).build());
    }
}
