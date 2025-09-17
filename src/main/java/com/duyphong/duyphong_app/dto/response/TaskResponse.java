package com.duyphong.duyphong_app.dto.response;

import com.duyphong.duyphong_app.entity.EmployeeEntity;
import com.duyphong.duyphong_app.enumeration.TaskStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Response DTO for Task entity
 * Used as response body for task APIs
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TaskResponse {

    private Integer id;
    private String taskName;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    
    // Employee information using EmployeeEntity directly
    private EmployeeEntity employee;
}