package com.duyphong.duyphong_app.enumeration;

import com.duyphong.duyphong_app.config.MealTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = MealTypeDeserializer.class)
public enum MealType {
    LUNCH,
    DINNER
}
