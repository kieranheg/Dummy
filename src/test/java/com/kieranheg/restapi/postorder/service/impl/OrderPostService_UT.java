package com.kieranheg.restapi.postorder.service.impl;

import com.kieranheg.restapi.postorder.model.Order;
import com.kieranheg.restapi.postorder.repository.OrderPostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderPostService_UT {
    private static final String SAVE_ID = "9876543210";
    private static final int QUANTITY = 99;
    
    @Mock
    private OrderPostRepository repo;
    
    @InjectMocks
    private OrderPostServiceImpl service;
    
    @Test
    @DisplayName("Test save product - success")
    void givenValidOrderServiceSavesAndReturnsOrder() {
        Order mockOrder = Order.builder().id(SAVE_ID).name("Dummy Order").quantity(QUANTITY).build();
        given(repo.save(any())).willReturn(mockOrder);
    
        Order savedOrder = service.save(mockOrder);
    
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder).isEqualTo(mockOrder);
    }
    
    @Test
    @DisplayName("Test save product calls the repository once - success")
    void givenValidOrderServiceCallsRepositoryOnce() {
        Order mockOrder = Order.builder().id(SAVE_ID).name("Dummy Order").quantity(QUANTITY).build();
        given(repo.save(any())).willReturn(mockOrder);
        
        service.save(mockOrder);
        
        verify(repo, times(1)).save(any());
    }
}
