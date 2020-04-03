package com.kieranheg.restapi.auxiliary.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductIdValidator implements ConstraintValidator<ValidProductId, String> {
    
    private static final Pattern PRODUCT_ID_REGEXP = Pattern.compile("\\d{10}");
    
    public boolean isValid(String object, ConstraintValidatorContext context) {
        if (object == null) {
            return false;
        }
        
        Matcher matcher = PRODUCT_ID_REGEXP.matcher(object);
        return matcher.matches();
    }
}

