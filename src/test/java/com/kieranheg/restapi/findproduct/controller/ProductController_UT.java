package com.kieranheg.restapi.findproduct.controller;

import com.kieranheg.restapi.auxiliary.exception.RestExceptionHandler;
import com.kieranheg.restapi.findproduct.model.Product;
import com.kieranheg.restapi.findproduct.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductController_UT {
    private static final String CAN_FIND_ID = "1234567890";
    private static final String NOT_FOUND_ID = "1737737737";
    private static final String BAD_PARAM_ID = "26";
    
    @Mock
    private ProductService productService;
    
    @InjectMocks
    private ProductController productController;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    public void BeforeEach() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).setControllerAdvice(new RestExceptionHandler()).build();
    }
    
    @Test
    @DisplayName("GET for valid product id - Ok")
    void givenValidProductIdReturnsProduct() throws Exception {
        Product mockProduct = Product.builder().id(CAN_FIND_ID).name("Sample Product").quantity(99).build();
        given(productService.findById(CAN_FIND_ID)).willReturn(Optional.of(mockProduct));
    
        mockMvc.perform(get("/product/{id}", CAN_FIND_ID))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/product/1234567890"))
                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(CAN_FIND_ID)))
                .andExpect(jsonPath("$.name", is("Sample Product")))
                .andExpect(jsonPath("$.quantity", is(99)));
    }
    
    @Test
    @DisplayName("GET for non existent product id - Not Found")
    void givenNonExistentProductIdReturnsProductNotFound() throws Exception {
        given(productService.findById(NOT_FOUND_ID)).willReturn(Optional.empty());
        
        mockMvc.perform(get("/product/{id}", NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("GET for invalid product id - Bad request")
    void givenInvalidProductIdReturnsProductNotFound() throws Exception {
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mock(ConstraintViolation.class));
        ConstraintViolationException constraintViolationException = mock(ConstraintViolationException.class);
        
        given(productService.findById(BAD_PARAM_ID)).willThrow(constraintViolationException);
        
        mockMvc.perform(get("/product/{id}", BAD_PARAM_ID))
                .andExpect(status().isBadRequest());
    }
}
