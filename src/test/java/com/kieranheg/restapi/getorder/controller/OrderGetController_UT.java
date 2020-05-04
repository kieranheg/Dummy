package com.kieranheg.restapi.getorder.controller;

import com.kieranheg.restapi.getorder.model.Order;
import com.kieranheg.restapi.getorder.service.OrderGetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.kieranheg.utils.JacksonMapperHelper.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderGetController.class)
public class OrderGetController_UT {
    private static final String CAN_FIND_ID = "1234567890";
    private static final String NOT_FOUND_ID = "1737737737";
    private static final String BAD_PARAM_ID = "26";
    private static final String ORDER_NAME = "Sample Order";
    private static final int QUANTITY_ORDERED = 99;
    
    @MockBean
    private OrderGetService service;
    
    @Autowired
    private MockMvc mockMvc;

    @Value("${url.get-order}")
    private String baseUrl;
    
    @Test
    @DisplayName("GET for valid order id - Ok")
    void givenValidOrderIdReturnsOrder() throws Exception {
        Order mockOrder = Order.builder().id(CAN_FIND_ID).name(ORDER_NAME).quantity(QUANTITY_ORDERED).build();
        
        given(service.findById(CAN_FIND_ID)).willReturn(Optional.of(mockOrder));
        
        mockMvc.perform(get(baseUrl + "{id}", CAN_FIND_ID)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(mockOrder)))
                
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                
                .andExpect(header().string(HttpHeaders.LOCATION, baseUrl + CAN_FIND_ID))
                
                .andExpect(jsonPath("$.id", is(CAN_FIND_ID)))
                .andExpect(jsonPath("$.name", is(ORDER_NAME)))
                .andExpect(jsonPath("$.quantity", is(QUANTITY_ORDERED)));
    }
    
    @Test
    @DisplayName("GET for non existent order id - Not Found")
    void givenNonExistentOrderIdReturnsOrderNotFound() throws Exception {
        given(service.findById(NOT_FOUND_ID)).willReturn(Optional.empty());
        
        mockMvc.perform(get(baseUrl + "{id}", NOT_FOUND_ID)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("GET for invalid order id - Bad request")
    void givenInvalidOrderIdReturnsOrderNotFound() throws Exception {
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mock(ConstraintViolation.class));
        ConstraintViolationException constraintViolationException = mock(ConstraintViolationException.class);
        
        given(service.findById(BAD_PARAM_ID)).willThrow(constraintViolationException);
        
        mockMvc.perform(get(baseUrl + "{id}", BAD_PARAM_ID)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
