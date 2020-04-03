package com.kieranheg.restapi.findproduct.repository.impl;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.kieranheg.restapi.findproduct.model.Product;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest
public class ProductRepository_UT {
    public static final String CAN_FIND_ID_1 = "1234567890";
    public static final String CAN_FIND_ID_2 = "9876543210";
    public static final String NOT_FOUND_ID = "1737737737";
    
    @Autowired
    private ProductRepositoryImpl productRepository;
    
    @Test
    @DataSet("products.yml")
    @DisplayName("Test findById with valid ID - success")
    void givenValidProductIdRepositoryReturnsProduct() {
        Optional<Product> productResultSet = productRepository.findById(CAN_FIND_ID_1);
        
        Product mockProduct = Product.builder().id(CAN_FIND_ID_1).name("Dummy Product 1").quantity(987).build();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(productResultSet.isPresent());
        softly.assertThat(productResultSet.get()).isEqualTo(mockProduct);
        softly.assertAll();
    }
    
    @Test
    @DataSet("products.yml")
    @DisplayName("Test findById with different valid ID - success")
    void givenSecondValidProductIdRepositoryReturnsSecondProduct() {
        Optional<Product> productResultSet = productRepository.findById(CAN_FIND_ID_2);
        
        Product mockProduct = Product.builder().id(CAN_FIND_ID_2).name("Dummy Product 2").quantity(321).build();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(productResultSet.isPresent());
        softly.assertThat(productResultSet.get()).isEqualTo(mockProduct);
        softly.assertAll();
    }
    
    @Test
    @DataSet("products.yml")
    @DisplayName("Test findById with Non Existent ID - fails")
    void givenNonExistentProductIdRepositoryReturnsNotFound() {
        Optional<Product> productResultSet = productRepository.findById(NOT_FOUND_ID);
        
        // Validate that we found it
        assertFalse(productResultSet.isPresent(), "should be not be found for Product ID " + NOT_FOUND_ID);
    }
}
