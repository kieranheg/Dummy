package com.kieranheg.restapi.findproduct.controller;

import com.kieranheg.restapi.findproduct.service.ProductService;
import com.kieranheg.restapi.other.validator.ValidProductId;
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
public class ProductController {
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(final @PathVariable @ValidProductId String id) {
        return productService.findById(id)
                .map(product -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .location(new URI("/product/" + product.getId()))
                                .body(product);
                    } catch (URISyntaxException e ) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
