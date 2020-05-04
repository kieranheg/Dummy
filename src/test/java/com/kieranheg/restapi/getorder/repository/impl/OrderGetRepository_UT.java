package com.kieranheg.restapi.getorder.repository.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.kieranheg.restapi.getorder.model.Order;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(DBUnitExtension.class)
public class OrderGetRepository_UT {
    private static final Integer CAN_FIND_ID_1 = 123456789;
    private static final Integer CAN_FIND_ID_2 = 987654321;
    private static final Integer NOT_FOUND_ID = 173773773;
    
    @Autowired
    private OrderGetRepositoryImpl repo;
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test findById with valid ID - success")
    void givenValidOrderIdRepositoryReturnsOrder() {
        Optional<Order> orderResultSet = repo.findById(CAN_FIND_ID_1);
        
        Order mockOrder = Order.builder().id(CAN_FIND_ID_1).name("Dummy Order 1").quantity(987).build();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(orderResultSet.isPresent());
        softly.assertThat(orderResultSet.get()).isEqualTo(mockOrder);
        softly.assertAll();
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test findById with different valid ID - success")
    void givenSecondValidOrderIdRepositoryReturnsSecondOrder() {
        Optional<Order> orderResultSet = repo.findById(CAN_FIND_ID_2);
        
        Order mockOrder = Order.builder().id(CAN_FIND_ID_2).name("Dummy Order 2").quantity(321).build();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(orderResultSet.isPresent());
        softly.assertThat(orderResultSet.get()).isEqualTo(mockOrder);
        softly.assertAll();
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test findById with Non Existent ID - fails")
    void givenNonExistentOrderIdRepositoryReturnsNotFound() {
        Optional<Order> notFoundOrderResultSet = repo.findById(NOT_FOUND_ID);
        
        assertFalse(notFoundOrderResultSet.isPresent(), "should not be found for OrderID " + NOT_FOUND_ID);
    }
}
