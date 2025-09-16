package com.duyphong.duyphong_app.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation to ensure at least one field is not null
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneFieldValidator.class)
@Documented
public @interface AtLeastOneField {
    
    String message() default "At least one field must be provided for update";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    String[] fields() default {};
}