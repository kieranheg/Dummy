package com.kieranheg.restapi.postorder.repository.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.kieranheg.restapi.postorder.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(DBUnitExtension.class)
public class OrderPostRepository_UT {
    private static final String DUMMY_ORDER_NAME = "Dummy Order Name";
    private static final Integer QUANTITY = 9911;
    
    @Autowired
    private OrderPostRepositoryImpl repo;
    
    @Test
    @DataSet(value = "orders.yml")
    @DisplayName("Test save product - success")
    void givenValidOrderServiceSavesAndReturnsOrder() {
        Order order = Order.builder().name(DUMMY_ORDER_NAME).quantity(QUANTITY).build();

        Order savedOrder = repo.save(order);
    
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getName()).isEqualTo(DUMMY_ORDER_NAME);
        assertThat(savedOrder.getQuantity()).isEqualTo(QUANTITY);
    }
}
