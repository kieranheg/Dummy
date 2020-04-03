package com.kieranheg.restapi.findproduct.service.impl;

import com.kieranheg.restapi.findproduct.model.Product;
import com.kieranheg.restapi.findproduct.repository.ProductRepository;
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
public class ProductService_UT {
    public static final String CAN_FIND_ID = "1234567890";
    public static final String NOT_FOUND_ID = "1737737737";
    
    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ProductServiceImpl productService;
    
    @Test
    @DisplayName("Test findById - success")
    void givenValidProductIdServiceReturnsProduct() {
        Product mockProduct = Product.builder().id(CAN_FIND_ID).name("Dummy Product").quantity(99).build();
        given(productRepository.findById(CAN_FIND_ID)).willReturn(Optional.of(mockProduct));
    
        Optional<Product> returnedProduct = productService.findById(CAN_FIND_ID);
    
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(returnedProduct.isPresent());
        softly.assertThat(returnedProduct.get()).isEqualTo(mockProduct);
        softly.assertAll();
    }
    
    @Test
    @DisplayName("Test findById calls the repository once - success")
    void givenValidProductIdServiceCallsRepositoryOnce() {
        Product mockProduct = Product.builder().id(CAN_FIND_ID).name("Dummy Product").quantity(99).build();
        given(productRepository.findById(CAN_FIND_ID)).willReturn(Optional.of(mockProduct));
        
        Optional<Product> returnedProduct = productService.findById(CAN_FIND_ID);
    
        verify(productRepository, times(1)).findById(anyString());
    }
    
    @Test
    @DisplayName("Test findById - Not Found")
    void givenNonExistentProductIdServiceReturnsNotFound() {
        given(productRepository.findById(NOT_FOUND_ID)).willReturn(Optional.empty());
        
        Optional<Product> returnedProduct = productService.findById(NOT_FOUND_ID);
        
        assertFalse(returnedProduct.isPresent(), "Product shouldn't be found");
    }
}
