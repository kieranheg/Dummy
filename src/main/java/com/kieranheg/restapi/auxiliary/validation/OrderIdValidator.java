package com.kieranheg.restapi.auxiliary.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.isEmpty;

public class OrderIdValidator implements ConstraintValidator<ValidOrderId, String> {
    
    private static final Pattern ORDER_ID_REGEXP = Pattern.compile("\\d{10}");
    
    public boolean isValid(String object, ConstraintValidatorContext context) {
        return !isEmpty(object) && ORDER_ID_REGEXP.matcher(object).matches();
    }
}

