package com.kieranheg.restapi.postorder.controller;

import com.kieranheg.restapi.postorder.model.Order;
import com.kieranheg.restapi.postorder.service.OrderPostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.kieranheg.utils.JacksonMapperHelper.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderPostController.class)
public class OrderPostController_UT {
    private static final Integer POST_ORDER_ID = 123456789;
    private static final String ORDER_NAME = "Sample Order";
    private static final Integer ORDER_QUANTITY = 999;
    
    @MockBean
    private OrderPostService service;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Value("${url.post-order}")
    private String baseUrl;
    
    @Test
    @DisplayName("POST for valid order - Ok")
    void givenValidOrderReturnsCreatedOrder() throws Exception {
        Order postOrder = Order.builder().name(ORDER_NAME).quantity(ORDER_QUANTITY).build();
        Order mockOrder = Order.builder().id(POST_ORDER_ID).name(ORDER_NAME).quantity(ORDER_QUANTITY).build();
    
        given(service.save(postOrder)).willReturn(mockOrder);
    
        mockMvc.perform(post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postOrder)))
            
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            
                .andExpect(header().string(HttpHeaders.LOCATION, baseUrl + POST_ORDER_ID))
            
                .andExpect(jsonPath("$.id", is(POST_ORDER_ID)))
                .andExpect(jsonPath("$.name", is(ORDER_NAME)))
                .andExpect(jsonPath("$.quantity", is(ORDER_QUANTITY)));
    }
    
    @Test
    @DisplayName("POST without order - Internal Server Error")
    void givenNoOrderReturnsInternalServerError() throws Exception {
        given(service.save(null)).willThrow(new RuntimeException("Something went wrong"));
        
        mockMvc.perform(post(baseUrl))
                .andExpect(status().isInternalServerError());
    }
}
