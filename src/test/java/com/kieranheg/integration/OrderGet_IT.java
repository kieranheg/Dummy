package com.kieranheg.integration;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.kieranheg.restapi.RestApi;
import com.kieranheg.restapi.getorder.model.Order;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest(classes = RestApi.class, webEnvironment = RANDOM_PORT)
public class OrderGet_IT {
    private static final String CAN_FIND_ID_1 = "1234567890";
    private static final String CAN_FIND_ID_2 = "9876543210";
    private static final String NOT_FOUND_ID = "1737737737";
    private static final String INVALID_ID = "19";
    private static final String MISSING_ORDER_ID = "";
    
    private static final String SERVER_URL = "http://localhost:";
    
    @LocalServerPort
    private int port;

    @Value("${url.get-order}")
    private String resourceUrl;
    
    private TestRestTemplate testRestTemplate;
    private HttpEntity<Object> requestEntity;

    @BeforeEach
    public void beforeEach() {
        testRestTemplate = new TestRestTemplate();
        setUpRequestEntity();
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test findById with valid ID - success")
    public void givenValidOrderIdRepositoryReturnsOrder() {
        ResponseEntity<Order> response =
                testRestTemplate.exchange(buildUrl(CAN_FIND_ID_1), GET, requestEntity, new ParameterizedTypeReference<Order>() {});
        
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(OK);
        softly.assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        softly.assertThat(response.getBody()).isNotNull();
        softly.assertThat(response.getBody().getId()).isEqualTo(CAN_FIND_ID_1);
        softly.assertThat(response.getBody().getName()).isEqualTo("Dummy Order 1");
        softly.assertThat(response.getBody().getQuantity()).isEqualTo(987);
        softly.assertAll();
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test findById with a second valid ID - success")
    public void givenSecondValidOrderIdRepositoryReturnsOrder() {
        ResponseEntity<Order> response =
                testRestTemplate.exchange(buildUrl(CAN_FIND_ID_2), GET, requestEntity, new ParameterizedTypeReference<Order>() {});
        
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(OK);
        softly.assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        softly.assertThat(response.getBody()).isNotNull();
        softly.assertThat(response.getBody().getId()).isEqualTo(CAN_FIND_ID_2);
        softly.assertThat(response.getBody().getName()).isEqualTo("Dummy Order 2");
        softly.assertThat(response.getBody().getQuantity()).isEqualTo(321);
        softly.assertAll();
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test findById with Non Existent ID - fails")
    public void givenNonExistentOrderIdRepositoryReturnsNotFound() {
        ResponseEntity<Order> response =
                testRestTemplate.exchange(buildUrl(NOT_FOUND_ID), GET, requestEntity, new ParameterizedTypeReference<Order>() {});
        
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        softly.assertThat(response.getBody()).isNull();
        softly.assertAll();
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test findById with invalid ID param - fails")
    public void givenInvalidOrderIdRepositoryReturnsBadRequest() {
        ResponseEntity<String> response =
                testRestTemplate.exchange(buildUrl(INVALID_ID), GET, requestEntity, new ParameterizedTypeReference<String>() {});
        
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        softly.assertThat(response.getBody()).isEqualTo("getOrder.id: Order id '19' is invalid");
        softly.assertAll();
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test findById with missing ID param - fails")
    public void givenMissingOrderIdRepositoryReturnsBadRequest() {
        ResponseEntity<String> response =
                testRestTemplate.exchange(buildUrl(MISSING_ORDER_ID), GET, requestEntity, new ParameterizedTypeReference<String>() {});
        
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }
    
    private String buildUrl(final String resource) {
        return SERVER_URL + port + resourceUrl + resource;
    }

    private void setUpRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        requestEntity = new HttpEntity<>("{}", headers);
    }
}
