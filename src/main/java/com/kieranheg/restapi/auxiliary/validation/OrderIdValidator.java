package com.kieranheg.restapi.auxiliary.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.isEmpty;

public class OrderIdValidator implements ConstraintValidator<ValidOrderId, Integer> {
    
    private static final Pattern ORDER_ID_REGEXP = Pattern.compile("\\d{9}");
    
    public boolean isValid(Integer object, ConstraintValidatorContext context) {
        return !isEmpty(object) && ORDER_ID_REGEXP.matcher(object.toString()).matches();
    }
}

