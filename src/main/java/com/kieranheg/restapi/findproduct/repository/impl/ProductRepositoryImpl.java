package com.kieranheg.restapi.findproduct.repository.impl;

import com.kieranheg.restapi.findproduct.model.Product;
import com.kieranheg.restapi.findproduct.repository.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
class ProductRepositoryImpl implements ProductRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    
    public ProductRepositoryImpl(final JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        
        // Build a SimpleJdbcInsert object from the specified data source
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
    }
    
    @Override
    public Optional<Product> findById(final String id) {
        try {
            Product product = jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        return Product.builder()
                                .id(rs.getString("id"))
                                .name(rs.getString("name"))
                                .quantity(rs.getInt("quantity"))
                                .build();
                    });
            return Optional.of(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
