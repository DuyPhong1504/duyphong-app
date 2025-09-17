package com.duyphong.duyphong_app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Request DTO for creating a department
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDepartmentRequest {
    
    @NotBlank(message = "Department name is required")
    private String name;
}