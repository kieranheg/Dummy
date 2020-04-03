package com.kieranheg.restapi.auxiliary.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductIdValidator_UT {
    private ProductIdValidator productIdValidator = new ProductIdValidator();

    @Test
    @DisplayName("10 Digit Product Id - true")
    public void givenTenDigitValidProductIdReturnsTrue() {
        assertTrue(productIdValidator.isValid("0123456789", null));
    }
    
    @Test
    @DisplayName("9 Digit Product Id - false")
    public void givenNineDigitInvalidProductIdReturnsFalse() {
        assertFalse(productIdValidator.isValid("012345678", null));
    }
    
    @Test
    @DisplayName("11 Digit Product Id - false")
    public void givenElevenDigitInvalidProductIdReturnsFalse() {
        assertFalse(productIdValidator.isValid("01234567890", null));
    }
    
    @Test
    @DisplayName("Greater than 10 Digit Product Id - false")
    public void givenGreaterThanTenDigitInvalidProductIdReturnsFalse() {
        assertFalse(productIdValidator.isValid("00000001234567890", null));
    }
    
    @Test
    @DisplayName("Non Digit Product Id - false")
    public void givenInvalidNonDigitProductIdReturnsFalse() {
        assertFalse(productIdValidator.isValid("abc", null));
    }
    
    @Test
    @DisplayName("Empty Product Id - false")
    public void givenInvalidEmptyProductIdReturnsFalse() {
        assertFalse(productIdValidator.isValid("", null));
    }
    
    @Test
    @DisplayName("Null Product Id - false")
    public void givenInvalidNullProductIdReturnsFalse() {
        assertFalse(productIdValidator.isValid(null, null));
    }
}
