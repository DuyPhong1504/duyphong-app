package com.duyphong.duyphong_app.config;

import com.duyphong.duyphong_app.enumeration.MealType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Custom deserializer for MealType enum that supports case-insensitive matching
 */
public class MealTypeDeserializer extends JsonDeserializer<MealType> {

    @Override
    public MealType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) 
            throws IOException {
        String value = jsonParser.getText();
        
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        // Try to match case-insensitively
        for (MealType mealType : MealType.values()) {
            if (mealType.name().equalsIgnoreCase(value.trim())) {
                return mealType;
            }
        }
        
        // If no match found, throw exception with helpful message
        throw new IllegalArgumentException(
            String.format("Invalid value '%s' for MealType. Must be one of: [LUNCH, DINNER] (case-insensitive)", value)
        );
    }
}