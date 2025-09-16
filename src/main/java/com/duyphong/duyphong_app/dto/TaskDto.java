package com.duyphong.duyphong_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO for creating a new task
 * Used as request body for task creation API
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TaskDto {

    @NotBlank(message = "Employee ID cannot be blank")
    @Size(max = 255, message = "Employee ID cannot exceed 255 characters")
    private String employeeId;

    @NotBlank(message = "Task name cannot be blank")
    @Size(max = 255, message = "Task name cannot exceed 255 characters")
    private String taskName;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
}
