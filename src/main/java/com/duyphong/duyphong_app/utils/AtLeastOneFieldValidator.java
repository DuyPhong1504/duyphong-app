package com.duyphong.duyphong_app.utils;

import com.duyphong.duyphong_app.dto.request.UpdateEmployeeRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for @AtLeastOneField annotation
 * Ensures that at least one field in UpdateEmployeeRequest is not null
 */
public class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneField, UpdateEmployeeRequest> {

    @Override
    public void initialize(AtLeastOneField constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(UpdateEmployeeRequest value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        
        // Check if at least one field is not null
        return value.getFullname() != null || 
               value.getPosition() != null || 
               value.getSalary() != null;
    }
}