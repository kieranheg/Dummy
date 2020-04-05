package com.kieranheg.restapi.auxiliary.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderIdValidator_UT {
    private OrderIdValidator orderIdValidator = new OrderIdValidator();

    @Test
    @DisplayName("10 Digit Order Id - true")
    public void givenTenDigitValidOrderIdReturnsTrue() {
        assertTrue(orderIdValidator.isValid("0123456789", null));
    }
    
    @Test
    @DisplayName("9 Digit Order Id - false")
    public void givenNineDigitInvalidOrderIdReturnsFalse() {
        assertFalse(orderIdValidator.isValid("012345678", null));
    }
    
    @Test
    @DisplayName("11 Digit Order Id - false")
    public void givenElevenDigitInvalidOrderIdReturnsFalse() {
        assertFalse(orderIdValidator.isValid("01234567890", null));
    }
    
    @Test
    @DisplayName("Greater than 10 Digit Order Id - false")
    public void givenGreaterThanTenDigitInvalidOrderIdReturnsFalse() {
        assertFalse(orderIdValidator.isValid("00000001234567890", null));
    }
    
    @Test
    @DisplayName("Non Digit Order Id - false")
    public void givenInvalidNonDigitOrderIdReturnsFalse() {
        assertFalse(orderIdValidator.isValid("abc", null));
    }
    
    @Test
    @DisplayName("Empty Order Id - false")
    public void givenInvalidEmptyOrderIdReturnsFalse() {
        assertFalse(orderIdValidator.isValid("", null));
    }
    
    @Test
    @DisplayName("Null Order Id - false")
    public void givenInvalidNullOrderIdReturnsFalse() {
        assertFalse(orderIdValidator.isValid(null, null));
    }
}
