package com.kieranheg.restapi.getorder.service.impl;

import com.kieranheg.restapi.getorder.model.Order;
import com.kieranheg.restapi.getorder.repository.OrderRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderService_UT {
    private static final String CAN_FIND_ID = "1234567890";
    private static final String NOT_FOUND_ID = "1737737737";
    
    @Mock
    private OrderRepository orderRepository;
    
    @InjectMocks
    private OrderServiceImpl orderService;
    
    @Test
    @DisplayName("Test findById - success")
    void givenValidOrderIdServiceReturnsService() {
        Order mockOrder = Order.builder().id(CAN_FIND_ID).name("Dummy Order").quantity(99).build();
        given(orderRepository.findById(CAN_FIND_ID)).willReturn(Optional.of(mockOrder));
    
        Optional<Order> returnedService = orderService.findById(CAN_FIND_ID);
    
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(returnedService.isPresent());
        softly.assertThat(returnedService.get()).isEqualTo(mockOrder);
        softly.assertAll();
    }
    
    @Test
    @DisplayName("Test findById calls the repository once - success")
    void givenValidOrderIdServiceCallsRepositoryOnce() {
        Order mockOrder = Order.builder().id(CAN_FIND_ID).name("Dummy Order").quantity(99).build();
        given(orderRepository.findById(CAN_FIND_ID)).willReturn(Optional.of(mockOrder));
        
        orderService.findById(CAN_FIND_ID);
    
        verify(orderRepository, times(1)).findById(anyString());
    }
    
    @Test
    @DisplayName("Test findById - Not Found")
    void givenNonExistentOrderIdServiceReturnsNotFound() {
        given(orderRepository.findById(NOT_FOUND_ID)).willReturn(Optional.empty());
        
        Optional<Order> orderReturnedByBadId = orderService.findById(NOT_FOUND_ID);
        
        assertFalse(orderReturnedByBadId.isPresent(), "Order Id shouldn't be found");
    }
}
