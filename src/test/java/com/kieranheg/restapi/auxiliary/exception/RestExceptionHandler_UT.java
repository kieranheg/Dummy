package com.kieranheg.restapi.auxiliary.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RestExceptionHandler_UT {
    
    @Test
    @DisplayName("Catch any missing params Exceptions and return Bad Request 400")
    void giveMissingParamNoHandlerFoundExceptionReturnBadRequest()  {
        NoHandlerFoundException noHandlerFoundException = mock(NoHandlerFoundException.class);
        ResponseEntity actualResponse = new RestExceptionHandler().noHandlerFoundExceptionHandler(noHandlerFoundException);
        
        assertThat(actualResponse.getStatusCode()).isEqualTo(BAD_REQUEST);
    }
    
    @Test
    @DisplayName("Catch any validation Constraint Violation Exception and return Bad Request 400")
    void givenInvalidParamConstraintViolationExceptionReturnBadRequest()  {
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(mock(ConstraintViolation.class));
        ConstraintViolationException constraintViolationException = mock(ConstraintViolationException.class);
        ResponseEntity actualResponse = new RestExceptionHandler().constraintViolationExceptionHandler(constraintViolationException);
        
        assertThat(actualResponse.getStatusCode()).isEqualTo(BAD_REQUEST);
    }
    
    @Test
    @DisplayName("Catch any unhandled Exception and return Internal Server Error 500")
    void givenUnhandledServerExceptionReturnInternalServerError()  {
        ResponseEntity actualResponse = new RestExceptionHandler().defaultExceptionHandler(new Exception());
        
        assertThat(actualResponse.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
    }
}
