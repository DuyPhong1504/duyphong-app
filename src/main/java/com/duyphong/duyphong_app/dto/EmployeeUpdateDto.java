package com.duyphong.duyphong_app.dto;

import com.duyphong.duyphong_app.utils.AtLeastOneField;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO for updating employee information
 * Contains only the fields that can be updated: fullname, position, and salary
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@AtLeastOneField(message = "At least one field (fullname, position, or salary) must be provided for update")
public class EmployeeUpdateDto {
    
    @Size(min = 2, max = 255, message = "Full name must be between 2 and 255 characters")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Full name can only contain letters and spaces")
    private String fullname;
    
    @Size(min = 2, max = 255, message = "Position must be between 2 and 255 characters")
    private String position;
    
    @Min(value = 0, message = "Salary must be greater than or equal to 0")
    @Max(value = 999999999, message = "Salary must not exceed 999,999,999")
    private Integer salary;
}