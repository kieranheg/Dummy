package com.kieranheg.restapi.findproduct.controller;

import com.kieranheg.restapi.other.exception.RestExceptionHandler;
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

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductController_UT {
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
    @DisplayName("GET /product/123456789 - Ok")
    void givenValidProductIdReturnsProduct() throws Exception {
        Product mockProduct = Product.builder().id(123456789).name("Sample Product").quantity(99).build();
        given(productService.findById(123456789)).willReturn(Optional.of(mockProduct));
    
        mockMvc.perform(get("/product/{id}", 123456789))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/product/123456789"))
                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(123456789)))
                .andExpect(jsonPath("$.name", is("Sample Product")))
                .andExpect(jsonPath("$.quantity", is(99)));
    }
    
    @Test
    @DisplayName("GET /product/737 - Not Found")
    void givenInvalidProductIdReturnsProductNotFound() throws Exception {
        given(productService.findById(737)).willReturn(Optional.empty());
        
        mockMvc.perform(get("/product/{id}", 737))
                // Validate the response code and content type
                .andExpect(status().isNotFound());
    }
}
