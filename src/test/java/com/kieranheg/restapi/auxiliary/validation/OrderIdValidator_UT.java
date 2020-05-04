package com.kieranheg.restapi.auxiliary.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderIdValidator_UT {
    private OrderIdValidator orderIdValidator = new OrderIdValidator();

    @Test
    @DisplayName("9 Digit Order Id - true")
    public void givenNineDigitValidOrderIdReturnsTrue() {
        assertTrue(orderIdValidator.isValid(123456789, null));
    }
    
    @Test
    @DisplayName("8 Digit Order Id - false")
    public void givenEightDigitInvalidOrderIdReturnsFalse() {
        assertFalse(orderIdValidator.isValid(12345678, null));
    }
    
    @Test
    @DisplayName("Null Order Id - false")
    public void givenInvalidNullOrderIdReturnsFalse() {
        assertFalse(orderIdValidator.isValid(null, null));
    }
}
