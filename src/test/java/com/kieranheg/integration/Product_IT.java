package com.kieranheg.integration;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.kieranheg.restapi.RestApi;
import com.kieranheg.restapi.findproduct.model.Product;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {RestApi.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("inttest")
public class Product_IT {
    private static final String SERVER_URL = "http://localhost:";
    private static final String RESOURCE_URL = "/product/";
    
    private static final String CAN_FIND_ID_1 = "1234567890";
    private static final String CAN_FIND_ID_2 = "9876543210";
    private static final String NOT_FOUND_ID = "1737737737";
    private static final String INVALID_ID = "19";
    
    @LocalServerPort
    private int port;
    private TestRestTemplate testRestTemplate;
    private HttpEntity<Object> requestEntity;
    
    @BeforeEach
    public void beforeEach() throws Exception {
        testRestTemplate = new TestRestTemplate();
        setUpRequestEntity();
    }
    
    @Test
    @DataSet("products.yml")
    @DisplayName("Test findById with valid ID - success")
    public void givenValidProductIdRepositoryReturnsProduct() {
        String baseUrl = SERVER_URL + port + RESOURCE_URL + CAN_FIND_ID_1;
    
        ResponseEntity<Product> response =
                testRestTemplate.exchange(baseUrl, GET, requestEntity, new ParameterizedTypeReference<Product>() {});
        
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(OK);
        softly.assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON);
        softly.assertThat(response.getBody()).isNotNull();
        softly.assertThat(response.getBody().getId()).isEqualTo(CAN_FIND_ID_1);
        softly.assertThat(response.getBody().getName()).isEqualTo("Dummy Product 1");
        softly.assertThat(response.getBody().getQuantity()).isEqualTo(987);
        softly.assertAll();
    }
    
    @Test
    @DataSet("products.yml")
    @DisplayName("Test findById with a second valid ID - success")
    public void givenSecondValidProductIdRepositoryReturnsProduct() {
        String baseUrl = SERVER_URL + port + RESOURCE_URL + CAN_FIND_ID_2;
        
        ResponseEntity<Product> response =
                testRestTemplate.exchange(baseUrl, GET, requestEntity, new ParameterizedTypeReference<Product>() {});
        
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(OK);
        softly.assertThat(response.getBody()).isNotNull();
        softly.assertThat(response.getBody().getId()).isEqualTo(CAN_FIND_ID_2);
        softly.assertThat(response.getBody().getName()).isEqualTo("Dummy Product 2");
        softly.assertThat(response.getBody().getQuantity()).isEqualTo(321);
        softly.assertAll();
    }
    
    @Test
    @DataSet("products.yml")
    @DisplayName("Test findById with Non Existent ID - fails")
    public void givenNonExistentProductIdRepositoryReturnsNotFound() {
        String baseUrl = SERVER_URL + port + RESOURCE_URL + NOT_FOUND_ID;
        
        ResponseEntity<Product> response =
                testRestTemplate.exchange(baseUrl, GET, requestEntity, new ParameterizedTypeReference<Product>() {});
        
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        softly.assertThat(response.getBody()).isNull();
        softly.assertAll();
    }
    
    @Test
    @DataSet("products.yml")
    @DisplayName("Test findById with invalid ID param - fails")
    public void givenInvalidProductIdRepositoryReturnsBadRequest() {
        String baseUrl = SERVER_URL + port + RESOURCE_URL + INVALID_ID;
        
        ResponseEntity<String> response =
                testRestTemplate.exchange(baseUrl, GET, requestEntity, new ParameterizedTypeReference<String>() {});
        
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        softly.assertThat(response.getBody()).isEqualTo("getProduct.id: Product id '19' is invalid");
        softly.assertAll();
    }
    
    
    @Test
    @DataSet("products.yml")
    @DisplayName("Test findById with missing ID param - fails")
    public void givenMissingProductIdRepositoryReturnsBadRequest() {
        String baseUrl = SERVER_URL + port + RESOURCE_URL;
        
        ResponseEntity<String> response =
                testRestTemplate.exchange(baseUrl, GET, requestEntity, new ParameterizedTypeReference<String>() {});
        
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }
    
    private void setUpRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        requestEntity = new HttpEntity<>("{}", headers);
    }
}
