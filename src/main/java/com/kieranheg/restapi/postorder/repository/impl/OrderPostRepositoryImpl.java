package com.kieranheg.restapi.postorder.repository.impl;

import com.kieranheg.restapi.postorder.model.Order;
import com.kieranheg.restapi.postorder.repository.OrderPostRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
class OrderPostRepositoryImpl implements OrderPostRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    
    public OrderPostRepositoryImpl(final JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        
        // Build a SimpleJdbcInsert object from the specified data source
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }
    
    @Override
    public Order save(Order order) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", order.getName());
        parameters.put("quantity", order.getQuantity());
        
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        
        order.setId(newId.intValue());
        
        return order;
    }
}
