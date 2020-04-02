package com.kieranheg.restapi.other.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class RestExceptionHandler_UT {
    
    @Test
    @DisplayName("Catch any unhandled Exception and return Internal Server Error 500")
    void givenUnhandledServerExceptionReturnInternalServerError()  {
        ResponseEntity errorResponse = new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        ResponseEntity actualResponse = new RestExceptionHandler().generalExceptionHandler(new Exception());
        
        assertThat(actualResponse.getStatusCode()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(actualResponse.getStatusCode()).isEqualTo(errorResponse.getStatusCode());
    }
}
