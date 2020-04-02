package com.kieranheg.restapi.findproduct.service.impl;

import com.kieranheg.restapi.findproduct.model.Product;
import com.kieranheg.restapi.findproduct.repository.ProductRepository;
import com.kieranheg.restapi.findproduct.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }
}
