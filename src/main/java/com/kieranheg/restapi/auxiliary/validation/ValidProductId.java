package com.kieranheg.restapi.auxiliary.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ProductIdValidator.class)
public @interface ValidProductId {
    
    String message() default "Product id '${validatedValue}' is invalid";
    
    Class<?>[] groups() default { };
    
    Class<? extends Payload>[] payload() default { };
}