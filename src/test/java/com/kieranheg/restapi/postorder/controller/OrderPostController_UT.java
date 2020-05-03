package com.kieranheg.restapi.postorder.controller;

import com.kieranheg.restapi.auxiliary.exception.RestExceptionHandler;
import com.kieranheg.restapi.postorder.model.Order;
import com.kieranheg.restapi.postorder.service.OrderPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.kieranheg.utils.JacksonMapperHelper.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
public class OrderPostController_UT {
    private static final String ORDER_URL = "/v1.0/order/new";
    private static final String POST_ORDER_ID = "1234567890";
    private static final String ORDER_NAME = "Sample Order";
    
    @Mock
    private OrderPostService orderPostService;
    
    @InjectMocks
    private OrderPostController orderPostController;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    public void BeforeEach() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderPostController).setControllerAdvice(new RestExceptionHandler()).build();
    }
    
    @Test
    @DisplayName("POST for valid order - Ok")
    void givenValidOrderReturnsCreatedOrder() throws Exception {
        Order postOrder = Order.builder().name(ORDER_NAME).quantity(999).build();
        Order mockOrder = Order.builder().id(POST_ORDER_ID).name(ORDER_NAME).quantity(999).build();
    
        given(orderPostService.save(postOrder)).willReturn(mockOrder);
    
        mockMvc.perform(post(ORDER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postOrder)))
            
                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            
                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, ORDER_URL + "/" + POST_ORDER_ID))
            
                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(POST_ORDER_ID)))
                .andExpect(jsonPath("$.name", is(ORDER_NAME)))
                .andExpect(jsonPath("$.quantity", is(999)));
    }
    
    @Test
    @DisplayName("POST without order - Internal Server Error")
    void givenNoOrderReturnsInternalServerError() throws Exception {
        given(orderPostService.save(null)).willThrow(new RuntimeException("Something went wrong"));
        
        mockMvc.perform(post(ORDER_URL))
                .andExpect(status().isInternalServerError());
    }
}
