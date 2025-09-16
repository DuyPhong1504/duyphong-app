package com.duyphong.duyphong_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO for updating employee department request
 * Contains the new department ID for employee department change
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateEmployeeDepartmentRequest {
    
    @NotBlank(message = "New department ID cannot be blank")
    @Size(min = 1, max = 255, message = "New department ID must be between 1 and 255 characters")
    private String newDepartmentId;
}
