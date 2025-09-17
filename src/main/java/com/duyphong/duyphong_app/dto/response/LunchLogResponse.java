package com.duyphong.duyphong_app.dto.response;

import com.duyphong.duyphong_app.enumeration.MealType;
import lombok.*;

import java.time.LocalDate;

/**
 * Response DTO for Lunch Log entity
 * Used as response body for lunch log APIs
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class LunchLogResponse {

    private Integer id;
    private String employeeId;
    private LocalDate lunchDate;
    private MealType mealType;
    private String restaurant;
    private String notes;
}