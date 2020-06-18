package com.kieranheg.integration;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.kieranheg.restapi.RestApi;
import com.kieranheg.restapi.getorder.model.Order;
import io.restassured.RestAssured;
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

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest(classes = RestApi.class, webEnvironment = RANDOM_PORT)
public class Order_IT {
    private static final Integer CAN_FIND_ID_1 = 123456789;
    private static final Integer CAN_FIND_ID_2 = 987654321;
    private static final Integer NOT_FOUND_ID = 173773779;
    private static final Integer INVALID_ID = 19;
    
    private static final String SERVER_URL = "http://localhost";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String QUANTITY = "quantity";
    private static final String DUMMY_ORDER_1 = "Dummy Order 1";
    private static final String RESOURCE_PATH = "/{id}";
    
    @LocalServerPort
    private int port;

    @Value("${url.get-order}")
    private String getUrl;
    
    @Value("${url.post-order}")
    private String postUrl;
    
    @Value("${url.put-order}")
    private String putUrl;
    
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
                testRestTemplate.exchange(this.buildGetUrlWithResource(CAN_FIND_ID_1), GET, requestEntity, new ParameterizedTypeReference<Order>() {});
        
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
                testRestTemplate.exchange(buildGetUrlWithResource(CAN_FIND_ID_2), GET, requestEntity, new ParameterizedTypeReference<Order>() {});
        
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
                testRestTemplate.exchange(buildGetUrlWithResource(NOT_FOUND_ID), GET, requestEntity, new ParameterizedTypeReference<Order>() {});
        
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
                testRestTemplate.exchange(buildGetUrlWithResource(INVALID_ID), GET, requestEntity, new ParameterizedTypeReference<String>() {});
        
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
                testRestTemplate.exchange(buildGetUrlWithoutResource(), GET, requestEntity, new ParameterizedTypeReference<String>() {});
        
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }
    
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test Get with valid Order id - success")
    public void givenValidGetOrderRequestReturnSuccess() {
        RestAssured.port = port;
        RestAssured.baseURI = SERVER_URL;
        RestAssured.basePath = getUrl;
        
        with()
                .pathParam(ID, CAN_FIND_ID_1)
                .contentType(JSON)
                .when()
                .get(RESOURCE_PATH)
                .then()
                .statusCode(OK.value())
                .body(ID, equalTo(CAN_FIND_ID_1))
                .body(NAME, equalTo(DUMMY_ORDER_1))
                .body(QUANTITY, equalTo(987));
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test Get with valid Order id - fails")
    public void givenInvalidGetOrderRequestReturnNotFound() {
        RestAssured.port = port;
        RestAssured.baseURI = SERVER_URL;
        RestAssured.basePath = getUrl;
        
        with()
                .pathParam(ID, NOT_FOUND_ID)
                .contentType(JSON)
                .when()
                .get(RESOURCE_PATH)
                .then()
                .statusCode(NOT_FOUND.value());
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test Post with valid Order - success")
    public void givenValidPostOrderRequestReturnSuccess() {
        RestAssured.port = port;
        RestAssured.baseURI = SERVER_URL;
        RestAssured.basePath = postUrl;
        
        Order orderResponseFromPost = with()
                .body(Order
                        .builder()
                        .id(123321123)
                        .name(DUMMY_ORDER_1)
                        .quantity(765)
                        .build())
                .contentType(JSON)
                .when()
                .post()
                .then()
                .statusCode(CREATED.value())
                .extract()
                .as(Order.class);
        
        RestAssured.basePath = getUrl;
        // now get it and ensure data is good
        with()
                .pathParam(ID, orderResponseFromPost.getId())
                .contentType(JSON)
                .when()
                .get(RESOURCE_PATH)
                .then()
                .statusCode(OK.value())
                .body(ID, equalTo(orderResponseFromPost.getId()))
                .body(NAME, equalTo(DUMMY_ORDER_1))
                .body(QUANTITY, equalTo(765));
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test Post with valid Order and then invalid Get - success")
    public void givenValidPostOrderWhenIncorrectParamInGetReturnNotFound() {
        RestAssured.port = port;
        RestAssured.baseURI = SERVER_URL;
        RestAssured.basePath = postUrl;
        
        with()
                .body(Order
                        .builder()
                        .id(123321123)
                        .name(DUMMY_ORDER_1)
                        .quantity(765)
                        .build())
                .contentType(JSON)
                .when()
                .post()
                .then()
                .statusCode(CREATED.value())
                .extract()
                .as(Order.class);
        
        RestAssured.basePath = getUrl;
        // now get it and ensure data is good
        with()
                .pathParam(ID, NOT_FOUND_ID)
                .contentType(JSON)
                .when()
                .get(RESOURCE_PATH)
                .then()
                .statusCode(NOT_FOUND.value());
    }
    
    @Test
    @DataSet("orders.yml")
    @DisplayName("Test Put with valid Order - success")
    public void givenValidPutOrderRequestReturnSuccess() {
        RestAssured.port = port;
        RestAssured.baseURI = SERVER_URL;
        RestAssured.basePath = putUrl;
        
        Order orderResponseFromPost = with()
                .pathParam(ID, CAN_FIND_ID_1)
                .body(Order
                        .builder()
                        .id(CAN_FIND_ID_1)
                        .name(DUMMY_ORDER_1)
                        .quantity(849)
                        .build())
                .contentType(JSON)
                .when()
                .put(RESOURCE_PATH)
                .then()
                .statusCode(OK.value())
                .extract()
                .as(Order.class);
        
        // now get it and ensure data is good
        with()
                .pathParam(ID, CAN_FIND_ID_1)
                .contentType(JSON)
                .when()
                .get(RESOURCE_PATH)
                .then()
                .statusCode(OK.value())
                .body(ID, equalTo(orderResponseFromPost.getId()))
                .body(NAME, equalTo(DUMMY_ORDER_1))
                .body(QUANTITY, equalTo(849));
    }
    
    private String buildGetUrlWithoutResource() {
        return SERVER_URL + ":" + port + getUrl;
    }
    
    private String buildGetUrlWithResource(final Integer resource) {
        return SERVER_URL + ":" + port + getUrl + resource;
    }
    
    private void setUpRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        requestEntity = new HttpEntity<>("{}", headers);
    }
}
