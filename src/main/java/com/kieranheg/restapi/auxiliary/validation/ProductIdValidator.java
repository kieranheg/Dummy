package com.kieranheg.restapi.auxiliary.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static org.springframework.util.StringUtils.isEmpty;

public class ProductIdValidator implements ConstraintValidator<ValidProductId, String> {
    
    private static final Pattern PRODUCT_ID_REGEXP = Pattern.compile("\\d{10}");
    
    public boolean isValid(String object, ConstraintValidatorContext context) {
        return !isEmpty(object) && PRODUCT_ID_REGEXP.matcher(object).matches();
    }
}

