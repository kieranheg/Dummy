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
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith({DBUnitExtension.class})
public class OrderRepository_UT {
    private static final String CAN_FIND_ID_1 = "1234567890";
    private static final String CAN_FIND_ID_2 = "9876543210";
    private static final String NOT_FOUND_ID = "1737737737";
    
    @Autowired
    private OrderRepositoryImpl orderRepository;
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test findById with valid ID - success")
    void givenValidOrderIdRepositoryReturnsOrder() {
        Optional<Order> orderResultSet = orderRepository.findById(CAN_FIND_ID_1);
        
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
        Optional<Order> orderResultSet = orderRepository.findById(CAN_FIND_ID_2);
        
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
        Optional<Order> notFoundorderResultSet = orderRepository.findById(NOT_FOUND_ID);
        
        assertFalse(notFoundorderResultSet.isPresent(), "should not be found for OrderID " + NOT_FOUND_ID);
    }
}
