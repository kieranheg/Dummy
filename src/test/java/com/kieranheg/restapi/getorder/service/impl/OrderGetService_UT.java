package com.kieranheg.restapi.getorder.service.impl;

import com.kieranheg.restapi.getorder.model.Order;
import com.kieranheg.restapi.getorder.repository.OrderGetRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderGetService_UT {
    private static final Integer CAN_FIND_ID = 123456789;
    private static final Integer NOT_FOUND_ID = 173773773;
    
    @Mock
    private OrderGetRepository repo;
    
    @InjectMocks
    private OrderGetServiceImpl service;
    
    @Test
    @DisplayName("Test findById - success")
    void givenValidOrderIdServiceReturnsOrder() {
        Order mockOrder = Order.builder().id(CAN_FIND_ID).name("Dummy Order").quantity(99).build();
        given(repo.findById(CAN_FIND_ID)).willReturn(Optional.of(mockOrder));
    
        Optional<Order> returnedOrder = service.findById(CAN_FIND_ID);
    
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(returnedOrder.isPresent());
        softly.assertThat(returnedOrder.get()).isEqualTo(mockOrder);
        softly.assertAll();
    }
    
    @Test
    @DisplayName("Test findById calls the repository once - success")
    void givenValidOrderIdServiceCallsRepositoryOnce() {
        Order mockOrder = Order.builder().id(CAN_FIND_ID).name("Dummy Order").quantity(99).build();
        given(repo.findById(CAN_FIND_ID)).willReturn(Optional.of(mockOrder));
        
        service.findById(CAN_FIND_ID);
    
        verify(repo, times(1)).findById(anyInt());
    }
    
    @Test
    @DisplayName("Test findById - Not Found")
    void givenNonExistentOrderIdServiceReturnsNotFound() {
        given(repo.findById(NOT_FOUND_ID)).willReturn(Optional.empty());
        
        Optional<Order> orderReturnedByBadId = service.findById(NOT_FOUND_ID);
        
        assertFalse(orderReturnedByBadId.isPresent(), "Order Id shouldn't be found");
    }
}
