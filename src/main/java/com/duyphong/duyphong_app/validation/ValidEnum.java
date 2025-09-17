package com.duyphong.duyphong_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation for enum values with case-insensitive matching
 * Validates that the string value matches one of the enum constants (ignoring case)
 */
@Documented
@Constraint(validatedBy = ValidEnumValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
    
    /**
     * The enum class to validate against
     */
    Class<? extends Enum<?>> enumClass();
    
    /**
     * Whether to ignore case when matching
     */
    boolean ignoreCase() default true;
    
    /**
     * Error message
     */
    String message() default "Invalid value. Must be one of: {validValues}";
    
    /**
     * Validation groups
     */
    Class<?>[] groups() default {};
    
    /**
     * Payload
     */
    Class<? extends Payload>[] payload() default {};
}