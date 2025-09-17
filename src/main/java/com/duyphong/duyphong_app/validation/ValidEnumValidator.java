package com.duyphong.duyphong_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator for enum values with case-insensitive matching
 * Works with both String and Enum types
 */
public class ValidEnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;
    private Set<String> validValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.ignoreCase = constraintAnnotation.ignoreCase();
        
        // Get all enum constants as strings
        this.validValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation
        }

        String stringValue;
        if (value instanceof Enum<?>) {
            stringValue = ((Enum<?>) value).name();
        } else if (value instanceof String) {
            stringValue = (String) value;
        } else {
            return false; // Invalid type
        }

        boolean isValid;
        if (ignoreCase) {
            isValid = validValues.stream()
                    .anyMatch(validValue -> validValue.equalsIgnoreCase(stringValue));
        } else {
            isValid = validValues.contains(stringValue);
        }

        if (!isValid) {
            // Customize error message to include valid values
            context.disableDefaultConstraintViolation();
            String validValuesString = String.join(", ", validValues);
            String message = String.format("Invalid value '%s'. Must be one of: [%s]", stringValue, validValuesString);
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}