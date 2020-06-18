package com.kieranheg.restapi.putorder.controller;

import com.kieranheg.restapi.putorder.model.Order;
import com.kieranheg.restapi.putorder.service.OrderPutService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static com.kieranheg.utils.JacksonMapperHelper.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderPutController.class)
public class OrderPutController_UT {
    private static final String RESOURCE_PATH = "{id}";
    private static final String ORDER_NAME = "Sample Order";
    private static final Integer PUT_ORDER_ID = 123456789;
    private static final Integer BAD_PARAM_ID = 72;
    private static final Integer ORDER_EXISTING_QUANTITY = 999;
    private static final Integer ORDER_NEW_QUANTITY = 321;
    
    @MockBean
    private OrderPutService service;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Value("${url.put-order}")
    private String baseUrl;
    
    @Test
    @DisplayName("PUT for valid order update - OK")
    void givenValidPutOrderReturnsCreatedOrder() throws Exception {
        Order updateOrder = Order.builder().id(PUT_ORDER_ID).name(ORDER_NAME).quantity(ORDER_NEW_QUANTITY).build();
        
        Order mockPreUpdateOrder = Order.builder().id(PUT_ORDER_ID).name(ORDER_NAME).quantity(ORDER_EXISTING_QUANTITY).build();
        given(service.findById(any())).willReturn(Optional.of(mockPreUpdateOrder));
        
        given(service.update(any())).willReturn(true);
        
        mockMvc.perform(put(baseUrl + RESOURCE_PATH, PUT_ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateOrder)))
                
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                
                .andExpect(header().string(HttpHeaders.LOCATION, baseUrl + PUT_ORDER_ID))
                
                .andExpect(jsonPath("$.id", is(PUT_ORDER_ID)))
                .andExpect(jsonPath("$.name", is(ORDER_NAME)))
                .andExpect(jsonPath("$.quantity", is(ORDER_NEW_QUANTITY)));
    }
    
    @Test
    @DisplayName("PUT for non existent order Id - Not Found")
    void givenNonExistentPutOrderIdReturnsNotFound() throws Exception {
        Order updateOrder = Order.builder().id(PUT_ORDER_ID).name(ORDER_NAME).quantity(ORDER_NEW_QUANTITY).build();
        
        given(service.findById(any())).willReturn(Optional.empty());
        
        mockMvc.perform(put(baseUrl + RESOURCE_PATH, PUT_ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateOrder)))
                
                .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("PUT for invalid order Id - Bad request")
    void givenInvalidPutOrderIdReturnsBadRequest() throws Exception {
        Order updateOrder = Order.builder().id(BAD_PARAM_ID).name(ORDER_NAME).quantity(ORDER_NEW_QUANTITY).build();
    
        mockMvc.perform(put(baseUrl + RESOURCE_PATH, BAD_PARAM_ID)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(updateOrder)))
        
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("PUT for valid order Id but not body in request - Internal Server Error")
    void givenValidPutOrderIdButNoBodyWithOrderReturnsInternalServerError() throws Exception {
        mockMvc.perform(put(baseUrl + RESOURCE_PATH, PUT_ORDER_ID)
                .contentType(APPLICATION_JSON))
                
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Required request body is missing")));
    }
}
