package com.duyphong.duyphong_app.dto.request;

import com.duyphong.duyphong_app.enumeration.MealType;
import com.duyphong.duyphong_app.validation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * Request DTO for creating a single lunch log entry
 * Used within bulk lunch log registration requests
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CreateLunchLogRequest {

    @NotBlank(message = "Employee ID cannot be blank")
    @Size(max = 255, message = "Employee ID cannot exceed 255 characters")
    private String employeeId;

    @NotNull(message = "Lunch date cannot be null. Please use format: yyyy-MM-dd (e.g., 2025-09-17)")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate lunchDate;

    @NotNull(message = "Meal type cannot be null")
    @ValidEnum(enumClass = MealType.class, message = "Meal type must be either LUNCH or DINNER (case-insensitive)")
    private MealType mealType;

    @Size(max = 255, message = "Restaurant name cannot exceed 255 characters")
    private String restaurant;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;
}