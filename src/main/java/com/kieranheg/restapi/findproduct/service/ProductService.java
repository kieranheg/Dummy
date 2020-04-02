package com.kieranheg.restapi.findproduct.service;

import com.kieranheg.restapi.findproduct.model.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Integer id);
}
