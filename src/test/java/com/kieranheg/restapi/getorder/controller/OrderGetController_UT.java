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

import java.util.Optional;

import static com.kieranheg.utils.JacksonMapperHelper.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderGetController.class)
public class OrderGetController_UT {
    private static final String RESOURCE_PATH = "{id}";
    private static final Integer CAN_FIND_ID = 123456789;
    private static final Integer NOT_FOUND_ID = 173773754;
    private static final Integer BAD_PARAM_ID = 26;
    private static final Integer QUANTITY_ORDERED = 99;
    private static final String ORDER_NAME = "Sample Order";
    
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
        
        mockMvc.perform(get(baseUrl + RESOURCE_PATH, CAN_FIND_ID)
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
        
        mockMvc.perform(get(baseUrl + NOT_FOUND_ID)
                .contentType(APPLICATION_JSON))
                
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("GET for invalid order id - Bad request")
    void givenInvalidOrderIdReturnsOrderBadRequest() throws Exception {
        mockMvc.perform(get(baseUrl + RESOURCE_PATH, BAD_PARAM_ID)
                .contentType(APPLICATION_JSON))
                
                .andExpect(status().isBadRequest());
    }
}
