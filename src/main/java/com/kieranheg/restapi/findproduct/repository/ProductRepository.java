package com.kieranheg.restapi.findproduct.repository;

import com.kieranheg.restapi.findproduct.model.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Integer id);
}
