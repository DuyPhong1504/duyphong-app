package com.duyphong.duyphong_app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

/**
 * Request DTO for creating a new task
 * Used as request body for task creation API
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CreateTaskRequest {

    @NotBlank(message = "Employee ID cannot be blank")
    @Size(max = 255, message = "Employee ID cannot exceed 255 characters")
    private String employeeId;

    @NotBlank(message = "Task name cannot be blank")
    @Size(max = 255, message = "Task name cannot exceed 255 characters")
    private String taskName;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Due date cannot be null. Please use format: yyyy-MM-dd (e.g., 2025-12-31)")
    @Future(message = "Due date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate dueDate;
}